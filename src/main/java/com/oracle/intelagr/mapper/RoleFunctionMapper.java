package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.RoleFunction;

import java.util.List;
import java.util.Map;

public interface RoleFunctionMapper {
    public List<RoleFunction> select(Map<String, Object> map);

    public void insert(RoleFunction rf);

    public void deleteRoleCode(String roleCode);
}
