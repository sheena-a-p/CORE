package com.core.system.serviceImpl;
import com.core.system.entity.System.Company;
import com.core.system.repository.CompanyRepository;
import com.core.system.service.CompanyService;
import com.core.system.service.CrudService;
import com.core.system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl extends CrudService implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public Company createCompany(Company company) {
        return null;
    }

    @Override
    public Company updateCompany(Company company) {
        return null;
    }

    @Override
    public Company getById(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        return  company;
    }

    @Override
    public Integer getCurrentCompanyId(Integer userId) {
        return userAccountService.getById(userId).getCompanyId();
    }
}
