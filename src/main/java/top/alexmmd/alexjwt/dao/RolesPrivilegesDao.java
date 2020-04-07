package top.alexmmd.alexjwt.dao;

import top.alexmmd.alexjwt.model.RolesPrivileges;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (RolesPrivileges)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-07 16:14:53
 */
public interface RolesPrivilegesDao {

    /**
     * 通过ID查询单条数据
     *
     * @param rolesPrivilegesId 主键
     * @return 实例对象
     */
    RolesPrivileges queryById(Integer rolesPrivilegesId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RolesPrivileges> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param rolesPrivileges 实例对象
     * @return 对象列表
     */
    List<RolesPrivileges> queryAll(RolesPrivileges rolesPrivileges);

    /**
     * 新增数据
     *
     * @param rolesPrivileges 实例对象
     * @return 影响行数
     */
    int insert(RolesPrivileges rolesPrivileges);

    /**
     * 修改数据
     *
     * @param rolesPrivileges 实例对象
     * @return 影响行数
     */
    int update(RolesPrivileges rolesPrivileges);

    /**
     * 通过主键删除数据
     *
     * @param rolesPrivilegesId 主键
     * @return 影响行数
     */
    int deleteById(Integer rolesPrivilegesId);

}