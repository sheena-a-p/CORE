package com.core.system.controller;
import com.core.system.entity.System.UserAccount;
import com.core.system.exception.BadRequestException;
import com.core.system.form.UserLoginForm;
import com.core.system.service.UserAccountService;
import com.core.system.view.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;

/* Controller to manage User Account
 * Author Sheena AP
 */
@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginView userAccountLogin(@Valid @RequestBody UserLoginForm userLoginForm) {
        return userAccountService.userLogin(userLoginForm);
    }

    @PostMapping("/login/gmail")
    public LoginView gmailLogin(@RequestParam(value = "token") String token, @Valid @RequestBody String data) throws IOException, GeneralSecurityException {
        if (data.isEmpty()) {
            throw new BadRequestException("String is empty");
        }
        return userAccountService.login(token, data);
    }

    @PostMapping("/save")
    public void saveUserAccount(@Valid @RequestBody UserAccount userAccount){
        try {
            if (userAccount.getUserId() == null){
                userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
                userAccountService.create(userAccount);
            }else {
                userAccountService.update(userAccount);
            }
        }catch (Exception e){
            throw  new RuntimeException("Saving user details failed !",e);
        }
    }
}
