package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.AreaDevision;

import java.util.List;
import java.util.Map;

public interface AreaDevisionMapper {
    public List<AreaDevision> queryCitys();

    public List<AreaDevision> queryTownsByCityCode(Map map);

    public List<AreaDevision> queryCountrysByCityAndTownCode(Map map);

    public List<AreaDevision> getAreaDevision(Map map);

    public List<AreaDevision> getAreaDevisionByCityAndTown(Map map);
}
