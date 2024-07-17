package web.mvc.service.chatbot;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class AuthService extends HttpCallService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";
    public static String authToken;

    public boolean getKakaoAuthToken(String code) {
        HttpHeaders header = new HttpHeaders();
        String accessToken = "";
        String refreshToken = "";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        header.set("Content-Type", APP_TYPE_URL_ENCODED);

        parameters.add("code", code);
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", "244304566ddaff470cd1880088f8f270");
        parameters.add("redirect_uri", "http://localhost:9000");

        HttpEntity<?> requestEntity = httpClientEntity(header, parameters);

        try {
            ResponseEntity<String> response = httpRequest(AUTH_URL, HttpMethod.POST, requestEntity);

            JSONObject jsonData = new JSONObject(response.getBody());
            accessToken = jsonData.getString("access_token");
            refreshToken = jsonData.getString("refresh_token");
            logger.info("발급된 Access Token: " + accessToken);
            logger.info("발급된 Refresh Token: " + refreshToken);

            if (accessToken.isEmpty() || refreshToken.isEmpty()) {
                logger.debug("토큰 발급에 실패했습니다.");
                return false;
            } else {
                authToken = accessToken;
                return true;
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error during token retrieval: " + e.getStatusCode());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during token retrieval", e);
            return false;
        }
    }
}

