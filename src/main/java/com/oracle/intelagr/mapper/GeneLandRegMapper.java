package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.AreaDevision;
import com.oracle.intelagr.entity.GeneLandReg;
import com.oracle.intelagr.entity.GeneLandRegD;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GeneLandRegMapper {
   public void insert(GeneLandReg geneLandReg);

   public Float getArc(String typeID,String contractorID,Integer year);
}
