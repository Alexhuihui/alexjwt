package top.alexmmd.alexjwt.service;

import top.alexmmd.alexjwt.model.ApplicationUser;
import top.alexmmd.alexjwt.model.ResultUtils;

import java.util.List;

/**
 * (ApplicationUser)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 16:30:11
 */
public interface ApplicationUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ApplicationUser queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ApplicationUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param applicationUser 实例对象
     * @return 实例对象
     */
    ApplicationUser insert(ApplicationUser applicationUser);

    /**
     * 修改数据
     *
     * @param applicationUser 实例对象
     * @return 实例对象
     */
    ApplicationUser update(ApplicationUser applicationUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    ResultUtils signUp(ApplicationUser user);

    /**
     * 注册时判断用户名是否已使用
     *
     * @param username
     * @return
     */
    ApplicationUser findByUsername(String username);
}