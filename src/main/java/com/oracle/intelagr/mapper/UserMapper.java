package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.Function;
import com.oracle.intelagr.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    /**
     * 登录
     *
     * @param map
     * @return
     */
    public List<User> select(Map<String, Object> map);

    public User selectById(String userID);

    /**
     * 查看用户基本信息
     * @param userID
     * @return
     */
    public User selectByUserId(String userID);

    public int count(Map<String, Object> map);

    public void insert(User user);

    public void update(User user);

    /**
     * 动态权限
     *
     * @param userID
     * @return
     */
    public List<Function> getFunctionsByUserId(String userID);
}
