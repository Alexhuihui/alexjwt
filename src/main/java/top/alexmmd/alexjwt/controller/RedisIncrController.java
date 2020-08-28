package top.alexmmd.alexjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import top.alexmmd.alexjwt.annotation.AutoIdempotent;
import top.alexmmd.alexjwt.dao.ApplicationUserDao;
import top.alexmmd.alexjwt.dao.RoleDao;
import top.alexmmd.alexjwt.model.*;
import top.alexmmd.alexjwt.service.RedisIncrService;

import java.util.List;

/**
 * @author 汪永晖
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisIncrController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisIncrService redisIncrService;

    @Autowired
    private ApplicationUserDao applicationUserDao;

    @Autowired
    private RoleDao roleDao;

    @PostMapping("/vote")
    @AutoIdempotent
    public ResultUtils incr(@RequestBody VotePackage votePackage) {
        // 投票
        List<ApplicationUser> applicationUserList = votePackage.getApplicationUserList();
        for (ApplicationUser user : applicationUserList) {
            redisIncrService.incr(user.getUsername());
        }

        // 记录登录人已经投票
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String staffCode = authentication.getName();
        String key = "idempotent" + staffCode;
        stringRedisTemplate.opsForValue().set(key, staffCode);
        applicationUserDao.update(ApplicationUser.builder()
                .isVote(1)
                .build());
        return new ResultUtils(100, "成功投票");
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
}
