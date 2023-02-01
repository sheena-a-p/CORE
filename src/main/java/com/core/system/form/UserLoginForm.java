package com.core.system.form;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;

/* Model class for User Login form
 * Author Sheena AP
 */
@Data
public class UserLoginForm {

    @NonNull
    private String email;
    @NotNull
    private String password;

    public UserLoginForm(){}

}
