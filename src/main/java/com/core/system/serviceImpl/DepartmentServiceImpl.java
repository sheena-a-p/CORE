package com.core.system.serviceImpl;
import com.core.system.entity.org.Department;
import com.core.system.entity.org.DepartmentStatusEnum;
import com.core.system.exception.NotFoundException;
import com.core.system.form.DepartmentForm;
import com.core.system.repository.DepartmentRepository;
import com.core.system.service.CrudService;
import com.core.system.service.DepartmentService;
import com.core.system.service.UserAccountService;
import com.core.system.util.SecurityUtil;
import com.core.system.view.DepartmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class DepartmentServiceImpl extends CrudService implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public void create(DepartmentForm departmentForm) {
        Department department = preCreate(new Department(departmentForm));
        try {
            departmentRepository.save(department);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(DepartmentForm departmentForm) {
        Department department = departmentRepository.findById(departmentForm.getDepartmentId()).orElseThrow(() ->{
            throw new NotFoundException("Department not found for id : "+departmentForm.getDepartmentId());
        });
        department = preUpdate(new Department(department,departmentForm));
        departmentRepository.save(department);
    }


    public Department preCreate(Department department) {
        department.setStatus(DepartmentStatusEnum.ACTIVE.getValue());
        department.setCompanyId(SecurityUtil.getCurrentCompanyId());
        department.setStaffCreated(SecurityUtil.getCurrentStaffId());
        department.setStaffModified(SecurityUtil.getCurrentStaffId());
        department.setDateCreated(new Date());
        department.setDateModified(new Date());
        return department;
    }

    public Department preUpdate(Department department) {
        department.setStaffModified(SecurityUtil.getCurrentStaffId());
        department.setDateModified(new Date());
        return department;
    }

    @Override
    public Department getById(Integer departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        return  department;
    }

    @Override
    public DepartmentView getViewById(Integer departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() ->{
            throw new NotFoundException("Department not found for id : "+departmentId);
        });
        return new DepartmentView(department);
    }
}
