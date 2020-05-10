package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.RoleFunction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleFunctionMapper {
    /**
     * 查询角色树
     * @param roleCode
     * @return
     */
    public List<Map<String,String>>  selectFunctions(@Param("roleCode") String roleCode);
    public List<RoleFunction> select(Map<String, Object> map);

    public void insert(RoleFunction rf);

    public void deleteRoleCode(String roleCode);
}
