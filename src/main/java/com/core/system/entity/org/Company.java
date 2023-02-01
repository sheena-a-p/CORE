package com.core.system.entity.org;
import com.core.system.entity.System.AuditableModel;
import com.core.system.form.CompanyForm;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


/* Entity for company
 * Author Sheena AP
 */
@Entity
@Data
@NoArgsConstructor
@Table(schema = "org")
public class Company extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyCode;
    private String companyName;
    private Integer status;

    public Company(CompanyForm companyForm) {
        this.companyCode = companyForm.getCompanyCode();
        this.companyName = companyForm.getCompanyName();
    }

    public Company(Company company, CompanyForm companyForm) {
        this.companyId = company.getCompanyId();
        this.companyCode = companyForm.getCompanyCode();
        this.companyName = companyForm.getCompanyName();
        this.status = company.getStatus();
        this.setDateCreated(company.getDateCreated());
        this.setStaffCreated(company.getStaffCreated());
    }

    public Company(Company company) {
        this.companyId = company.getCompanyId();
        this.companyName = company.getCompanyName();
        this.companyCode = company.getCompanyCode();
        this.status = company.getStatus();
        this.setStaffCreated(company.getStaffCreated());
        this.setStaffModified(company.getStaffModified());
        this.setDateCreated(company.getDateCreated());
        this.setDateModified(company.getDateModified());
    }
}
