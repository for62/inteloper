package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.GeneLandDetail;
import com.oracle.intelagr.entity.GeneLandRegD;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneLandDetailMapper {
   public void insert(GeneLandDetail geneLandDetail);
}
