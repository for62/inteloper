package com.oracle.intelagr.service.impl;

import com.oracle.intelagr.entity.Contract;
import com.oracle.intelagr.entity.Peasant;
import com.oracle.intelagr.mapper.ContractMapper;
import com.oracle.intelagr.mapper.PeasantMapper;
import com.oracle.intelagr.service.IGeneLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GeneLandServiceImpl implements IGeneLandService {
    @Autowired
    ContractMapper contractMapper;
    @Autowired
    PeasantMapper peasantMapper;

    @Override
    public Peasant selectPeasantByContractorID(String contractorIDType, String contractorID) {
        Peasant peasant = peasantMapper.selectByContractorID(contractorIDType,contractorID);
        if(peasant == null){
            return null;
        }
        List<Contract> list = contractMapper.selectContractByCode(peasant.getContractorCode());
        peasant.setContractList(list);
        return peasant;
    }
}
