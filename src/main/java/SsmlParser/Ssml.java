package SsmlParser;

import java.util.List;

/**
 * SSML (Speech Synthesis Markup Language) is a subset of XML specifically
 * designed for controlling synthesis. You can see examples of how the SSML
 * should be parsed in com.speechify.SSMLTest in `src/test/java/SSMLTest.java`.
 * <p>
 * You may:
 * - Read online guides to supplement information given in com.speechify.SSMLTest to understand SSML syntax.
 * You must not:
 * - Use XML parsing libraries or the DocumentBuilderFactory. The task should be solved only using string manipulation.
 * - Read guides about how to code an XML or SSML parser.
 */
public class Ssml {

    public static final String SPEAK_TAG_NAME = "speak";

    // Parses SSML to a SSMLNode, throwing on invalid SSML
    public static SSMLNode parseSSML(String ssml) {
        SSMLParser ssmlParser = new SSMLParser(ssml);

        TokenData token = ssmlParser.getNextToken();
        if (token != null && SPEAK_TAG_NAME.equals(token.name()) && token.isOpening()) {
            List<SSMLNode> children = ssmlParser.parseChildElements(SPEAK_TAG_NAME);
            if (ssmlParser.isAllProcessed()) {
                return new SSMLElement(SPEAK_TAG_NAME, token.attributes(), children);
            }
        }
        throw new IllegalArgumentException("Tags could not be parsed");
    }

    // Recursively converts SSML node to string and unescapes XML chars
    public static String ssmlNodeToText(SSMLNode node) {
        StringBuilder sb = new StringBuilder();
        if (node instanceof SSMLText text) {
            sb.append(text.text());
        } else if (node instanceof SSMLElement element) {
            element.children.stream().map(Ssml::ssmlNodeToText).forEach(sb::append);
        }
        return sb.toString();
    }

    // Already done for you
    public static String unescapeXMLChars(String text) {
        return text.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
    }

    public sealed interface SSMLNode permits SSMLElement, SSMLText {
    }

    public record SSMLElement(String name, List<SSMLAttribute> attributes, List<SSMLNode> children) implements SSMLNode {
    }

    public record SSMLAttribute(String name, String value) {
    }

    public record SSMLText(String text) implements SSMLNode {
    }
}
