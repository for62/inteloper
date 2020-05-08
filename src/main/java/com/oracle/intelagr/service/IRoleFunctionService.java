package com.oracle.intelagr.service;

import com.oracle.intelagr.entity.RoleFunction;

import java.util.List;
import java.util.Map;

public interface IRoleFunctionService {
    public List<RoleFunction> query(Map<String, Object> map);
}
