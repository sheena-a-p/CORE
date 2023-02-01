package com.core.system.service;
import com.core.system.entity.org.Department;
import com.core.system.form.DepartmentForm;
import com.core.system.view.DepartmentView;

public interface DepartmentService {

    void create(DepartmentForm departmentForm);

    void update(DepartmentForm departmentForm);

    Department getById(Integer departmentId);

    DepartmentView getViewById(Integer departmentId);

}
