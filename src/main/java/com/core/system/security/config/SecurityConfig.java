package com.core.system.security.config;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Duration;

public class SecurityConfig {

    @NotNull(message ="security.token-generator-password cannot be null")
    @Pattern(regexp = "[\\x00-\\x7F]{16}", message = "security.token-generator-password must be exactly 16 ASCII characters")
            private String tokenGeneratorPassword;

    @NotNull(message = "security.token-generator-salt cannot be null")
    @Pattern(regexp = "[0-9A-Fa-f]{16}", message = "security.token-generator-password must be exactly 16 digit hexadecimal")
    private String tokenGeneratorSalt;

    @NotNull
    private Duration accessTokenExpiry = Duration.ofMinutes(30);

    @NotNull
    private Duration refreshTokenExpiry = Duration.ofDays(7);

    public String getTokenGeneratorPassword() {
        return tokenGeneratorPassword;
    }

    public void setTokenGeneratorPassword(String tokenGeneratorPassword) {
        this.tokenGeneratorPassword = tokenGeneratorPassword;
    }

    public String getTokenGeneratorSalt() {
        return tokenGeneratorSalt;
    }

    public void setTokenGeneratorSalt(String tokenGeneratorSalt) {
        this.tokenGeneratorSalt = tokenGeneratorSalt;
    }

    public Duration getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public void setAccessTokenExpiry(Duration accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }

    public Duration getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(Duration refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
}
