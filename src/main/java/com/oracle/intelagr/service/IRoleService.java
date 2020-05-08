package com.oracle.intelagr.service;

import com.github.pagehelper.PageInfo;
import com.oracle.intelagr.common.PageModel;
import com.oracle.intelagr.entity.Role;
import com.oracle.intelagr.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IRoleService {
    /**
     * 查询出角色列表
     * @return
     */
    public List<Role> selectAll();

    public void queryForPage(PageModel pageModel);

    /**
     * 分页查询角色信息
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Role> queryForPage(Map map, Integer pageNum, Integer pageSize);

    public Role queryById(int id);

    public void delete(int[] ids);

    public void update(Role role);

    public void save(Role role);

    public void saveRoleAuth(String roleCode, String[] funIds, User user);
}
