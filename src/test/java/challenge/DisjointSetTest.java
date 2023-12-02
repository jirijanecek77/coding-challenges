package challenge;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class DisjointSetTest {

    @Test
    void test_synonyms() throws IOException {
        DisjointSet.checkSynonyms("src/test/resources/synonyms/example.in");
    }
}