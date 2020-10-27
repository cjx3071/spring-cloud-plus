package org.gourd.hu.rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.core.utils.PageUtil;
import org.gourd.hu.rbac.model.dto.RbacUserOperateDTO;
import org.gourd.hu.rbac.model.dto.RbacUserOrgSearchDTO;
import org.gourd.hu.rbac.model.dto.RbacUserSearchDTO;
import org.gourd.hu.rbac.model.vo.UserVO;
import org.gourd.hu.rbac.service.RbacUserService;
import org.gourd.hu.rbac.validation.groups.CreateGroup;
import org.gourd.hu.rbac.validation.groups.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gourd
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户API", description = "用户控制器")
public class UserController{

    @Autowired
    private RbacUserService userService;

    @GetMapping("/all")
    @ApiOperation(value = "获取所有的用户")
    @RequiresRoles(value={"ADMIN"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<List<UserVO>> findAll() {
        return BaseResponse.ok(userService.findAll());
    }

    @GetMapping("/page")
    @ApiOperation(value = "根据条件获取用户分页")
    @RequiresRoles(value={"ADMIN"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<IPage<UserVO>> find(RbacUserSearchDTO rbacUserDTO) {
        Page page = PageUtil.buildPage(rbacUserDTO);
        IPage<UserVO> rbacUserPage = userService.find(rbacUserDTO, page);
        return BaseResponse.ok(rbacUserPage);

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<UserVO> getDetail(@PathVariable Long id) {
        return BaseResponse.ok(userService.getById(id));
    }

    @GetMapping("/account/{account}")
    @ApiOperation(value = "根据账号获取用户")
    @RequiresPermissions(value={"READ"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<UserVO> getByAccount(@PathVariable String account) {
        return BaseResponse.ok(userService.getByAccount(account));
    }


    @PostMapping("")
    @ApiOperation(value = "创建用户")
    @RequiresPermissions(value={"WRITE"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<UserVO> create(@RequestBody @Validated({CreateGroup.class}) RbacUserOperateDTO rbacUserCreateDTO) {
        return BaseResponse.ok(userService.create(rbacUserCreateDTO));
    }

    @PutMapping("")
    @ApiOperation(value = "更新用户")
    @RequiresPermissions(value={"WRITE"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse update(@RequestBody @Validated({UpdateGroup.class}) RbacUserOperateDTO rbacUserUpdateDTO) {
        return BaseResponse.ok(userService.update(rbacUserUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    @RequiresRoles(value={"ADMIN"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse delete(@PathVariable Long id) {
        userService.delete(id);
        return BaseResponse.ok("删除用户成功");
    }

    @GetMapping("/users-org")
    @ApiOperation(value = "查询某部门的用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<IPage<UserVO>> findUsersOrg(RbacUserOrgSearchDTO rbacUserOrgSearchDTO) {
        Page page = PageUtil.buildPage(rbacUserOrgSearchDTO);
        IPage<UserVO> rbacUserPage = userService.findUsersOrg(rbacUserOrgSearchDTO, page);
        return BaseResponse.ok(rbacUserPage);

    }

}
