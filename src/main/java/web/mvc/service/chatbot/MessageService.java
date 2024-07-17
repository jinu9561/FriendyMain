package web.mvc.service.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.client.RestTemplate;
import web.mvc.dto.chatbot.DefaultMessageDto;

@Slf4j
@Service
public class MessageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String MESSAGE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

    public boolean sendMessage(String accessToken, DefaultMessageDto messageDto) {

        log.info("messageDto" , messageDto.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String messageJson = objectMapper.writeValueAsString(messageDto);

            MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
            System.out.println("MessageService messageJson = " + messageJson);
            parameters.add("template_object", messageJson);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(MESSAGE_URL, HttpMethod.POST, requestEntity, String.class);

            logger.info("Message sent successfully: " + response.getBody());
            return true;

        } catch (HttpClientErrorException e) {
            logger.error("HTTP error during message sending: " + e.getStatusCode());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during message sending", e);
            return false;
        }
    }
}

