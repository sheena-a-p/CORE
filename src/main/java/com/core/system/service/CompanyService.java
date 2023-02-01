package com.core.system.service;
import com.core.system.entity.org.Company;
import com.core.system.form.CompanyForm;
import com.core.system.view.CompanyView;

public interface CompanyService {

    void create(CompanyForm companyForm);

    void update(CompanyForm companyForm);

    Company getById(Integer companyId);

    CompanyView getViewById(Integer companyId);

}
