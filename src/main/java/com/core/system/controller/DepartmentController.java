package com.core.system.controller;
import com.core.system.form.DepartmentForm;
import com.core.system.service.DepartmentService;
import com.core.system.view.DepartmentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/* Controller to manage Department
 * Author Sheena AP
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save")
    public void saveDepartment(@Valid @RequestBody DepartmentForm departmentForm){
        try {
            if (departmentForm.getDepartmentId() == null){
                departmentService.create(departmentForm);
            }else {
                departmentService.update(departmentForm);
            }
        }catch (Exception e){
            throw  new RuntimeException("Saving department failed !",e);
        }
    }

    @GetMapping("/view")
    public DepartmentView viewDepartment(@RequestParam (value = "departmentId")Integer departmentId){
        return departmentService.getViewById(departmentId);
    }
}
