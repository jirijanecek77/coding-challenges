package challenge;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class SynonymsTest {

    @Test
    void test() throws IOException {
        new Synonyms().checkSynonyms("resources/synonyms/example.in");
    }
}