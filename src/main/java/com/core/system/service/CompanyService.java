package com.core.system.service;
import com.core.system.entity.System.Company;

public interface CompanyService {

    Company createCompany(Company company);

    Company updateCompany(Company company);

    Company getById(Integer companuId);

    Integer getCurrentCompanyId(Integer userId);
}
