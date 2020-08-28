package top.alexmmd.alexjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.alexmmd.alexjwt.annotation.AutoIdempotent;
import top.alexmmd.alexjwt.dao.RoleDao;
import top.alexmmd.alexjwt.model.ApplicationUser;
import top.alexmmd.alexjwt.model.ResultUtils;
import top.alexmmd.alexjwt.service.ApplicationUserService;
import top.alexmmd.alexjwt.service.RoleService;

import java.util.Arrays;

/**
 * @author 汪永晖
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ApplicationUserService applicationUserService;

    /**
     * 注册新用户
     *
     * @param user
     */
    @PostMapping("/record")
    public ResultUtils signUp(@RequestBody @Validated ApplicationUser user) {
        if (usernameExist(user.getUsername())) {
            return new ResultUtils(500, "该用户名已经被使用");
        }
        return applicationUserService.signUp(user);
    }

    @GetMapping("/secure")
    @AutoIdempotent
    public ResultUtils reachSecureEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResultUtils(100, "If your are reading this you reached a user endpoint", authentication);
    }

    /**
     * 判断用户名是否被使用
     *
     * @param username
     * @return true -> exist ; false -> notExist
     */
    private boolean usernameExist(String username) {
        if (null == applicationUserService.findByUsername(username)) {
            return false;
        }
        return true;
    }
}
