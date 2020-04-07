package top.alexmmd.alexjwt.service;

import top.alexmmd.alexjwt.model.RolesPrivileges;

import java.util.List;

/**
 * (RolesPrivileges)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 16:30:11
 */
public interface RolesPrivilegesService {

    /**
     * 通过ID查询单条数据
     *
     * @param rolesPrivilegesId 主键
     * @return 实例对象
     */
    RolesPrivileges queryById(Integer rolesPrivilegesId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RolesPrivileges> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param rolesPrivileges 实例对象
     * @return 实例对象
     */
    RolesPrivileges insert(RolesPrivileges rolesPrivileges);

    /**
     * 修改数据
     *
     * @param rolesPrivileges 实例对象
     * @return 实例对象
     */
    RolesPrivileges update(RolesPrivileges rolesPrivileges);

    /**
     * 通过主键删除数据
     *
     * @param rolesPrivilegesId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer rolesPrivilegesId);

}