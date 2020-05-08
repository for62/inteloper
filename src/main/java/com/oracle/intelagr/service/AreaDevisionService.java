package com.oracle.intelagr.service;

import com.oracle.intelagr.entity.AreaDevision;

import java.util.List;


public interface AreaDevisionService {

    //public AreaDevision getAreaDevisionByName(String townName, String countryName);

    public AreaDevision getAreaDevision(String cityCode, String townCode, String countryCode);

    public List<AreaDevision> queryCitys();

    public List<AreaDevision> queryTownsByCityCode(String cityCode);

    public List<AreaDevision> queryCountrysByCityAndTownCode(String cityCode, String townCode);

    public AreaDevision getAreaDevision(String cityCode, String townCode);
	
/*	public String getAreaNameByCode(String codeKey, String code);
	
	public String getCountryNameByCode(String codeKey, String code);*/
}