package com.oracle.intelagr.service.impl;

import com.oracle.intelagr.entity.AreaDevision;
import com.oracle.intelagr.mapper.AreaDevisionMapper;
import com.oracle.intelagr.service.AreaDevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class AreaDevisionServiceImpl implements AreaDevisionService {

    @Autowired
    protected AreaDevisionMapper areaDevisionMapper;

    /*//通过证件id查询农户信息
    public AreaDevision getAreaDevisionByName(String townName, String countryName){
        return areaDivisionDao.getAreaDevisionByName(townName, countryName);
    }*/
    @Override
    public List<AreaDevision> queryCitys() {
        return areaDevisionMapper.queryCitys();
    }

    @Override
    public List<AreaDevision> queryTownsByCityCode(String cityCode) {
        Map map = new HashMap();
        map.put("cityCode", cityCode);
        return areaDevisionMapper.queryTownsByCityCode(map);
    }

    @Override
    public List<AreaDevision> queryCountrysByCityAndTownCode(String cityCode, String townCode) {
        Map map = new HashMap();
        map.put("cityCode", cityCode);
        map.put("townCode", townCode);
        return areaDevisionMapper.queryCountrysByCityAndTownCode(map);
    }

    @Override
    public AreaDevision getAreaDevision(String cityCode, String townCode, String countryCode) {
        Map map = new HashMap();
        map.put("cityCode", cityCode);
        map.put("townCode", townCode);
        map.put("countryCode", countryCode);
        List<AreaDevision> areas = areaDevisionMapper.getAreaDevision(map);
        return (areas == null || areas.size() == 0) ? new AreaDevision() : areas.get(0);
    }

    @Override
    public AreaDevision getAreaDevision(String cityCode, String townCode) {
        Map map = new HashMap();
        map.put("cityCode", cityCode);
        map.put("townCode", townCode);
        List<AreaDevision> areas = areaDevisionMapper.getAreaDevisionByCityAndTown(map);
        return (areas == null || areas.size() == 0) ? new AreaDevision() : areas.get(0);
    }
	
	/*@Override
	public String getAreaNameByCode( String codeKey,String code) {
		String sql = "SELECT DISTINCT "+codeKey+"Name, "+codeKey+"Code FROM m_areadivision WHERE "+codeKey+"Code='"+code+"'";
		List<Object[]> name = areaDivisionDao.queryBySQL(sql, null);
		return (name==null||name.size()==0)?"":name.get(0)[0]+"";
	}
	
	public String getCountryNameByCode( String codeKey,String code){
		if(code.indexOf(",")<0){
			return getAreaNameByCode(codeKey, code);
		}else if(code.trim().length()>1 && code.indexOf(",")>-1){
			String[] codes = code.split(",");
			String sql = "SELECT DISTINCT "+codeKey+"Name, "+codeKey+"Code FROM m_areadivision WHERE townCode='"+codes[0].trim()+"' and countryCode='"+codes[1].trim()+"'";
			List<Object[]> name = areaDivisionDao.queryBySQL(sql, null);
			return (name==null||name.size()==0)?"":name.get(0)[0]+"";
		}
		return "";
	}*/
}
