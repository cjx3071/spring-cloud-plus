package org.gourd.hu.rbac.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.gourd.hu.rbac.dao.RbacUserDao;
import org.gourd.hu.rbac.model.entity.RbacUser;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @Description 用户分批查询任务
 * @Author gourd.hu
 * @Date 2020/9/28 15:33
 * @Version 1.0
 */
public class UserFindTask extends RecursiveTask<List<RbacUser>> {
    /**
     * 默认分批数量
     */
    public static final int BATCH_COUNT = 10000;
    /**
     * 分批数量
     */
    private Integer batchCount;

    /**
     * 任务最大的分批页
     */
    private Integer maxBatchNo;
    /**
     * 任务最小的分批页
     */
    private Integer minBatchNo;

    /**
     * 数据查询接口
     */
    private RbacUserDao rbacUserDao;

    public UserFindTask(Integer batchCount,Integer minBatchNo,Integer maxBatchNo,RbacUserDao rbacUserDao){
        this.batchCount = batchCount;
        this.minBatchNo = minBatchNo;
        this.maxBatchNo = maxBatchNo;
        this.rbacUserDao = rbacUserDao;
    }
    public UserFindTask(Integer minBatchNo,Integer maxBatchNo,RbacUserDao rbacUserDao){
        this.batchCount = BATCH_COUNT;
        this.minBatchNo = minBatchNo;
        this.maxBatchNo = maxBatchNo;
        this.rbacUserDao = rbacUserDao;
    }

    @Override
    protected List<RbacUser> compute() {
        // 相当于分页，当最小、最大分页数相同时，说明任务已切分完成，执行任务逻辑
        if(maxBatchNo.equals(minBatchNo)){
            Page page = new Page<>(minBatchNo,batchCount);
            LambdaQueryWrapper<RbacUser> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.orderByDesc(RbacUser::getId);
            return rbacUserDao.selectPage(page,queryWrapper).getRecords();
        }
        // 将任务分成2批次
        int middleBatchNo =  (maxBatchNo+minBatchNo) >>1 ;
        // 每个批次创建各自子任务
        UserFindTask userFindTask1 = new UserFindTask(batchCount,minBatchNo,middleBatchNo,rbacUserDao);
        UserFindTask userFindTask2 = new UserFindTask(batchCount,middleBatchNo+1,maxBatchNo,rbacUserDao);
        // 子任务执行(fork)
        invokeAll(userFindTask1,userFindTask2);
        // 任务汇总
        List<RbacUser> userList1 = userFindTask1.join();
        List<RbacUser> userList2 = userFindTask2.join();
        // 自定义汇总逻辑
        userList1.addAll(userList2);
        return userList1;
    }
}
