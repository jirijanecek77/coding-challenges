package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Trie {

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode(null);
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node = insertChar(ch, node);
        }
        node.wordEnd = true;
    }

    public boolean search(String word) {
        return find(word, root).map(e -> e.wordEnd).orElse(false);
    }

    public boolean startsWith(String prefix) {
        return find(prefix, root).isPresent();
    }

    public List<String> findByPrefix(String prefix, int limit) {
        return find(prefix, root)
                .map(node -> getSuffixes(node, prefix.substring(0, prefix.length() - 1)).stream()
                        .sorted()
                        .limit(limit)
                        .toList()
                )
                .orElseGet(List::of);
    }

    private TrieNode insertChar(char ch, TrieNode root) {
        return root.children.stream().filter(child -> child.ch == ch).findFirst()
                .orElseGet(() -> {
                    TrieNode node = new TrieNode(ch);
                    root.children.add(node);
                    return node;
                });
    }

    private Optional<TrieNode> find(String word, TrieNode root) {
        Optional<TrieNode> node = Optional.ofNullable(root);
        for (char ch : word.toCharArray()) {
            if (node.isEmpty()) {
                return Optional.empty();
            }
            node = node.flatMap(e -> e.children.stream().filter(child -> child.ch == ch).findFirst());
        }
        return node;
    }

    private List<String> getSuffixes(TrieNode node, String str) {
        String word = str + node.ch;
        if (node.children.isEmpty()) {
            return List.of(word);
        } else if (node.wordEnd) {
            return node.children.stream()
                    .flatMap(child -> getSuffixes(child, word).stream())
                    .collect(() -> new ArrayList<>(List.of(word)), ArrayList::add, ArrayList::addAll);
        }
        return node.children.stream().flatMap(child -> getSuffixes(child, word).stream()).toList();
    }

    private static class TrieNode {

        TrieNode(Character ch) {
            this.ch = ch;
            this.wordEnd = false;
            this.children = new ArrayList<>();
        }

        private final Character ch;
        private boolean wordEnd;
        private final List<TrieNode> children;
    }
}
