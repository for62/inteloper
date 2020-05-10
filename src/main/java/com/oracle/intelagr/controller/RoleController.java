package com.oracle.intelagr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.oracle.intelagr.common.JsonResult;
import com.oracle.intelagr.common.TreeModel;
import com.oracle.intelagr.entity.Role;
import com.oracle.intelagr.entity.User;
import com.oracle.intelagr.service.IRoleFunctionService;
import com.oracle.intelagr.service.IRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    IRoleService iRoleService;
    @Autowired
    IRoleFunctionService iRoleFunctionService;

    org.apache.log4j.Logger logger = Logger.getLogger(UserController.class);

    /**
     * 分页查询---角色
     * @param map
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    public String userList(@RequestParam Map map, Map request, @RequestParam(value = "page", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        logger.debug("map:"+map);
        PageInfo pageInfo = iRoleService.queryForPage(map, pageNum, pageSize);
        request.put("pageModel", pageInfo);
        request.put("data", map);
        return "role/roleList";
    }

    /**
     * 添加角色---界面
     * @param map
     * @return
     */
    @RequestMapping("/add.do")
    public String add(Map map) {
        return "role/addRole";
    }

    /**
     * 添加角色
     * @return
     */
    @RequestMapping("/save.do")
    @ResponseBody
    public JsonResult save(Role role, HttpSession session) {
        /**
         * 获取登录用户信息
         */
        User loginUser = (User)session.getAttribute("loginUser");
        if(loginUser!=null){
            role.setCreateDate(new Date());
            role.setUpdateDate(new Date());
            role.setCreateUserId(loginUser.getUserID());
            role.setUpdateUserId(loginUser.getUserID());
            iRoleService.save(role);
            return new JsonResult(true);
        }
        return new JsonResult(false,"添加失败");
    }

    /**
     * 创建角色树
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("roleAuth.do")
    public String roleAuth(Integer id,Map map){
        /**
         * 查询角色
         */
        Role role = iRoleService.queryById(id);
        map.put("role",role);
        List<TreeModel> list = iRoleFunctionService.selectFunctions(role.getRoleCode());
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            map.put("jsonData",objectMapper.writeValueAsString(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "role/roleAuth";
    }

    /**
     *  保存选中的角色权限
     * @return
     */
    @RequestMapping("saveRoleAuth.do")
    @ResponseBody
    public JsonResult saveRoleAuth(@RequestBody Role role,HttpSession session){
        try{
            User loginUser = (User)session.getAttribute("loginUser");
            role.setCreateUserId(loginUser.getUserID());
            iRoleFunctionService.updateAuth(role);
        }catch (Exception e){
            return new JsonResult(false,"更新失败");
        }
        return new JsonResult(true);
    }
}
