package com.oracle.intelagr.service;

import com.oracle.intelagr.entity.Peasant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

public interface IGeneLandService {
    public Peasant selectPeasantByContractorID(@Param("contractorIDType") String contractorIDType, @Param("contractorID") String contractorID);
}
