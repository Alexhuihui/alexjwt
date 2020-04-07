package top.alexmmd.alexjwt.service;

import top.alexmmd.alexjwt.model.Privilege;

import java.util.List;

/**
 * (Privilege)表服务接口
 *
 * @author makejava
 * @since 2020-04-07 16:30:11
 */
public interface PrivilegeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Privilege queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Privilege> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param privilege 实例对象
     * @return 实例对象
     */
    Privilege insert(Privilege privilege);

    /**
     * 修改数据
     *
     * @param privilege 实例对象
     * @return 实例对象
     */
    Privilege update(Privilege privilege);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}