package com.core.system.serviceImpl;
import com.core.system.entity.System.CompanyStatusEnum;
import com.core.system.entity.org.Company;
import com.core.system.exception.NotFoundException;
import com.core.system.form.CompanyForm;
import com.core.system.repository.CompanyRepository;
import com.core.system.service.CompanyService;
import com.core.system.service.CrudService;
import com.core.system.service.UserAccountService;
import com.core.system.util.SecurityUtil;
import com.core.system.view.CompanyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class CompanyServiceImpl extends CrudService implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public void create(CompanyForm companyForm) {
        Company company = preCreate(new Company(companyForm));
        try {
            companyRepository.save(company);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(CompanyForm companyForm) {
        Company company = companyRepository.findById(companyForm.getCompanyId()).orElseThrow(() ->{
            throw new NotFoundException("Company not found for id : "+companyForm.getCompanyId());
        });
        company = preUpdate(new Company(company,companyForm));
        companyRepository.save(company);
    }


    public Company preCreate(Company company) {
        company.setStatus(CompanyStatusEnum.ACTIVE.getValue());
        company.setStaffCreated(SecurityUtil.getCurrentStaffId());
        company.setStaffModified(SecurityUtil.getCurrentStaffId());
        company.setDateCreated(new Date());
        company.setDateModified(new Date());
        return company;
    }

    public Company preUpdate(Company company) {
        company.setStaffModified(SecurityUtil.getCurrentStaffId());
        company.setDateModified(new Date());
        return company;
    }

    @Override
    public Company getById(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->{
            throw new NotFoundException("Company not found for id : "+companyId);
        });
        return  company;
    }

    @Override
    public CompanyView getViewById(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() ->{
            throw new NotFoundException("Company not found for id : "+companyId);
        });
        return  new CompanyView(company);
    }
}
