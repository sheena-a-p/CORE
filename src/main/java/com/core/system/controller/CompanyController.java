package com.core.system.controller;
import com.core.system.form.CompanyForm;
import com.core.system.service.CompanyService;
import com.core.system.view.CompanyView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/* Controller to manage Company
 * Author Sheena AP
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/save")
    public void saveCompany(@Valid @RequestBody CompanyForm companyForm){
        try {
            if (companyForm.getCompanyId() == null){
                companyService.create(companyForm);
            }else {
                companyService.update(companyForm);
            }
        }catch (Exception e){
            throw  new RuntimeException("Saving company details failed !",e);
        }
    }

    @GetMapping("/view")
    public CompanyView viewCompany(@RequestParam (value = "companyId")Integer companyId){
        return companyService.getViewById(companyId);
    }
}
