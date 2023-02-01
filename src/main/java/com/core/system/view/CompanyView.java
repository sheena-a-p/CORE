package com.core.system.view;
import com.core.system.entity.org.Company;
import lombok.Data;

@Data
public class CompanyView extends Company {

    private String statusName;

    public CompanyView(Company company){
        super(company);
    }
}
