package com.oracle.intelagr.service;

import com.oracle.intelagr.common.TreeModel;
import com.oracle.intelagr.entity.Role;
import com.oracle.intelagr.entity.RoleFunction;

import java.util.List;
import java.util.Map;

public interface IRoleFunctionService {
    public List<RoleFunction> query(Map<String, Object> map);

    /**
     * 角色树
     * @param roleCode
     * @return
     */
    public List<TreeModel> selectFunctions(String roleCode);

    /**
     * 更新权限
     * @param role
     */
    public void updateAuth(Role role);
}
