package com.core.system.serviceImpl;
import com.core.system.entity.System.UserAccount;
import com.core.system.entity.System.UserAccountStatusEnum;
import com.core.system.exception.BadRequestException;
import com.core.system.exception.InvalidTokenException;
import com.core.system.exception.NotFoundException;
import com.core.system.exception.TokenExpiredException;
import com.core.system.form.UserLoginForm;
import com.core.system.repository.UserAccountRepository;
import com.core.system.security.config.SecurityConfig;
import com.core.system.service.CrudService;
import com.core.system.service.UserAccountService;
import com.core.system.util.GoogleAuthenticator;
import com.core.system.util.LanguageUtil;
import com.core.system.util.SecurityUtil;
import com.core.system.util.TokenGenerator;
import com.core.system.view.LoginView;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

@Service
public class UserAccountServiceImpl extends CrudService implements UserAccountService {

    private static final String PURPOSE_REFRESH_TOKEN = "REFRESH_TOKEN";
    private static final String PURPOSE_EMAIL_VERIFICATION_TOKEN = "EMAIL_VERIFICATION_TOKEN";
    private static final String PURPOSE_EMAIL_UPDATE_VERIFICATION_TOKEN = "EMAIL_UPDATE_VERIFICATION_TOKEN";
    private static final String PURPOSE_FORGOT_PASSWORD_TOKEN = "FORGOT_PASSWORD_TOKEN";
    public static final String PURPOSE_ACCESS_TOKEN = "ACCESS_TOKEN";

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    GoogleAuthenticator googleAuthenticator;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Override
    public void createUserAccount(UserAccount userAccount) {
        preCreate(userAccount);
        userAccountRepository.save(userAccount);
    }

    @Override
    public void updateUserAccount(UserAccount userAccount) {
        preUpdate(userAccount);
        userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount getById(Integer userId) {
        System.out.println("get By id  : "+userId);
        return userAccountRepository.findById(userId).orElseThrow();
    }

    public UserAccount preCreate(UserAccount userAccount) {
        userAccount.setStatus(100);
        userAccount.setDateCreated(new Date());
        userAccount.setDateModified(new Date());
        userAccount.setStaffCreated(SecurityUtil.getCurrentStaffId());
        userAccount.setStaffModified(SecurityUtil.getCurrentStaffId());
        userAccount.setCompanyId(SecurityUtil.getCurrentCompanyId());
        return  userAccount;
    }

    public UserAccount preUpdate(UserAccount userAccount) {
        userAccount.setDateModified(new Date());
        userAccount.setStaffModified(SecurityUtil.getCurrentStaffId());
        return userAccount;
    }

    @Override
    public LoginView userLogin(UserLoginForm userLoginForm) {
        UserAccount user = userAccountRepository.findByEmail(userLoginForm.getEmail()).orElseThrow();
        LOGGER.info("login:- logging in user with UserId : " + user.getUserId());

        if (!passwordEncoder.matches(userLoginForm.getPassword(), user.getPassword())) {
            throw new RuntimeException(languageUtil.getTranslatedText("invalid.credantials", null, "en"));
        }
        if (UserAccountStatusEnum.ACTIVE.getValue() != user.getStatus()) {
            try {
                tokenGenerator.verify(PURPOSE_EMAIL_VERIFICATION_TOKEN, user.getVerificationToken());
            } catch (InvalidTokenException e) {
                throw new BadRequestException(languageUtil.getTranslatedText("access.token.expired", null, "en"));
            } catch (TokenExpiredException e) {
                throw new BadRequestException("Token is expired");
            }
            throw new BadRequestException(languageUtil.getTranslatedText("email.not.verified", null, "en"));
        }
        String id = String.format("%010d", user.getUserId());
        TokenGenerator.Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, securityConfig.getAccessTokenExpiry());
        TokenGenerator.Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + user.getPassword(),
                securityConfig.getRefreshTokenExpiry());

        return  new LoginView(user.getEmail(),accessToken,refreshToken);
    }

    @Override
    public LoginView login(String token, String data) throws BadRequestException, IOException, GeneralSecurityException {
        String idToken = "";
        String googleRefreshToken = "";
        String email = "";
        String subject = "";

        if (token.isEmpty()) {
            JSONObject googleTokens = googleAuthenticator.getGoogleRefreshToken(data);
            if (googleTokens.has("id_token")) {
                idToken = googleTokens.get("id_token").toString();
            }
            if (googleTokens.has("refresh_token")) {
                googleRefreshToken = googleTokens.get("refresh_token").toString();
            }
            JSONObject idTokenData = googleAuthenticator.googleFilter(idToken);
            String name = "";
            String picture = "";
            if (idTokenData.has("email")) email = idTokenData.get("email").toString();
            if (idTokenData.has("name")) name = idTokenData.get("name").toString();
            if (idTokenData.has("picture")) picture = idTokenData.get("picture").toString();
            boolean isNewUser = false;
            UserAccount user = userAccountRepository.findByEmailAndStatus(email, 1).orElseThrow(() -> {
                throw new NotFoundException("user not found!");
            });
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@innovaturelabs.com$";
            if (!email.matches(regex)) {
                throw new BadRequestException(languageUtil.getTranslatedText("not.under.@innovaturelabs", null, "en"));
            }
            if (idTokenData.has("sub")) subject = idTokenData.get("sub").toString();
            if (user.getPassword() == null) user.setPassword(passwordEncoder.encode(subject));
            user.setDateModified(new Date());
            user.setGoogleRefreshToken(googleRefreshToken);
            user = userAccountRepository.save(user);
            String id = String.format("%10d", user.getUserId());
            TokenGenerator.Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, securityConfig.getAccessTokenExpiry());
            TokenGenerator.Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + subject, securityConfig.getRefreshTokenExpiry());
            Integer callFromLogin = 0;
            return new LoginView(user.getEmail(), accessToken, refreshToken);
        } else {
            JSONObject googleTokens = googleAuthenticator.getGoogleRefreshToken(data);
            if (googleTokens.has("id_token")) {
                idToken = googleTokens.get("id_token").toString();
            }
            if (googleTokens.has("refresh_token")) {
                googleRefreshToken = googleTokens.get("refresh_token").toString();
            }
            JSONObject idTokenData = googleAuthenticator.googleFilter(idToken);
            String name = "";
            String picture = "";
            if (idTokenData.has("email")) email = idTokenData.get("email").toString();
            if (idTokenData.has("name")) name = idTokenData.get("name").toString();
            if (idTokenData.has("picture")) picture = idTokenData.get("picture").toString();
            boolean isNewUser = false;
            UserAccount user = userAccountRepository.findByEmailAndStatus(email, 1).orElseThrow(() -> {
                throw new NotFoundException("user not found!");
            });
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@innovaturelabs.com$";
            if (!email.matches(regex)) {
                throw new BadRequestException(languageUtil.getTranslatedText("not.under.@innovaturelabs", null, "en"));
            }
            if (idTokenData.has("sub")) subject = idTokenData.get("sub").toString();
            if (user.getPassword() == null) user.setPassword(subject);
            user = userAccountRepository.save(user);
            String id = String.format("%10d", user.getUserId());
            TokenGenerator.Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, securityConfig.getAccessTokenExpiry());
            TokenGenerator.Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + subject, securityConfig.getRefreshTokenExpiry());
            Integer callFromLogin = 0;
            return new LoginView(user.getEmail(), accessToken, refreshToken);
        }
    }
}
