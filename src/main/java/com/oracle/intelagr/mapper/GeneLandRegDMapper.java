package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.GeneLandReg;
import com.oracle.intelagr.entity.GeneLandRegD;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneLandRegDMapper {
   public void insert(GeneLandRegD geneLandRegD);
}
