package com.core.system.view;
import com.core.system.entity.org.Department;
import lombok.Data;

@Data
public class DepartmentView extends Department {

    private String statusName;

    public DepartmentView(Department department){
        super(department);
    }
}
