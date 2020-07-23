package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.es.model.dto.UserEsFindDTO;
import org.gourd.hu.es.model.entity.UserEs;
import org.gourd.hu.es.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 使用方式有两种：
 * 1.一种是经过 SpringData 封装过的，直接在 dao 接口继承 ElasticsearchRepository 即可
 * 2.一种是经过 Spring 封装过的，直接在 Service/Controller 中引入该 bean 即可 ElasticsearchTemplate
 * @author gourd.hu
 */
@RestController
@RequestMapping("/user-es")
@Api(tags = "ES测试API", description = "ES测试API")
public class UserEsController {
    
    @Autowired
    private UserRepository userRepository;

    /**
     * 保存/修改
     * @return
     */
    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/save")
    public BaseResponse<UserEs> save(@RequestBody UserEs userEs){
        userEs = userEs == null ? new UserEs() : userEs;
        BaseResponse<UserEs> res = new BaseResponse<>();
        UserEs UserEs2 = userRepository.save(userEs);
        res.setData(UserEs2);
        return res;
    }


    /**
     * 根据ID获取单个索引
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "根据ID获取单个索引")
    public BaseResponse<UserEs> get(@PathVariable Long id){
        BaseResponse<UserEs> res = new BaseResponse<>();
        Optional<UserEs> get = userRepository.findById(id);
        res.setData(get.isPresent() == false ? null : get.get());
        return res;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    @ApiOperation(value = "删除")
    public BaseResponse delete(@PathVariable Long id){
        userRepository.deleteById(id);
        return BaseResponse.ok();
    }



    /**
     * ============================单条件查询==================================
     */

    /**
     * 通过match进行模糊查询
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/searchByMatch")
    @ApiOperation(value = "通过match进行模糊查询")
    public BaseResponse<List<UserEs>> searchByMatch(@RequestParam String  name){
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        MatchQueryBuilder matchUserEs = QueryBuilders.matchQuery("name",name);
        queryBuilder(res, matchUserEs);
        return res;
    }


    /**
     *  通过term进行全量完全匹配查询
     *  根据传入属性值，检索指定属性下是否有属性值完全匹配的
     *
     *  例如：
     *  name:中国人
     *  那么查询不会进行分词，就是按照  包含完整的  中国人  进行查询匹配
     *
     * 此时ik中文分词 并没有起作用【此时是在@Field注解 指定的ik分词器】
     * 例如存入  张卫健  三个字，以ik_max_word 分词存入，查询也指定以ik查询，但是  以张卫健  查询 没有结果
     * 以   【张】   或   【卫】   或   【健】  查询 才有结果，说明分词是以默认分词器 进行分词 ，也就是一个中文汉字 进行一个分词的效果。
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/searchByTerm")
    @ApiOperation(value = "通过term进行全量完全匹配查询")
    public BaseResponse<List<UserEs>> searchByTerm(@RequestParam String name){
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        TermQueryBuilder termUserEs = QueryBuilders.termQuery("name",name);
        queryBuilder(res, termUserEs);
        return res;
    }


    /**
     * 根据range进行范围查询
     *
     * 时间也可以进行范围查询，但时间传入值应该为yyyy-MM-dd HH:mm:ss 格式的时间字符串或时间戳 或其他定义的时间格式
     * 只有在mapping中定义的时间格式，才能被ES查询解析成功
     *
     * @param minAge
     * @param maxAge
     * @return
     */
    @GetMapping(value = "/searchByRange")
    @ApiOperation(value = "根据range进行范围查询")
    public BaseResponse<List<UserEs>> searchByRange(@RequestParam Integer minAge,@RequestParam Integer maxAge){
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        RangeQueryBuilder rangeUserEs = QueryBuilders.rangeQuery("age").gt(minAge).lt(maxAge);
        queryBuilder(res, rangeUserEs);
        return res;
    }

    /**
     * ============================复合条件查询==================================
     */

    /**
     * 使用bool进行复合查询，使用filter比must query性能好
     *
     * filter是过滤，1.文档是否包含于结果 2.不涉及评分 3.更快
     * query是查询，1.文档是否匹配于结果 2.计算文档匹配评分 3.速度慢
     * @param userEsFindDTO
     * @return
     */
    @GetMapping(value = "/searchByBool")
    @ApiOperation(value = "使用bool进行复合查询")
    public BaseResponse<Page<UserEs>>  searchByBool(UserEsFindDTO userEsFindDTO){
        BaseResponse<Page<UserEs>> res = new BaseResponse<>();
        BoolQueryBuilder boolUserEs = QueryBuilders.boolQuery();

        if(StringUtils.isNotBlank(userEsFindDTO.getKeyword())){
            //多个字段匹配 属性值 must query
            MultiMatchQueryBuilder  matchQueryUserEs =
                    QueryBuilders.multiMatchQuery(userEsFindDTO.getKeyword(),"name","nickName");
            boolUserEs.must(matchQueryUserEs);
        }
        //filter 分别过滤不同字段，缩小筛选范围
        if(StringUtils.isNotBlank(userEsFindDTO.getAccount())){
            TermQueryBuilder numQuery = QueryBuilders.termQuery("account",userEsFindDTO.getAccount());
            boolUserEs.filter(numQuery);
        }
        if(userEsFindDTO.getBirth() != null){
            RangeQueryBuilder dateQuery = QueryBuilders.rangeQuery("birth").lt(userEsFindDTO.getBirth().getTime());
            boolUserEs.filter(dateQuery);
        }
        if(userEsFindDTO.getAge() != null){
            RangeQueryBuilder dateQuery = QueryBuilders.rangeQuery("age").lt(userEsFindDTO.getAge());
            boolUserEs.filter(dateQuery);
        }
        //排序 + 分页
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        if(StringUtils.isNotBlank(userEsFindDTO.getOrderColumn()) && userEsFindDTO.getOrderType() != null){
            sort = Sort.by(userEsFindDTO.getOrderType().getValue(),userEsFindDTO.getOrderColumn());
        }
        PageRequest pageRequest = PageRequest.of(userEsFindDTO.getPageNo()-1,userEsFindDTO.getPageSize(),sort);
        Page<UserEs> search = userRepository.search(boolUserEs, pageRequest);
        res.setData(search);
        return res;
    }

    /**
     * 时间范围查询
     * ES中时间字段需要设置为 date类型，才能查询时间范围
     * 时间范围要想准确查询，需要将时间转化为时间戳进行查询
     *
     * ES中date字段存储是 时间戳存储
     *
     * from[包含]  -   to[包含]
     * gt - lt
     * gte - lte
     *
     * @return
     */
    @GetMapping(value = "/searchByTimeRange")
    @ApiOperation(value = "时间范围查询")
    public BaseResponse<List<UserEs>> searchByTimeRange(@RequestParam Date birthBegin,@RequestParam Date birthEnd){
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        QueryBuilder queryUserEs = QueryBuilders.rangeQuery("birth").from(birthBegin.getTime()).to(birthEnd.getTime());
        queryBuilder(res, queryUserEs);
        return res;
    }


    /**
     *  检索所有数据
     * @return
     */
    @GetMapping(value = "/searchAll")
    @ApiOperation(value = "检索所有数据")
    public BaseResponse<List<UserEs>> searchAll(){
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        QueryBuilder queryUserEs = QueryBuilders.boolQuery();
        queryBuilder(res, queryUserEs);
        return res;
    }

    /**
     *
     * 根据传入属性值  全文检索所有属性
     * 关于QueryStringQueryUserEs的使用，如果不指定分词器，那么查询的时候，会使用ES默认的分词器进行查询。
     * 结果就是 会查询出与查询内容丝毫不相干的结果。
     *
     * 关于ES内置分词器：
     * https://blog.csdn.net/u013795975/article/details/81102010
     *
     * @return
     */
    @GetMapping(value = "/searchByKeyword")
    @ApiOperation(value = "全文检索所有属性")
    public BaseResponse<List<UserEs>> searchByKeyword(String keyword){
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keyword);
        queryBuilder(res, queryBuilder);
        return res;

    }

    /**
     * 方式1
     *
     * 选择用term或match方式查询
     * 查询字段buildName或者buildName2
     * 指定以分词器 ik_max_word 或  ik_smart 或 standard[es默认分词器] 或 english 或 whitespace 分词器进行分词查询
     *
     *
     *
     * @param analyzer  分词器
     * @param keyword       查询属性值
     * @return
     */
    @GetMapping(value = "/searchByIK")
    @ApiOperation(value = "分词查询")
    public BaseResponse<List<UserEs>> searchByIK(@RequestParam(required = false) String analyzer,@RequestParam String keyword){
        if(StringUtils.isBlank(analyzer)){
            analyzer = "ik_max_word";
        }
        BaseResponse<List<UserEs>> res = new BaseResponse<>();
        QueryBuilder matchUserEs = QueryBuilders.matchQuery("name",keyword).analyzer(analyzer);
        queryBuilder(res, matchUserEs);
        return res;
    }


    /**
     * 繁简体转化查询、拼音查询,并且加入评分查询
     * 评分规则详情：https://blog.csdn.net/paditang/article/details/79098830
     * @param pinyinStr
     * @return
     */
    @GetMapping(value = "/searchByPinYin")
    @ApiOperation(value = "拼音查询")
    public BaseResponse<List<UserEs>> searchByPinYin(@RequestParam String pinyinStr){
        BaseResponse<List<UserEs>> res =  new BaseResponse<>();
        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
        QueryBuilder ikSTQuery = QueryBuilders.matchQuery("name",pinyinStr).boost(1f);
        QueryBuilder pinyinQuery = QueryBuilders.matchQuery("name.pinyin",pinyinStr);

        disMaxQuery.add(ikSTQuery);
        disMaxQuery.add(pinyinQuery);
        queryBuilder(res, disMaxQuery);
        return res;
    }

    /**
     * 查询
     * @param res
     * @param matchUserEs
     */
    private void queryBuilder(BaseResponse<List<UserEs>> res, QueryBuilder matchUserEs) {
        Iterable<UserEs> search = userRepository.search(matchUserEs);
        Iterator<UserEs> iterator = search.iterator();
        List<UserEs> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        res.setData(list);
    }

    /**
     * 处理GET请求的时间转化
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}