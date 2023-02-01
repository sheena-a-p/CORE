package com.core.system.security;
import com.core.system.entity.System.UserAccount;
import com.core.system.entity.System.UserAccountStatusEnum;
import com.core.system.util.LanguageUtil;
import com.core.system.util.TokenGenerator;
import com.core.system.exception.InvalidTokenException;
import com.core.system.exception.TokenExpiredException;
import com.core.system.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AccessTokenUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    public static final String PURPOSE_ACCESS_TOKEN = "ACCESS_TOKEN";

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LanguageUtil languageUtil;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (!PURPOSE_ACCESS_TOKEN.equals(token.getCredentials())) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        final TokenGenerator.Status status;
        try {
            status = tokenGenerator.verify(PURPOSE_ACCESS_TOKEN, token.getPrincipal().toString());
        } catch (InvalidTokenException e) {
            throw new UsernameNotFoundException("Invalid access token", e);
        } catch (TokenExpiredException e) {
            throw new UsernameNotFoundException("Access token expired", e);
        }
        int userId = Integer.parseInt(status.data.trim());
        UserAccount userAccount = userAccountRepository.findByUserId(userId).orElse(null);
        if (UserAccountStatusEnum.ACTIVE.getValue() != userAccount.getStatus()) {
            throw new UsernameNotFoundException(languageUtil.getTranslatedText("access.token.expired", null, "en"));
        }
        return new AccessTokenUserDetails(userId,userAccount.getStaffId(),userAccount.getCompanyId());
    }
}