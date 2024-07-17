package web.mvc.controller.user;


import java.util.HashSet;
import java.util.Set;

public class ProfanityFilter {

    private static final Set<String> profanitySet = new HashSet<String>();

    static {
        profanitySet.add("씨발");
        profanitySet.add("미친");
        profanitySet.add("병신");

    }

    public static boolean containsProfanity(String input) {
        if (input == null) return false;
        String[] words = input.split("\\s+");
        for (String word : words) {
            if (profanitySet.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
