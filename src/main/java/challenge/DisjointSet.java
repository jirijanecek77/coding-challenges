package challenge;

import domain.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class DisjointSet {

    private static class DisjointUnionSets {
        private final int[] rank;
        private final int[] parent;

        // Constructor
        public DisjointUnionSets(int n) {
            this.rank = new int[n];
            this.parent = new int[n];

            for (int i = 0; i < n; i++) {
                // Initially, all elements are in their own set.
                this.parent[i] = i;
            }
        }

        // Returns representative of x's set
        int find(int x) {
            // Finds the representative of the set
            // that x is an element of
            if (parent[x] != x) {
                // if x is not the parent of itself
                // Then x is not the representative of his set,
                parent[x] = find(parent[x]);

                // so we recursively call Find on its parent and move i's node directly under the
                // representative of this set
            }

            return parent[x];
        }

        // Unites the set that includes x and the set that includes x
        void union(int x, int y) {
            // Find representatives of two sets
            int xRoot = find(x), yRoot = find(y);

            // Elements are in the same set, no need to unite anything.
            if (xRoot == yRoot)
                return;

            // If x's rank is less than y's rank
            if (rank[xRoot] < rank[yRoot])

                // Then move x under y  so that depth of tree remains less
                parent[xRoot] = yRoot;

                // Else if y's rank is less than x's rank
            else if (rank[yRoot] < rank[xRoot])

                // Then move y under x so that depth of tree remains less
                parent[yRoot] = xRoot;

            else // if ranks are the same
            {
                // Then move y under x (doesn't matter which one goes where)
                parent[yRoot] = xRoot;

                // And increment the result tree's rank by 1
                rank[xRoot] = rank[xRoot] + 1;
            }
        }
    }

    public static void checkSynonyms(String inputFileName) throws IOException {
        final String outputFileName = "src/test/resources/synonyms/output.txt";

        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

        try (reader; writer) {
            int testCases = Integer.parseInt(reader.readLine());

            for (int testCase = 0; testCase < testCases; testCase++) {
                int synLen = Integer.parseInt(reader.readLine());
                List<Pair<String, String>> synonyms = readWords(reader, synLen);
                List<String> words = synonyms.stream().map(p -> List.of(p.first(), p.second()))
                        .flatMap(List::stream).distinct().toList();
                Map<String, Integer> wordToIndexMap = IntStream.range(0, words.size())
                        .boxed()
                        .collect(toMap(words::get, Function.identity()));
                DisjointUnionSets disjointSet = new DisjointUnionSets(words.size());


                for (Pair<String, String> s : synonyms) {
                    disjointSet.union(wordToIndexMap.get(s.first()), wordToIndexMap.get(s.second()));
                }

                int queryLen = Integer.parseInt(reader.readLine());
                List<Pair<String, String>> queries = readWords(reader, queryLen);

                for (Pair<String, String> query : queries) {
                    String word1 = query.first();
                    String word2 = query.second();
                    Integer index1 = wordToIndexMap.get(word1);
                    Integer index2 = wordToIndexMap.get(word2);
                    writer.write(
                            word1.equals(word2)
                                    || (index1 != null && index2 != null && disjointSet.find(index1) == disjointSet.find(index2))
                                    ? "synonyms\n"
                                    : "different\n"
                    );
                }
            }

        }
    }

    private static List<Pair<String, String>> readWords(BufferedReader reader, int synLen) throws IOException {
        List<Pair<String, String>> result = new ArrayList<>();

        for (int i = 0; i < synLen; i++) {
            var line = reader.readLine();
            String[] words = line.split(" ");

            result.add(new Pair<>(words[0].toUpperCase(), words[1].toUpperCase()));
        }

        return result;
    }
}
