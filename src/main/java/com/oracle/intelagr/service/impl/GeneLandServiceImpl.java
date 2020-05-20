package com.oracle.intelagr.service.impl;

import com.oracle.intelagr.entity.*;
import com.oracle.intelagr.mapper.*;
import com.oracle.intelagr.service.IGeneLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class GeneLandServiceImpl implements IGeneLandService {
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    PeasantMapper peasantMapper;
    @Autowired
    CommonDataService commonDataService;
    @Autowired
    GeneLandRegMapper geneLandRegMapper;
    @Autowired
    GeneLandRegDMapper geneLandRegDMapper;
    @Autowired
    GeneLandDetailMapper geneLandDetailMapper;

    /**
     * 普通土地备案
     * @param geneLandReg
     */
    @Override
    @Transactional
    public void save(GeneLandReg geneLandReg) {
        // 保存主表
        geneLandRegMapper.insert(geneLandReg);
        // 保存子表
        for (GeneLandRegD geneLandRegD:geneLandReg.getGeneLandRegDList()){
            geneLandRegD.setHid(geneLandReg.getId());
            geneLandRegD.setCreateDate(new Date());
            geneLandRegD.setCreateUserId(geneLandReg.getCreateUserId());
            geneLandRegD.setUpdateDate(new Date());
            geneLandRegD.setUpdateUserId(geneLandReg.getUpdateUserId());
            geneLandRegD.setCityCode("230184");
            geneLandRegDMapper.insert(geneLandRegD);
        }
        // 保存详情表(要先查询)
        for(GeneLandRegD geneLandRegD:geneLandReg.getGeneLandRegDList() ){
            // 通过证件类型与证件号码获得土地信息
            List<Peasant> peasant = peasantMapper.selectByContractorID(geneLandRegD.getIdType(),geneLandRegD.getContractorID());
            // 根据农户查找详细信息
            for(Peasant p:peasant){
                List<Contract> list = contractMapper.selectContractByCode(p.getContractorCode());
                for(Contract c:list){
                    GeneLandDetail geneLandDetail = new GeneLandDetail();
                    geneLandDetail.setId(geneLandRegD.getId());
                    geneLandDetail.setUpdateUserId(geneLandRegD.getUpdateUserId());
                    geneLandDetail.setUpdateDate(geneLandRegD.getUpdateDate());
                    geneLandDetail.setTownCode(geneLandRegD.getTownCode());
                    geneLandDetail.setMeasurementMu(c.getMeasurementMu());
                    geneLandDetail.setLandType(c.getLandType());
                    geneLandDetail.setLandClass(c.getLandClass());
                    geneLandDetail.setGroupName(geneLandRegD.getGroupName());
                    geneLandDetail.setCreateDate(new Date());
                    geneLandDetail.setCreateUserId(geneLandRegD.getCreateUserId());
                    geneLandDetail.setCountryCode(geneLandRegD.getCountryCode());
                    geneLandDetail.setCityCode(geneLandRegD.getCityCode());
                    geneLandDetailMapper.insert(geneLandDetail);
                }
            }
        }
    }

    @Override
    public List<Peasant> selectPeasantByContractorID(String contractorIDType, String contractorID,Integer year) {
        List<Peasant> peasantList = peasantMapper.selectByContractorID(contractorIDType,contractorID);
        System.out.println(peasantList);
        if(peasantList == null){
            return null;
        }
        float zmj = 0f;
        // 查询出每个合同的地块
        for(Peasant peasant:peasantList){
            List<Contract> list = contractMapper.selectContractByCode(peasant.getContractorCode());
            for(Contract c:list){
                c.setLandClassName(commonDataService.getCommonData("PlowlandType",c.getLandClass()).getCodeValue());
                c.setLandTypeName(commonDataService.getCommonData("PlowlandClass",c.getLandType()).getCodeValue());
                zmj+=c.getMeasurementMu();
            }
            peasant.setContractList(list);
            peasant.setZmj(zmj);
            // 以下数据后续修改
            peasant.setYba(geneLandRegMapper.getArc(contractorIDType,contractorID,year));
            peasant.setKba(peasant.getZmj()-peasant.getYba());
        }
        return peasantList;
    }
}
