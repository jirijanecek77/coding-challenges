package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static String[] parseByPattern(String str, Pattern pattern, int... groupIds) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String[] result = new String[groupIds.length];
            for (int i = 0; i < groupIds.length; i++) {
                result[i] = matcher.group(groupIds[i]);
            }
            return result;
        }
        throw new RuntimeException("matcher does not match");
    }
}
