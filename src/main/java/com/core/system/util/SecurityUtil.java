package com.core.system.util;
import com.core.system.security.AccessTokenUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil(){}

    private static AccessTokenUserDetails currentUserDetails(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth==null|| !auth.isAuthenticated()){
            return  null;
        }
        Object principal =auth.getPrincipal();
        if(!(principal instanceof AccessTokenUserDetails)){
            return  null;
        }
        return ((AccessTokenUserDetails) principal);
    }

    public static Integer getCurrentUserId(){
        return currentUserDetails().userId;
    }

    public static Integer getCurrentStaffId(){
        return currentUserDetails().staffId;
    }

    public static Integer getCurrentCompanyId(){
        return currentUserDetails().companyId;
    }
}