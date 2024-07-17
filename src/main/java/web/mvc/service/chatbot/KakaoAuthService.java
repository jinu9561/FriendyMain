package web.mvc.service.chatbot;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoAuthService {

    private static final String TOKEN_INFO_URL = "https://kapi.kakao.com/v1/user/access_token_info";

    public boolean verifyAccessToken(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(TOKEN_INFO_URL, HttpMethod.GET, entity, String.class);

            JSONObject jsonResponse = new JSONObject(response.getBody());
            int expiresIn = jsonResponse.getInt("expires_in");

            System.out.println("expiresIn = " + expiresIn);

            return expiresIn > 0;  // Token is valid if it hasn't expired
        } catch (Exception e) {
            // Handle exceptions (e.g., token is invalid or expired)
            e.printStackTrace();
            return false;
        }
    }
}
