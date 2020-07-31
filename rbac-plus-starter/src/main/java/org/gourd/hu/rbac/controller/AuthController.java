package org.gourd.hu.rbac.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.rbac.auth.jwt.JwtToken;
import org.gourd.hu.rbac.model.dto.RbacUserLoginDTO;
import org.gourd.hu.rbac.model.dto.RbacUserRegisterDTO;
import org.gourd.hu.rbac.model.vo.UserVO;
import org.gourd.hu.rbac.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gourd
 * createAt: 2018/9/17
 */
@RestController
@Api(tags = "登陆鉴权API",description = "权限控制器")
@RequestMapping("/auth")
public class AuthController {
    @Value("${auth.jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;


    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登陆", notes = "登陆成功返回token,测试管理员账号:admin@gourd,123456")
    public BaseResponse<JwtToken> login(@Validated @RequestBody RbacUserLoginDTO user){
        JwtToken response = authService.login(user.getAccount(), user.getPassword());
        return BaseResponse.ok(response);
    }

    @GetMapping(value = "/logout" )
    @ApiOperation(value = "登出", notes = "退出登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt-token", value = "jwt-token", required = true, dataType = "string", paramType = "header")})
    public BaseResponse logout(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        authService.logout(token);
        return BaseResponse.ok("登出成功");
    }

    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册")
    public BaseResponse<UserVO> sign(@RequestBody @Validated RbacUserRegisterDTO user) {
        return BaseResponse.ok(authService.register(user));
    }
}
