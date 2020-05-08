package com.oracle.intelagr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.intelagr.entity.Function;
import com.oracle.intelagr.entity.User;
import com.oracle.intelagr.entity.UserRole;
import com.oracle.intelagr.mapper.UserMapper;
import com.oracle.intelagr.mapper.UserRoleMapper;
import com.oracle.intelagr.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    /**
     * 登录
     *
     * @param user
     * @return
     */

    @Override
    public User login(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", user.getUserID());
        map.put("password", user.getPassword());
        List<User> list = userMapper.select(map);
        if (list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Map> getFunction(String userID) {
        return null;
    }

    /**
     * 分页查询用户
     *
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> queryForPage(Map map, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize); // 分页信息
        return new PageInfo<User>(userMapper.select(map));
    }

    @Override
    @Transactional
    public void save(User user,String[] role) {
        /**
         *插入用户基本信息
         */
        userMapper.insert(user);
        /**
         * 插入角色信息
         */
        for(String r:role){
            UserRole ur = new UserRole();
            ur.setUserID(user.getUserID());
            ur.setRoleCode(r);
            ur.setCreateDate(new Date());
            ur.setCreateDate(new Date());
            ur.setCreateUserId(user.getUpdateUserId());
            ur.setUpdateUserId(user.getUpdateUserId());
            userRoleMapper.insert(ur);
        }
    }

    /**
     * 修改用户回显
     * @param userID
     * @return
     */
    @Override
    public User selectById(String userID) {
        return userMapper.selectById(userID);
    }

    /**
     * 修改用户基本信息
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(String userID) {

    }

    @Override
    public void resetPwd(String userID, String password) {

    }

    @Override
    public void startUse(String userID) {

    }

    @Override
    public void endUse(String userID) {

    }

    /**
     * 动态权限集合分组
     *
     * @param userId
     */
    @Override
    public Map<String, List<Function>> getFunctions(String userId) {
        /**
         * 有序的Map→LinkedHashMap
         */
        Map<String, List<Function>> map = new LinkedHashMap<String, List<Function>>();
        for (Function f : userMapper.getFunctionsByUserId(userId)) {
            if (!map.containsKey(f.getModuleName())) {
                map.put(f.getModuleName(), new ArrayList<Function>());
            }
            map.get(f.getModuleName()).add(f);
        }
        return map;
    }
}
