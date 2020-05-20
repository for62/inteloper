package com.oracle.intelagr.service.impl;

import com.oracle.intelagr.common.TreeModel;
import com.oracle.intelagr.entity.Function;
import com.oracle.intelagr.entity.Role;
import com.oracle.intelagr.entity.RoleFunction;
import com.oracle.intelagr.mapper.RoleFunctionMapper;
import com.oracle.intelagr.service.IRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleFunctionServiceImpl implements IRoleFunctionService {
    @Autowired
    RoleFunctionMapper roleFunctionMapper;

    @Override
    public List<RoleFunction> query(Map<String, Object> map) {
        return null;
    }

    /**
     * 查询角色树
     *
     * @param roleCode
     * @return
     */
    @Override
    public List<TreeModel> selectFunctions(String roleCode) {
        // 查询出所有的角色
        List<Map<String, String>> list = roleFunctionMapper.selectFunctions(roleCode);
        /**
         * 将Map转成TreeModel
         */
        List<TreeModel> treeList = new ArrayList<TreeModel>();
        /**
         * LinkedHashMap 有序
         */
        Map<String, TreeModel> map = new LinkedHashMap<String, TreeModel>();
        for (Map<String, String> m : list) {
            // 一级节点
            if (!map.containsKey(m.get("ModuleCode"))) {
                TreeModel treeModel = new TreeModel();
                treeModel.setId(m.get("ModuleCode"));
                treeModel.setText(m.get("ModuleName"));
                map.put(m.get("ModuleCode"), treeModel);
            }
            // 二级节点
            TreeModel treeModel = new TreeModel();
            treeModel.setId(m.get("FunctionCode"));
            treeModel.setText(m.get("FunctionName"));
            treeModel.setPid(m.get("ModuleCode"));
            if (m.get("checked") != null) {
                treeModel.setChecked("checked");
            }
            System.out.println("m.get(\"ModuleCode\")-------"+m.get("ModuleCode"));
//            try {
                map.get(m.get("ModuleCode")).getChildren().add(treeModel);

//            } catch (Exception e){
//                System.out.println(e.getMessage());
//            }

        }
        treeList.addAll(map.values());
        return treeList;
    }

    /**
     * 更新权限
     *
     * @param role
     */
    @Override
    @Transactional
    public void updateAuth(Role role) {
        // 1. 删除所有
        roleFunctionMapper.deleteRoleCode(role.getRoleCode());
        System.out.println("删除成功了没？");
        // 2. 插入所有
        for(Function f:role.getFunctions()){
            if (f.getFunctionCode().length()>6){ // 只插入二级节点
                RoleFunction roleFunction = new RoleFunction();
                roleFunction.setCreateUserId(role.getCreateUserId());
                roleFunction.setUpdateUserId(role.getCreateUserId());
                roleFunction.setCreateDate(new Date());
                roleFunction.setUpdateDate(new Date());
                roleFunction.setRoleCode(role.getRoleCode());
                roleFunction.setFunctionCode(f.getFunctionCode());
                System.out.println("插入成功了没abc？");
                roleFunctionMapper.insert(roleFunction);
            }
        }
        System.out.println("插入成功了没？");
    }
}
