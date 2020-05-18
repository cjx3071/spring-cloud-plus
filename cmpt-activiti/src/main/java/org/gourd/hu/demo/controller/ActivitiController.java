package org.gourd.hu.demo.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.activiti.service.WorkFlowService;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.base.holder.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gourd.hu
 */
@RestController
@Api(tags = "工作流测试API",description = "工作流测试API")
@RequestMapping("/activiti")
@Slf4j
public class ActivitiController {

    @Autowired
    private WorkFlowService workFlowService;


    @PostMapping("/qj-apply")
    @ApiOperation(value="启动请假流程,请假pdKey:QjFlow")
    public BaseResponse startWorkflow(@RequestParam(required = false) String pdKey){
        Map param = new HashMap(4){{
            put("applyUserId","001");
            put("approveUserIds", Arrays.asList("001","002","003"));
        }};

        if(StringUtils.isBlank(pdKey)){
            pdKey="QjFlow";
        }
        // 启动流程
        String pdId = workFlowService.startWorkflow(pdKey, "QJ001", param);
        // 获取请假申请任务节点
        String Id = workFlowService.getCurrentTask(pdId);
        // 完成请假申请任务节点
        Map continueParam = new HashMap(2){{
            put("dealUserId",param.get("applyUserId"));
        }};
        workFlowService.continueWorkflow(Id,continueParam);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pdId",pdId);
        return BaseResponse.ok("请假已提交",jsonObject);
    }

    @PostMapping("/qj-approve")
    @ApiOperation(value="审批请假流程,result：0-驳回，1-同意，2-拒绝")
    public BaseResponse continueWorkflow(@RequestParam String pId,@RequestParam String result){
        Map param = new HashMap(2){{
            put("dealUserId","001");
            put("result",result);
        }};

        // 获取请假审批任务节点
        String Id = workFlowService.getCurrentTask(pId);
        // 完成请假审批任务节点
        workFlowService.continueWorkflow(Id,param);
        return BaseResponse.ok("审批成功");
    }

    @PostMapping("/qj-delegate")
    @ApiOperation(value="委托请假流程")
    public BaseResponse delegateWorkflow(@RequestParam String pId,@RequestParam String userId){
        Map param = new HashMap(2){{
            put("dealUserId",userId);
        }};
        // 获取请假审批任务节点
        String Id = workFlowService.getCurrentTask(pId);
        // 完成请假审批任务节点
        workFlowService.delegateWorkflow(Id,param);
        return BaseResponse.ok("委托成功");
    }

    /**
     *  查询用户待办流程实例
     * @param userId
     * @param pdKey
     */
    @GetMapping("/user-process")
    @ApiOperation(value="查询用户待办流程实例，请假pdKey:QjFlow")
    public BaseResponse findUserProcessIds(@RequestParam String userId, @RequestParam(required = false) String pdKey) {
        if(StringUtils.isBlank(pdKey)){
            pdKey="QjFlow";
        }
        // 获取流程图
        return BaseResponse.ok(workFlowService.findUserProcessIds(userId,pdKey,1,0));
    }

    /**
     * 读取流程资源
     * @param pId 流程实例id
     */
    @GetMapping("/read-resource")
    @ApiOperation(value="读取流程资源")
    public void readResource(@RequestParam String pId) {
        // 获取流程图
        HttpServletResponse response = RequestHolder.getResponse();
        workFlowService.getProcessImage(pId,response);

    }

}
