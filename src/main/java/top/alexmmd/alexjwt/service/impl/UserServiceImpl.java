package top.alexmmd.alexjwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import top.alexmmd.alexjwt.dao.ApplicationUserDao;
import org.springframework.stereotype.Service;
import top.alexmmd.alexjwt.dao.RoleDao;
import top.alexmmd.alexjwt.dao.UsersRolesDao;
import top.alexmmd.alexjwt.model.ApplicationUser;
import top.alexmmd.alexjwt.model.ResultUtils;
import top.alexmmd.alexjwt.model.Role;
import top.alexmmd.alexjwt.model.UsersRoles;
import top.alexmmd.alexjwt.service.ApplicationUserService;

import java.util.Arrays;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 16:34:06
 */
@Service("userService")
public class UserServiceImpl implements ApplicationUserService {

    @Autowired
    private ApplicationUserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UsersRolesDao usersRolesDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ApplicationUser queryById(Long id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ApplicationUser> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public ApplicationUser insert(ApplicationUser user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public ApplicationUser update(ApplicationUser user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userDao.deleteById(id) > 0;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ResultUtils signUp(ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.insert(user);
        ApplicationUser newUser = userDao.findByUsername(user.getUsername());
        Role role = roleDao.queryByRoleName("ROLE_USER");
        UsersRoles usersRoles = UsersRoles.builder()
                .roleId(role.getId())
                .userId(Long.valueOf(newUser.getId()))
                .build();
        usersRolesDao.insert(usersRoles);
        return new ResultUtils(101, "注册成功");
    }

    /**
     * 注册时判断用户名是否已使用
     *
     * @param username
     * @return
     */
    @Override
    public ApplicationUser findByUsername(String username) {
        ApplicationUser applicationUser = userDao.findByUsername(username);
        return applicationUser;
    }
}