package domain;

import java.util.*;

public class Trie {

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = this.root;
        for (char ch : word.toCharArray()) {
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        node.endOfWord = true;
    }

    public boolean search(String word) {
        return find(word).map(e -> e.endOfWord).orElse(false);
    }

    public boolean startsWith(String prefix) {
        return find(prefix).isPresent();
    }

    public List<String> findByPrefix(String prefix, int limit) {
        return find(prefix)
                .map(node -> getWordsWithPrefix(node, prefix).stream()
                        .sorted()
                        .limit(limit)
                        .toList()
                )
                .orElseGet(List::of);
    }

    private Optional<TrieNode> find(String word) {
        return dfs(this.root, word.chars().mapToObj(e -> (char) e).iterator());
    }

    private Optional<TrieNode> dfs(TrieNode node, Iterator<Character> it) {
        if (!it.hasNext()) {
            return Optional.of(node);
        }

        char ch = it.next();
        if (!node.children.containsKey(ch)) {
            return Optional.empty();
        }

        return dfs(node.children.get(ch), it);
    }

    private List<String> getWordsWithPrefix(TrieNode node, String str) {
        if (node.endOfWord) {
            return node.children.entrySet().stream()
                    .flatMap(e -> getWordsWithPrefix(e.getValue(), str + e.getKey()).stream())
                    .collect(() -> new ArrayList<>(List.of(str)), ArrayList::add, ArrayList::addAll);
        }
        return node.children.entrySet().stream().flatMap(e -> getWordsWithPrefix(e.getValue(), str + e.getKey()).stream()).toList();
    }

    private static class TrieNode {

        TrieNode() {
            this.endOfWord = false;
            this.children = new HashMap<>();
        }

        private final Map<Character, TrieNode> children;
        private boolean endOfWord;
    }
}
