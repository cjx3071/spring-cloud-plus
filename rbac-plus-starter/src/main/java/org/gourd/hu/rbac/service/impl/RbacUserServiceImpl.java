package org.gourd.hu.rbac.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.core.utils.CollectionUtil;
import org.gourd.hu.core.utils.Pinyin4jUtil;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.auth.jwt.JwtUtil;
import org.gourd.hu.rbac.dao.RbacUserDao;
import org.gourd.hu.rbac.dao.RbacUserRoleDao;
import org.gourd.hu.rbac.model.dto.*;
import org.gourd.hu.rbac.model.entity.RbacUser;
import org.gourd.hu.rbac.model.entity.RbacUserRole;
import org.gourd.hu.rbac.model.entity.SysTenant;
import org.gourd.hu.rbac.model.vo.UserVO;
import org.gourd.hu.rbac.service.RbacUserService;
import org.gourd.hu.rbac.service.SysTenantService;
import org.gourd.hu.rbac.utils.ShiroKitUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户处理业务层
 *
 * @author gourd.hu
 * @date 2019-04-02 17:26:16
 */
@Service
@Slf4j
public class RbacUserServiceImpl extends ServiceImpl<RbacUserDao, RbacUser> implements RbacUserService {

    @Autowired
    private RbacUserDao rbacUserDao;

    @Autowired
    private SysTenantService sysTenantService;

    @Autowired
    private RbacUserRoleDao rbacUserRoleDao;

    @Override
    @DS("slave")
    public UserVO getByAccount(String account){
        UserVO userVO = new UserVO();
        RbacUser rbacUser = rbacUserDao.getByAccount(account);
        // 断言用户存在
        ResponseEnum.DATA_NOT_FOUND.assertNotNull(rbacUser);
        BeanUtils.copyProperties(rbacUser,userVO);
        return userVO ;
    }

    @Override
    @DS("slave")
    public RbacUser getByAccountAndTenantId(String account, Long tenantId){
       return rbacUserDao.getByAccountAndTenantId(account,tenantId);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @Override
    @DS("slave")
    public UserVO getById(Long id){
        UserVO userVO = new UserVO();
        RbacUser rbacUser = rbacUserDao.selectById(id);
        // 断言用户存在
        ResponseEnum.DATA_NOT_FOUND.assertNotNull(rbacUser);
        BeanUtils.copyProperties(rbacUser,userVO);
        return userVO;
    }
    /**
     * 获取当前用户信息
     * @return
     */
    @Override
    public JwtToken getCurrent(){
        return JwtUtil.getCurrentUser();
    }
    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    @DS("slave")
    @Cacheable("user")
    public List<UserVO> findAll(){
        List<RbacUser> rbacUsers = rbacUserDao.selectList(null);
        return CollectionUtil.copyList(rbacUsers, UserVO.class);
    }
    /**
     * 根据条件获取用户
     * @return
     */
    @Override
    @DS("slave")
    @Cacheable("user")
    public IPage<UserVO> find(RbacUserSearchDTO rbacUserDTO, Page page ){
        QueryWrapper<RbacUser> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(rbacUserDTO.getAccount())){
            wrapper.eq("account", rbacUserDTO.getAccount());
        }
        if(StringUtils.isNotEmpty(rbacUserDTO.getName())){
            wrapper.like("name", rbacUserDTO.getAccount());
        }
        IPage<UserVO> rbacUserPage = rbacUserDao.selectPage(page, wrapper);
        // 需要再转换一次
        List<UserVO> userVOList = CollectionUtil.copyList(rbacUserPage.getRecords(), UserVO.class);
        rbacUserPage.setRecords(userVOList);
        return rbacUserPage;
    }

    /**
     * 根据部门id获取用户
     * @return
     */
    @Override
    @DS("slave")
    public IPage<UserVO> findUsersOrg(RbacUserOrgSearchDTO userOrgSearchDTO, Page page){
        JwtToken jwtUser = JwtUtil.getCurrentUser();
        IPage<UserVO> usersDeptPage= rbacUserDao.findUsersOrg(page, userOrgSearchDTO, jwtUser.getTenantId());
        List<UserVO> userVOList = CollectionUtil.copyList(usersDeptPage.getRecords(), UserVO.class);
        usersDeptPage.setRecords(userVOList);
        return usersDeptPage;
    }

    /**
     * 保存用户
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user",allEntries = true)
    public UserVO register(RbacUserRegisterDTO rbacUserRegisterDTO){
        // 返回VO对象
        String[] accountItems = StringUtils.split(rbacUserRegisterDTO.getAccount(), "@");
        // 账号元素
        String accountItem = accountItems[0];
        // 承租人元素（number或code）
        String tenantItem = accountItems[1];
        SysTenant tenant = sysTenantService.checkGetTenant(tenantItem);
        RbacUserOperateDTO rbacUserCreateDTO = new RbacUserOperateDTO();
        BeanUtils.copyProperties(rbacUserRegisterDTO, rbacUserCreateDTO);
        rbacUserCreateDTO.setTenantId(tenant.getId());
        rbacUserCreateDTO.setAccount(accountItem);
        return this.create(rbacUserCreateDTO);
    }

    /**
     * 保存用户
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user",allEntries = true)
    public UserVO create(RbacUserOperateDTO rbacUserCreateDTO){
        // 当前用户承租人
        Long tenantId = null;
        if(rbacUserCreateDTO.getTenantId() == null){
            tenantId = JwtUtil.getCurrentUser().getTenantId();
        }else {
            tenantId = rbacUserCreateDTO.getTenantId();
        }
        RbacUser dbUser = rbacUserDao.getByAccountAndTenantId(rbacUserCreateDTO.getAccount(), tenantId);
        // 断言用户未注册
        ResponseEnum.ACCOUNT_BEEN_USED.assertIsNull(dbUser);
        // 返回VO对象
        UserVO userVO = new UserVO();
        RbacUser rbacUser = new RbacUser();
        BeanUtils.copyProperties(rbacUserCreateDTO,rbacUser);
        rbacUser.setTenantId(tenantId);
        // 生成加密密码
        rbacUser.setPassword(ShiroKitUtil.md5(rbacUserCreateDTO.getPassword(),rbacUserCreateDTO.getAccount()));
        // 设置拼音
        rbacUser.setPinYin(Pinyin4jUtil.getPinyin(rbacUser.getName()));
        // 保存主表用户信息
        rbacUserDao.insert(rbacUser);
        // 设置默认的 角色
        long roleId = 3;
        RbacUserRole rbacUserRole = new RbacUserRole();
        rbacUserRole.setUserId(rbacUser.getId());
        rbacUserRole.setRoleId(roleId);
        rbacUserRoleDao.insert(rbacUserRole);
        BeanUtils.copyProperties(rbacUser,userVO);
        return userVO;
    }


    /**
     * 更新用户
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user",allEntries = true)
    @Retryable(value = RetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1))
    public int update(RbacUserOperateDTO user){
        RbacUser dbOldUser= rbacUserDao.selectById(user.getId());
        // 断言用户存在
        ResponseEnum.DATA_NOT_FOUND.assertNotNull(dbOldUser);
        RbacUser rbacUser = new RbacUser();
        BeanUtils.copyProperties(user,rbacUser);
        rbacUser.setVersion(dbOldUser.getVersion());
        int userU = rbacUserDao.updateById(rbacUser);
        if(userU != 1){
            // 抛出重试，防止乐观锁更新失败
            throw new RetryException("更新用户失败");
        }
        return userU;
    }
    /**
     * 删除用户
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user",allEntries = true)
    @Retryable(value = RetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1))
    public int delete(Long id){
        // 删除用户
        int userD = rbacUserDao.deleteById(id);
        if(userD != 1 ){
            throw new RetryException("删除用户失败");
        }
        // 删除用户角色
        Map deleteMap = new HashMap(4){{
            put("user_id",id);
        }};
        int userRoleD =  rbacUserRoleDao.deleteByMap(deleteMap);
        if(userRoleD != 1){
            throw new RetryException("删除用户角色失败");
        }
        return userRoleD;
    }
}