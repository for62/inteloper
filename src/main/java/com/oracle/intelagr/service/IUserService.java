package com.oracle.intelagr.service;

import com.github.pagehelper.PageInfo;
import com.oracle.intelagr.entity.Function;
import com.oracle.intelagr.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IUserService {
    public User login(User user);

    public List<Map> getFunction(String userID);

    /**
     * 分页查询用户
     *
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<User> queryForPage(Map map, Integer pageNum, Integer pageSize);

    /**
     * 插入用户以及中间表角色表
     * @param user
     * @param role
     */
    public void save(User user,String[] role);

    public User selectById(String userID);

    public void update(User user);

    public void delete(String userID);

    public void resetPwd(String userID, String password);

    public void startUse(String userID);

    public void endUse(String userID);

    /**
     * 动态权限集合分组
     */
    public Map<String, List<Function>> getFunctions(String userId);
}
