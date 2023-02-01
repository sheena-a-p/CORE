package com.core.system.entity.org;
import com.core.system.entity.System.AuditableModel;
import com.core.system.form.DepartmentForm;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(schema = "org")
public class Department extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;
    private String departmentName;
    private String departmentCode;
    private Integer status;
    private Integer companyId;

    public Department(Department department){
        this.departmentId = department.getDepartmentId();
        this.departmentName = department.getDepartmentName();
        this.departmentCode = department.getDepartmentCode();
        this.status = department.getStatus();
        this.companyId = department.getCompanyId();
        this.setDateCreated(department.getDateCreated());
        this.setDateModified(department.getDateModified());
        this.setStaffCreated(department.getStaffCreated());
        this.setStaffModified(department.getStaffModified());
    }

    public Department(DepartmentForm departmentForm){
        this.departmentName = departmentForm.getDepartmentName();
        this.departmentCode = departmentForm.getDepartmentCode();
    }

    public Department(Department department, DepartmentForm departmentForm){
        this.departmentId = department.getDepartmentId();
        this.departmentName = departmentForm.getDepartmentName();
        this.departmentCode = departmentForm.getDepartmentCode();
        this.status = department.getStatus();
        this.companyId = department.getCompanyId();
        this.setDateCreated(department.getDateCreated());
        this.setStaffCreated(department.getStaffCreated());
    }
}
