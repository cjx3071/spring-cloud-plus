package org.gourd.hu.rbac.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.gourd.hu.core.utils.DateUtil;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.constant.RbacConstant;
import org.gourd.hu.rbac.handler.GlobalTenantParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@MapperScan({"org.gourd.hu.rbac.dao"})
public class RbacAutoConfiguration {

    /**
     * 承租人模式，依赖 MP 分页插件
     * 不需要承租人的，可将此注释掉
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        //  承租人 SQL 解析处理拦截器
        TenantSqlParser tenantSqlParser = new GlobalTenantParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {

            @Override
            public Expression getTenantId(boolean where) {
                // 设置为单承租人模式
                final boolean multipleTenantIds = false;
                if (where && multipleTenantIds) {
                    return multipleTenantIdCondition();
                } else {
                    return singleTenantIdCondition();
                }
            }

            /**
             * 获取单承租人id
             * @return
             */
            private Expression singleTenantIdCondition() {
                return new LongValue(JwtUtil.getCurrentUser() == null? 0L:JwtUtil.getCurrentUser().getTenantId());
            }

            /**
             * 获取多承租人ids
             * @return
             */
            private Expression multipleTenantIdCondition() {
                final InExpression inExpression = new InExpression();
                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
                final ExpressionList itemsList = new ExpressionList();
                final List<Expression> inValues = new ArrayList<>(2);
                inValues.add(new LongValue(1));
                inValues.add(new LongValue(2));
                itemsList.setExpressions(inValues);
                inExpression.setRightItemsList(itemsList);
                return inExpression;
            }

            @Override
            public String getTenantIdColumn() {
                return RbacConstant.COLUMN_TENANT_ID;
            }

            /**
             * 过滤不需要按承租人查询的表
             * @param tableName
             * @return
             */
            @Override
            public boolean doTableFilter(String tableName) {
                // 这里可以判断是否过滤表
                if (RbacConstant.NO_FILTER_TABLES.contains(tableName)) {
                    return true;
                }
                return false;
            }

        });
        sqlParserList.add(tenantSqlParser);
        // 动态表名解析处理器
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(2) {{
            put("sys_report", (metaObject, sql, tableName) -> {
                // metaObject 可以获取传入参数，这里实现你自己的动态规则
                String year = "_"+ DateUtil.getCurrentYear();
                return tableName + year;
            });
        }});
        sqlParserList.add(dynamicTableNameParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        // 过滤自定义查询
        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                // 过滤自定义查询此时无租户信息约束
                if (RbacConstant.NO_FILTER_SQLS.contains(ms.getId())) {
                    return true;
                }
                return false;
            }
        });
        return paginationInterceptor;
    }


}
