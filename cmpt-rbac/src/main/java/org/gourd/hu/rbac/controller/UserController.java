package org.gourd.hu.rbac.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.gourd.hu.base.common.response.BaseResponse;
import org.gourd.hu.base.utils.PageUtil;
import org.gourd.hu.rbac.dto.RbacUserCreateDTO;
import org.gourd.hu.rbac.dto.RbacUserDeptSearchDTO;
import org.gourd.hu.rbac.dto.RbacUserSearchDTO;
import org.gourd.hu.rbac.dto.RbacUserUpdateDTO;
import org.gourd.hu.rbac.service.RbacUserService;
import org.gourd.hu.rbac.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    @RequiresRoles(value={"ADMIN"}, logical= Logical.AND)
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
    public BaseResponse<UserVO> create(@RequestBody @Validated RbacUserCreateDTO rbacUserCreateDTO) {
        return BaseResponse.ok(userService.create(rbacUserCreateDTO));
    }

    @PutMapping("")
    @ApiOperation(value = "更新用户")
    @RequiresPermissions(value={"WRITE"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse update(@RequestBody @Validated RbacUserUpdateDTO rbacUserUpdateDTO) {
        return BaseResponse.ok(userService.update(rbacUserUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    @RequiresPermissions(value={"ADMIN:WRITE"})
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse delete(@PathVariable Long id) {
        userService.delete(id);
        return BaseResponse.ok("删除用户成功");
    }

    @GetMapping("/users-dept")
    @ApiOperation(value = "查询某部门的用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse<IPage<UserVO>> findUsersDept(RbacUserDeptSearchDTO rbacUserDeptSearchDTO) {
        Page page = PageUtil.buildPage(rbacUserDeptSearchDTO);
        IPage<UserVO> rbacUserPage = userService.findUsersDept(rbacUserDeptSearchDTO, page);
        return BaseResponse.ok(rbacUserPage);

    }

}
