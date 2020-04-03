package org.gourd.hu.base.datahandler;

import org.gourd.hu.core.utils.AesHopeUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AESEncryptHandler extends BaseTypeHandler {

    /**
     * 秘钥
     */
    public static final String secretKey = "GOURD-HXN-1314";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, AesHopeUtil.encrypt(secretKey,(String)parameter));
    }
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return AesHopeUtil.decryt(secretKey,columnValue);
    }
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return AesHopeUtil.decryt(secretKey,columnValue);
    }
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return AesHopeUtil.decryt(secretKey,columnValue);
    }
}