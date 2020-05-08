package com.oracle.intelagr.service;

import com.oracle.intelagr.entity.Company;

import java.util.List;

public interface ICompanyService {
    public Company getCompany(String companyCode);

    public List<Company> getCompanyListByCompanyType(String companyType);
}
