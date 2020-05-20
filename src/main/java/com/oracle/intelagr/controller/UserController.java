package com.oracle.intelagr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.oracle.intelagr.common.JsonResult;
import com.oracle.intelagr.entity.Function;
import com.oracle.intelagr.entity.Role;
import com.oracle.intelagr.entity.User;
import com.oracle.intelagr.service.IRoleService;
import com.oracle.intelagr.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@SessionAttributes(names={"userIDReset","loginUser"})
public class UserController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IRoleService iRoleService;
    Logger logger = Logger.getLogger(UserController.class);

    /**
     * 分页查询
     *
     * @param map
     * @return
     * @RequestParam 标识入参
     */
    @RequestMapping("/list.do")
    public String userList(@RequestParam Map map, Map request, @RequestParam(value = "page", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        System.out.println("-------------------------------------------");
        logger.debug("map:"+map);
        System.out.println(pageNum+"---"+pageSize);
        System.out.println("-------------------------------------------");
        PageInfo<User> pageInfo = iUserService.queryForPage(map, pageNum, pageSize);
        System.out.println(pageInfo+"-------------");
        request.put("pageModel", pageInfo);
        request.put("data", map);
        return "user/userList";
    }

    /**
     * 添加用户
     * @param map
     * @return
     */
    @RequestMapping("/add.do")
    public String add(Map map) {
        /**
         * 查询出角色列表
         */
        List<Role> list = iRoleService.selectAll();
        try {
            /**
             * Json处理：ObjectMapper()
             */
            map.put("roleList",new ObjectMapper().writeValueAsString(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "user/addUser";
    }

    /**
     * 修改用户基本信息
     * @return
     */
    @RequestMapping("edit.do")
    public String edit(String userID,Map<String,Object> map){
        map.put("user",iUserService.selectById(userID));
        return "user/basicInfoEdit";
    }

    /**
     * 查看用户基本信息
     * @return
     */
    @RequestMapping("detail.do")
    public String detail(@RequestParam(value = "userID")String userID,Map map){
        map.put("user",iUserService.selectByUserID(userID));
        return "user/basicInfoBrowse";
    }

    /**
     * 添加用户以及角色
     * @return
     */
    @RequestMapping("/save.do")
    @ResponseBody
    public JsonResult save(User user,HttpSession session,String[] role) {
        /**
         * 获取登录用户信息
         */
        User loginUser = (User)session.getAttribute("loginUser");
        if(loginUser!=null){
            user.setCreateDate(new Date());
            user.setUpdateDate(new Date());
            user.setCreateUserId(loginUser.getUserID());
            user.setUpdateUserId(loginUser.getUserID());
            if (!"02".equals(user.getUserType())){ // 非企业用户
                user.setCompanyCode(null);
            }
            iUserService.save(user,role);
            return new JsonResult(true);
        }
        return new JsonResult(false,"添加失败");
    }

    /**
     * 删除用户---逻辑删除
     * @return
     */
    @RequestMapping("delete.do")
    public String delete(@RequestParam(value = "userID")String userID,Map map){
        iUserService.delete(userID);
        return "redirect:/user/list.do";
    }

    /**
     *  启用
     * @return
     */
    @RequestMapping("startUse.do")
    @Transactional
    public String startUse(@RequestParam(value = "userID") String userID,Map map){
        iUserService.startUse(userID);
        return "redirect:/user/list.do";
    }

    /**
     * 禁用
     * @return
     */
    @RequestMapping("endUse.do")
    @Transactional
    public String endUse(@RequestParam(value = "userID")String userID,Map map){
        iUserService.endUse(userID);
        return "redirect:/user/list.do";
    }

    @RequestMapping("/login.do")
    public String login(User user, Map<String, Object> map) {
        logger.debug(user);
        User u = iUserService.login(user);
        if (u == null) {
            return "redirect:/pages/login.jsp";
        } else {
            Map<String, List<Function>> funs = iUserService.getFunctions(u.getUserID());
            map.put("functions", funs);
            map.put("loginUser", u); // 将用户存储到Session中
            return "main";
        }
    }

    /**
     * 修改用户基本信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public JsonResult update(User user,HttpSession session) {
        /**
         * 获取登录用户信息
         */
        User loginUser = (User)session.getAttribute("loginUser");
        if(loginUser!=null){
            user.setUpdateDate(new Date());
            user.setUpdateUserId(loginUser.getUserID()); // 登录用户修改
            iUserService.update(user);
            return new JsonResult(true);
        }
        return new JsonResult(false,"修改失败");
    }

    @RequestMapping("resetPwdInit.do")
    public String resetPwdInit(@RequestParam(value = "userID") String userID,Map map){
        map.put("userIDReset",userID);
        return "user/updatePwd";
    }

    @RequestMapping("updatePwd.do")
    @ResponseBody
    @Transactional
    public JsonResult resetPwdInit(@RequestBody Map map,Map request,HttpSession session){
        User user = new User();
        user.setUserID((String)session.getAttribute("userIDReset"));
        user.setPassword((String)map.get("oldPwd"));
        if(iUserService.login(user)!=null){ // 原密码匹配可以修改
            /**
             * 先删除后插入
             */
            User user1 = iUserService.selectByUserID((String)session.getAttribute("userIDReset"));
            iUserService.delete((String)session.getAttribute("userIDReset"));
            user1.setPassword((String)map.get("newPassword"));
            iUserService.update(user1);
            return new JsonResult(true);
        }
        return new JsonResult(false,"修改密码失败~");
    }

}
