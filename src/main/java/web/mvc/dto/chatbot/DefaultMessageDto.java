package web.mvc.dto.chatbot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefaultMessageDto {
    @JsonProperty("object_type")
    private String objType;

    private String text;
//    private Map<String, String> link;
    private Link link;

    @JsonProperty("button_title")
    private String btnTitle;

    @Getter
    @Setter
    public static class Link {
        @JsonProperty("web_url")
        private String webUrl;
        @JsonProperty("mobile_web_url")
        private String mobileUrl;
    }
}
