package org.gourd.hu.mongo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.gourd.hu.mongo.model.entity.DeptMog;
import org.gourd.hu.mongo.model.entity.UserMog;
import org.gourd.hu.mongo.repository.DeptRepository;
import org.gourd.hu.mongo.repository.UserRepository;
import org.gourd.hu.mongo.service.UserMogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Description mongo用户服务类
 * @Author gourd.hu
 * @Date 2020/7/22 17:24
 * @Version 1.0
 */
public class UserMogServiceImpl implements UserMogService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeptRepository deptRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserMog userMog) {
        DeptMog deptMog = userMog.getDept();
        if(deptMog != null ){
            // 保存部门
            if(deptMog.getId() == null){
                deptMog.setId(IdWorker.getId());
                deptRepository.save(deptMog);
            }else {
                Optional<DeptMog> mogOptional = deptRepository.findById(deptMog.getId());
                if(!mogOptional.isPresent()){
                    deptRepository.save(deptMog);
                }else {
                    deptMog = mogOptional.get();
                }
            }
        }
        // 保存用户
        userMog.setDeptId(deptMog.getId());
        userMog.setDept(deptMog);
        if(userMog.getId() == null){
            userMog.setId(IdWorker.getId());
        }
        userRepository.save(userMog);
    }
}
