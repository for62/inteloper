package com.oracle.intelagr.mapper;

import com.oracle.intelagr.entity.Mfile;

import java.util.List;
import java.util.Map;

public interface FileMapper {
    public void insert(Mfile file);

    public void update(Mfile file);

    public List<Mfile> select(Map<String, Object> map);
}
