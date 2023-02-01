package com.core.system.util;
import com.core.system.exception.BadRequestException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;


@Component
public class GoogleAuthenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAuthenticator.class);
    @Value("${client.id}")
    private String clientId;
    @Value("${client.secret}")
    private String clientSecret;
    @Value("${grant.type.code}")
    private String grantType;
    @Value("${redirect.uri}")
    private String redirectUri;
    @Value("${access.type}")
    private String accessType;

    public JSONObject googleFilter(String idTokenString) throws GeneralSecurityException, IOException {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(Collections.singletonList(clientId)).build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            return new JSONObject(payload);
        } else {
            throw new BadRequestException("invalid Login");
        }
    }

    public JSONObject getGoogleRefreshToken(String code) {
        LOGGER.info("Inside google refresh token generator, started at " + new Date());
        //create a client
        var client = HttpClient.newHttpClient();
        //Generate google tokens
        var request = HttpRequest.newBuilder(URI.create("https://oauth2.googleapis.com/token?client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code + "&grant_type=" + grantType + "&redirect_uri=" + redirectUri + "&access_type=" + accessType)).header("Accept", "application/json").POST(HttpRequest.BodyPublishers.noBody()).build();

        // use the client to send the request
        HttpResponse<String> responseFuture = null;
        JSONObject object = null;
        try {
            // use the client to send the request
            var httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            object = new JSONObject(httpResponse.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            client = null;
            request = null;
            responseFuture = null;
        }
        LOGGER.info("Inside google refresh token generator, started at " + new Date());
        return object;
    }
}
