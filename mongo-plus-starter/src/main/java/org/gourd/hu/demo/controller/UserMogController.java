package org.gourd.hu.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.mongo.model.dto.UserMogFindDTO;
import org.gourd.hu.mongo.model.dto.UserMogTestFindDTO;
import org.gourd.hu.mongo.model.entity.UserMog;
import org.gourd.hu.mongo.repository.UserRepository;
import org.gourd.hu.mongo.service.UserMogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description mongo测试
 * @Author gourd.hu
 * @Date 2020/7/22 16:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/user-mongo")
@Api(tags = "mongo测试API", description = "mongo测试API")
public class UserMogController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMogService userMogService;

    /**
     * 保存/修改
     *
     * @return
     */
    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/save")
    public BaseResponse save(@RequestBody UserMog userMog) {
        userMogService.save(userMog);
        return BaseResponse.ok("保存/修改成功");
    }

    /**
     * 删除
     *
     * @return
     */
    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete/{userId}")
    public BaseResponse delete(@PathVariable Long userId) {
        userRepository.deleteById(userId);
        return BaseResponse.ok("删除成功");
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    @ApiOperation(value = "获取所有数据")
    @GetMapping(value = "/findAll")
    public BaseResponse<List<UserMog>> findAll() {
        List<UserMog> userMogs = userRepository.findAll();
        return BaseResponse.ok(userMogs);
    }

    /**
     * 查询数据
     *
     * @return
     */
    @ApiOperation(value = "查询数据")
    @GetMapping(value = "/find")
    public BaseResponse<Page<UserMog>> find(UserMogFindDTO userMogFindDTO) {
        // page为0 表示 第一页，所以此处减1
        Pageable pageable = PageRequest.of(userMogFindDTO.getPageNo() - 1, userMogFindDTO.getPageSize());
        UserMog userMog = new UserMog();
        BeanUtil.copyProperties(userMogFindDTO, userMog);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("account", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith());
        Example<UserMog> userMogExample = Example.of(userMog, exampleMatcher);
        Page<UserMog> userMogPage = userRepository.findAll(userMogExample, pageable);
        return BaseResponse.ok(userMogPage);
    }


    /**
     * 测试查询
     *
     * @return
     */
    @ApiOperation(value = "测试查询")
    @GetMapping(value = "/testFind")
    public BaseResponse<List<UserMog>> testFind(UserMogTestFindDTO testFindDTO) {
        List<UserMog> userMogList;
        if (CollectionUtil.isNotEmpty(testFindDTO.getAges())) {
            userMogList = userRepository.findByAgeIn(testFindDTO.getAges());
            return BaseResponse.ok(userMogList);
        } else {
            userMogList = userRepository.findByAccountLikeAndAgeBetween(testFindDTO.getAccount(), testFindDTO.getStartAge(), testFindDTO.getEndAge());
            return BaseResponse.ok(userMogList);
        }
    }


    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 关联查询
     *
     * @return
     */
    @ApiOperation(value = "关联查询")
    @GetMapping(value = "/relateFind")
    public BaseResponse<List<UserMog>> relateFind(UserMogFindDTO userMogFindDTO) {
        // 创建条件
        AggregationOperation lookup = Aggregation.lookup("dept", "deptId", "_id", "dept");
//        AggregationOperation unwind = Aggregation.unwind("depts");
        AggregationOperation match = Aggregation.match(Criteria.where("account").is(userMogFindDTO.getAccount()).and("dept.name").is(userMogFindDTO.getDeptName()));
//        AggregationOperation project = Aggregation.project("name", "age", "dept.code","dept.name");
        // 将条件封装到Aggregate管道
        Aggregation aggregation = Aggregation.newAggregation(lookup, match);
        // 查询
        AggregationResults<UserMog> aggregate = mongoTemplate.aggregate(aggregation, "user", UserMog.class);
        return BaseResponse.ok(aggregate.getMappedResults());
    }
}
