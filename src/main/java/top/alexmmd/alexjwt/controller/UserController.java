package top.alexmmd.alexjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.alexmmd.alexjwt.annotation.AutoIdempotent;
import top.alexmmd.alexjwt.dao.ApplicationUserDao;
import top.alexmmd.alexjwt.dao.RoleDao;
import top.alexmmd.alexjwt.model.ApplicationUser;
import top.alexmmd.alexjwt.model.ResultUtils;
import top.alexmmd.alexjwt.model.RoleDetail;
import top.alexmmd.alexjwt.service.ApplicationUserService;
import top.alexmmd.alexjwt.service.RoleService;

import java.util.List;

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

    @Autowired
    private ApplicationUserDao applicationUserDao;

    @Autowired
    private RoleDao roleDao;

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

    @GetMapping("/getMyInfo")
    public ResultUtils getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String staffCode = authentication.getName();
        ApplicationUser applicationUser = applicationUserDao.findByUsername(staffCode);
        List<RoleDetail> roleDetailList = roleDao.queryAllByUserId(applicationUser.getId());
        applicationUser.setRoleDetailList(roleDetailList);
        return new ResultUtils(100, "成功查询用户信息", applicationUser);
    }

    /**
     * 判断用户名是否被使用
     *
     * @param staffCode
     * @return true -> exist ; false -> notExist
     */
    private boolean usernameExist(String staffCode) {
        if (null == applicationUserService.findByUsername(staffCode)) {
            return false;
        }
        return true;
    }
}
