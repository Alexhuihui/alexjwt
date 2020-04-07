package top.alexmmd.alexjwt.service.impl;

import org.springframework.stereotype.Service;
import top.alexmmd.alexjwt.dao.UsersRolesDao;
import top.alexmmd.alexjwt.model.UsersRoles;
import top.alexmmd.alexjwt.service.UsersRolesService;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UsersRoles)表服务实现类
 *
 * @author makejava
 * @since 2020-04-07 16:34:06
 */
@Service("usersRolesService")
public class UsersRolesServiceImpl implements UsersRolesService {
    @Resource
    private UsersRolesDao usersRolesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param usersRolesId 主键
     * @return 实例对象
     */
    @Override
    public UsersRoles queryById(Integer usersRolesId) {
        return this.usersRolesDao.queryById(usersRolesId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UsersRoles> queryAllByLimit(int offset, int limit) {
        return this.usersRolesDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param usersRoles 实例对象
     * @return 实例对象
     */
    @Override
    public UsersRoles insert(UsersRoles usersRoles) {
        this.usersRolesDao.insert(usersRoles);
        return usersRoles;
    }

    /**
     * 修改数据
     *
     * @param usersRoles 实例对象
     * @return 实例对象
     */
    @Override
    public UsersRoles update(UsersRoles usersRoles) {
        this.usersRolesDao.update(usersRoles);
        return this.queryById(usersRoles.getUsersRolesId());
    }

    /**
     * 通过主键删除数据
     *
     * @param usersRolesId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer usersRolesId) {
        return this.usersRolesDao.deleteById(usersRolesId) > 0;
    }
}