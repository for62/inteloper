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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
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
     * 查询回显
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/editRoleInit.do")
    public String editRoleInit(@RequestParam("id") Integer id,Map map){
        Role role = iRoleService.queryById(id);
        map.put("roleEdit",role);
        return "role/editRole";
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping("updateRole.do")
    @ResponseBody
    public void updateRole(Role role){
        System.out.println("role.getRemark()---------"+role.getRemark());
        iRoleService.update(role);
    }

    /**
     * 删除角色
     * @return
     */
    @RequestMapping("delete.do")
    @ResponseBody
    public void delete(@RequestParam("id") Integer id){
        System.out.println("---------------"+id);
        iRoleService.delete(id);
    }

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
    public String save(Role role, HttpSession session) {
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
            return "redirect:/role/list.do";
        }else{
            return "redirect:/pages/login.jsp";
        }
    }

    /**
     * 创建角色树
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("roleAuth.do")
    public String roleAuth(Integer id,Map map){
        logger.debug("进来了~");
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
        System.out.println("role.getFunctions()----------------"+role.getFunctions());
        System.out.println("role.getRoleCode()----------------"+role.getRoleCode());
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
