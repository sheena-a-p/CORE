package com.core.system.view;
import com.core.system.util.TokenGenerator;
import com.core.system.security.json.Json;
import java.util.Date;

public class LoginView{

    public static class TokenView {

        private final String value;
        @Json.DateTimeFormat
        private final Date expiry;
        @Json.DateTimeFormat
        private final Date now;

        public TokenView(TokenGenerator.Token token) {
            this.value = token.value;
            this.expiry = new Date(token.expiry);
            this.now = new Date();
        }

        public TokenView(String value, long expiry) {
            this.value = value;
            this.expiry = new Date(expiry);
            this.now = new Date();
        }

        public String getValue() {
            return value;
        }

        public Date getExpiry() {
            return expiry;
        }

        public Date getNow() {
            return now;
        }
    }

    private final TokenView accessToken;
    private final TokenView refreshToken;
    private final String email;

    public LoginView(String email, TokenGenerator.Token accessToken, TokenGenerator.Token refreshToken) {
        this.accessToken = new TokenView(accessToken);
        this.refreshToken = new TokenView(refreshToken);
        this.email = email;
    }

    public TokenView getAccessToken() {
        return accessToken;
    }

    public TokenView getRefreshToken() {
        return refreshToken;
    }

    public String getEmail() {
        return email;
    }
}
