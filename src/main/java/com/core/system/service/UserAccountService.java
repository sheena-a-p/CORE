package com.core.system.service;
import com.core.system.entity.System.UserAccount;
import com.core.system.form.UserLoginForm;
import com.core.system.view.LoginView;
import com.core.system.exception.BadRequestException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface UserAccountService {

    void create(UserAccount userAccount);

    void update(UserAccount userAccount);

    LoginView userLogin(UserLoginForm userLoginForm);

    UserAccount getById(Integer userId);

    LoginView login(String token,String data) throws BadRequestException, IOException, GeneralSecurityException;

}
