package SsmlParser;

import java.util.ArrayList;
import java.util.List;

import static SsmlParser.Ssml.unescapeXMLChars;

public class SSMLParser {

    private final String ssml;
    private int position;

    public SSMLParser(String ssml) {
        this.ssml = ssml;
        this.position = 0;
    }

    public List<Ssml.SSMLNode> parseChildElements(String parentTag) {
        List<Ssml.SSMLNode> children = new ArrayList<>();

        TokenData token;
        while ((token = getNextToken()) != null) {
            if (token.text() != null) {
                children.add(new Ssml.SSMLText(unescapeXMLChars(token.text())));
            } else if (token.isOpening()) {
                children.add(
                        new Ssml.SSMLElement(token.name(), token.attributes(), parseChildElements(token.name()))
                );
            } else if (token.name().equals(parentTag)) {
                return children;
            } else {
                break;
            }
        }
        throw new IllegalArgumentException("Tags could not be parsed");
    }

    public TokenData getNextToken() {
        String str = ssml.substring(position);
        if (str.isEmpty()) {
            return null;
        }

        State state = State.INIT;
        boolean isOpening = true;
        List<Ssml.SSMLAttribute> attributes = new ArrayList<>();
        StringBuilder attrKey = new StringBuilder();
        StringBuilder attrVal = new StringBuilder();
        StringBuilder text = new StringBuilder();
        for (char ch : str.toCharArray()) {
            switch (state) {
                case INIT -> {
                    if (ch == '<') {
                        state = State.TAG_NAME;
                    } else {
                        state = State.TAG_VALUE;
                        text.append(ch);
                    }
                }
                case TAG_NAME -> {
                    if (ch == '/') {
                        isOpening = false;
                    } else if (ch == '>') {
                        position += 1;
                        return new TokenData(text.toString(), isOpening, attributes, null);
                    } else if (ch == ' ') {
                        state = State.TAG_ATTR_KEY;
                    } else {
                        text.append(ch);
                    }
                }
                case TAG_ATTR_KEY -> {
                    if (ch == '=') {
                        if (attrKey.isEmpty()) {
                            throw new IllegalArgumentException("Attributes could not be parsed");
                        }
                        state = State.TAG_ATTR_START;
                    } else if (ch == '>') {
                        throw new IllegalArgumentException("Attributes could not be parsed");
                    } else if (ch == '<') {
                        throw new IllegalArgumentException("Tags could not be parsed");
                    } else if (ch != ' ') {
                        attrKey.append(ch);
                    }
                }
                case TAG_ATTR_START -> {
                    if (ch == '"') {
                        state = State.TAG_ATTR_VALUE;
                    } else if (ch != ' ') {
                        throw new IllegalArgumentException("Attributes could not be parsed");
                    }
                }
                case TAG_ATTR_VALUE -> {
                    if (ch == '"') {
                        attributes.add(new Ssml.SSMLAttribute(attrKey.toString(), attrVal.toString()));
                        attrKey.setLength(0);
                        attrVal.setLength(0);
                        state = State.TAG_ATTR_END;
                    } else if (ch == '>') {
                        throw new IllegalArgumentException("Attributes could not be parsed");
                    } else {
                        attrVal.append(ch);
                    }
                }
                case TAG_ATTR_END -> {
                    if (ch == '>') {
                        position += 1;
                        return new TokenData(text.toString(), isOpening, attributes, null);
                    } else {
                        state = State.TAG_ATTR_KEY;
                    }
                }
                case TAG_VALUE -> {
                    if (ch == '<') {
                        return new TokenData(null, false, null, text.toString());
                    }
                    text.append(ch);
                }
            }
            position += 1;
        }

        if (state == State.TAG_VALUE) {
            return new TokenData(null, false, null, text.toString());
        }
        throw new IllegalArgumentException("Tags could not be parsed");
    }

    public boolean isAllProcessed() {
        return this.position == this.ssml.length();
    }

    private enum State {
        INIT,
        TAG_NAME,
        TAG_ATTR_KEY,
        TAG_ATTR_START,
        TAG_ATTR_VALUE,
        TAG_ATTR_END,
        TAG_VALUE
    }
}
