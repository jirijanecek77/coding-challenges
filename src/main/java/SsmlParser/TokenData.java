package SsmlParser;

import java.util.List;

public record TokenData(String name, boolean isOpening, List<Ssml.SSMLAttribute> attributes, String text) {
}
