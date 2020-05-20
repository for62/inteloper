package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.Peasant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeasantMapper {

    public List<Peasant> selectByContractorID(@Param("contractorIDType")String contractorIDType, @Param("contractorID")String contractorID);
}
