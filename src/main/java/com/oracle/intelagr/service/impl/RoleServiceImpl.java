package com.oracle.intelagr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.intelagr.common.PageModel;
import com.oracle.intelagr.entity.Role;
import com.oracle.intelagr.entity.User;
import com.oracle.intelagr.mapper.RoleMapper;
import com.oracle.intelagr.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public void queryForPage(PageModel pageModel) {

    }

    /**
     * 分页查询角色信息
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Role> queryForPage(Map map, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(roleMapper.select(map));
    }

    /**
     * 查询用户角色
     * @param id
     * @return
     */
    @Override
    public Role queryById(int id) {
        return roleMapper.selectById(id);
    }

    @Override
    public void delete(int[] ids) {

    }

    @Override
    public void update(Role role) {

    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void saveRoleAuth(String roleCode, String[] funIds, User user) {

    }
}
