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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@SessionAttributes("loginUser")
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

        logger.debug("map:"+map);
        PageInfo pageInfo = iUserService.queryForPage(map, pageNum, pageSize);
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

    @RequestMapping("/login.do")
    public String login(User user, Map<String, Object> map) {
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
}
