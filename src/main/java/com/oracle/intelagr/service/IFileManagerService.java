package com.oracle.intelagr.service;

import com.oracle.intelagr.common.MfileModel;
import com.oracle.intelagr.entity.Mfile;

import java.util.List;

public interface IFileManagerService {
    public void save(Mfile mfile);

    public List<Mfile> getFileList(String bizType, String bizCode);

    public void save(MfileModel mfileModel);

    public void deleteFile(String bizType, String bizCode, String filePath);

    public Mfile getFile(String bizType, String bizCode, String filePath);
}
