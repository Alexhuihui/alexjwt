package top.alexmmd.alexjwt.service.impl;

import org.springframework.stereotype.Service;
import top.alexmmd.alexjwt.dao.RolesPrivilegesDao;
import top.alexmmd.alexjwt.model.RolesPrivileges;
import top.alexmmd.alexjwt.service.RolesPrivilegesService;

import javax.annotation.Resource;
import java.util.List;

/**
 * (RolesPrivileges)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 16:34:06
 */
@Service("rolesPrivilegesService")
public class RolesPrivilegesServiceImpl implements RolesPrivilegesService {
    @Resource
    private RolesPrivilegesDao rolesPrivilegesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param rolesPrivilegesId 主键
     * @return 实例对象
     */
    @Override
    public RolesPrivileges queryById(Integer rolesPrivilegesId) {
        return this.rolesPrivilegesDao.queryById(rolesPrivilegesId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<RolesPrivileges> queryAllByLimit(int offset, int limit) {
        return this.rolesPrivilegesDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param rolesPrivileges 实例对象
     * @return 实例对象
     */
    @Override
    public RolesPrivileges insert(RolesPrivileges rolesPrivileges) {
        this.rolesPrivilegesDao.insert(rolesPrivileges);
        return rolesPrivileges;
    }

    /**
     * 修改数据
     *
     * @param rolesPrivileges 实例对象
     * @return 实例对象
     */
    @Override
    public RolesPrivileges update(RolesPrivileges rolesPrivileges) {
        this.rolesPrivilegesDao.update(rolesPrivileges);
        return this.queryById(rolesPrivileges.getRolesPrivilegesId());
    }

    /**
     * 通过主键删除数据
     *
     * @param rolesPrivilegesId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer rolesPrivilegesId) {
        return this.rolesPrivilegesDao.deleteById(rolesPrivilegesId) > 0;
    }
}