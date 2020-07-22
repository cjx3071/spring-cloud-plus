package org.gourd.hu.demo.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.quartz.job.DemoJob;
import org.gourd.hu.quartz.job.DynamicDemoTask;
import org.gourd.hu.quartz.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 供前期测试使用
 * @author gourd.hu
 */
@RestController
@RequestMapping("/quartz")
@Api(tags = "quartz测试API", description = "定时器控制器")
@Slf4j
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private DynamicDemoTask dynamicDemoTask;


    @PostMapping("/job")
    @ApiOperation(value = "增加定时任务")
    public void addJob() {
        Map map = new HashMap(2);
        map.put("id",1L);
        // 先删除，再新增加
        quartzService.deleteJob("job", "test");
        quartzService.addJob(DemoJob.class, "job", "test", "0/30 * * * * ?",map);
    }
    
    @PutMapping("/job")
    @ApiOperation(value = "更新定时任务")
    public void updateJob() {
            quartzService.updateJob("job", "test", "0/10 * * * * ?");
    }
    
    @DeleteMapping("/job")
    @ApiOperation(value = "删除定时任务")
    public void deleteJob() {
            quartzService.deleteJob("job", "test");
    }
    


    @GetMapping("/job")
    @ApiOperation(value = "查询所有定时任务")
    public Object queryAllJob() {
        return quartzService.queryAllJob();
    }


    @PutMapping("/task")
    @ApiOperation(value = "修改task任务")
    public void getCron(String name,String cron,String jobData) {
        dynamicDemoTask.setName(name);
        dynamicDemoTask.setCron(cron);
        dynamicDemoTask.setJobData(jobData);
    }


}