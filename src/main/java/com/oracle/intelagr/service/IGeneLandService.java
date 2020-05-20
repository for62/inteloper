package com.oracle.intelagr.service;

import com.oracle.intelagr.entity.GeneLandReg;
import com.oracle.intelagr.entity.Peasant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IGeneLandService {
    public List<Peasant> selectPeasantByContractorID(@Param("contractorIDType") String contractorIDType, @Param("contractorID") String contractorID, Integer year);
    public void save(GeneLandReg geneLandReg);
}
