package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.Peasant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeasantMapper {

    public Peasant selectByContractorID(@Param("contractorIDType")String contractorIDType,@Param("contractorID")String contractorID);
}
