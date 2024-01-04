package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrieTest {

    @Test
    void testTrieInsert() {
        var trie = new Trie();
        assertFalse(trie.search("app"));
        assertFalse(trie.startsWith("app"));
        trie.insert("apple");

        assertTrue(trie.search("apple"));
        assertFalse(trie.search("app"));
        assertTrue(trie.startsWith("app"));

        trie.insert("app");
        assertTrue(trie.search("app"));
        assertFalse(trie.search("a"));
        assertFalse(trie.search("apb"));
        assertFalse(trie.startsWith("apb"));
    }

}