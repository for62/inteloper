package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.Contract;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractMapper {

    public List<Contract> selectContractByCode(@Param("contractorCode") String contractorCode);
}
