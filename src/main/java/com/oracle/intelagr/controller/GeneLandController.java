package com.oracle.intelagr.controller;

import com.mysql.jdbc.UpdatableResultSet;
import com.oracle.intelagr.common.Constants;
import com.oracle.intelagr.common.JsonResult;
import com.oracle.intelagr.entity.GeneLandReg;
import com.oracle.intelagr.entity.Peasant;
import com.oracle.intelagr.entity.User;
import com.oracle.intelagr.service.IGeneLandService;
import com.oracle.intelagr.service.IServialNumService;
import com.oracle.intelagr.task.UpdateServialNumTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
@Controller
@RequestMapping("/geneLandReg")
public class GeneLandController {

    @Autowired
    IServialNumService iServialNumService;
    @Autowired
    IGeneLandService iGeneLandService;

    Logger logger = Logger.getLogger(GeneLandController.class);
    /**
     * 处理定时任务
     *
     * @param map
     * @return
     */
    @RequestMapping("/geneLand.do")
    public String geneLand(Map<String, Object> map) {
        /**
         * 基于定时任务的流水号
         */
        map.put("ptNumber", iServialNumService.getServialNum(Constants.BIZ_TYPE_PT));
        return "genelandreg/geneLandRegEdit";
    }

    /**
     * 添加地块
     *
     * @param map
     * @return
     */
    @RequestMapping("/addGeneLand.do")
    public String addGeneLand(Integer year, Map<String, Object> map) {
        map.put("year", year);
        return "genelandreg/geneLandRegDEdit";
    }

    /**
     * 根据证件类型以及证件号查询土地信息
     * @param contractorIDType
     * @param contractorID
     * @param year
     * @return
     */
    @RequestMapping("/getContractorInfo.do")
    @ResponseBody
    public JsonResult getContractorInfo(String contractorIDType,String contractorID,Integer year) {
        logger.debug("contractorIDType----------"+contractorIDType);
        List<Peasant> peasant = iGeneLandService.selectPeasantByContractorID(contractorIDType,contractorID,year);
        if (peasant == null){
            new JsonResult(false,"查无此人~");
        }
        JsonResult jsonResult = new JsonResult(true);
        for (Peasant peasant1 : peasant) {
            jsonResult.setData(peasant1);
        }
        return jsonResult;
    }

    /**
     * 保存
     * @return
     */
    @RequestMapping("/save.do")
    @ResponseBody
    public JsonResult save(@RequestBody GeneLandReg geneLandReg, HttpSession session) {
        User loginUser = (User)session.getAttribute("loginUser");
        geneLandReg.setCreateDate(new Date());
        geneLandReg.setUpdateDate(new Date());
        geneLandReg.setCreateUserId(loginUser.getUserID());
        geneLandReg.setUpdateUserId(loginUser.getUserID());
        iGeneLandService.save(geneLandReg);
        return new JsonResult(true);
    }

    /**
     * 修改信息
     */
    @RequestMapping("/editInput.do")
    public String editInput(){
        return null;
    }

}
