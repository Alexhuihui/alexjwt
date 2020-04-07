package top.alexmmd.alexjwt.service;

import top.alexmmd.alexjwt.model.UsersRoles;

import java.util.List;

/**
 * (UsersRoles)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 16:30:11
 */
public interface UsersRolesService {

    /**
     * 通过ID查询单条数据
     *
     * @param usersRolesId 主键
     * @return 实例对象
     */
    UsersRoles queryById(Integer usersRolesId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UsersRoles> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param usersRoles 实例对象
     * @return 实例对象
     */
    UsersRoles insert(UsersRoles usersRoles);

    /**
     * 修改数据
     *
     * @param usersRoles 实例对象
     * @return 实例对象
     */
    UsersRoles update(UsersRoles usersRoles);

    /**
     * 通过主键删除数据
     *
     * @param usersRolesId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer usersRolesId);

}