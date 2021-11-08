import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Synonyms {

    public void checkSynonyms(String inputFileName) throws IOException {
        final String outputFileName = "resources/synonyms/output.txt";

        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));


        try (reader; writer) {
            int testCases = Integer.parseInt(reader.readLine());

            for (int testCase = 0; testCase < testCases; testCase++) {
                int synLen = Integer.parseInt(reader.readLine());
                List<Pair> synonyms = readWords(reader, synLen);

                Map<String, Integer> clusters = clusterSynonyms(synonyms);

                int queryLen = Integer.parseInt(reader.readLine());
                List<Pair> queries = readWords(reader, queryLen);

                queries.forEach(query -> {
                    try {
                        writer.write(evaluate(query, clusters) ? "synonyms\n" : "different\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

        }
    }

    private List<Pair> readWords(BufferedReader reader, int synLen) throws IOException {
        List<Pair> result = new ArrayList<>();

        for (int i = 0; i < synLen; i++) {
            var line = reader.readLine();
            String[] words = line.split(" ");

            result.add(new Pair(words[0].toUpperCase(), words[1].toUpperCase()));
        }

        return result;
    }

    private Map<String, Integer> clusterSynonyms(List<Pair> synonyms) {
        int clusterNr = 1;
        Map<String, Integer> clusters = new HashMap<>();

        for (Pair pair : synonyms) {
            Optional<Integer> fromCluster = clusters.entrySet().stream()
                    .filter(from -> from.getKey().equals(pair.from))
                    .findFirst()
                    .map(Map.Entry::getValue);

            Optional<Integer> toCluster = clusters.entrySet().stream()
                    .filter(to -> to.getKey().equals(pair.to))
                    .findFirst()
                    .map(Map.Entry::getValue);

            if (fromCluster.isEmpty() && toCluster.isEmpty()) {
                clusters.put(pair.from, clusterNr);
                clusters.put(pair.to, clusterNr);
                clusterNr++;
                continue;
            }

            fromCluster.ifPresent(newCluster -> {
                Integer oldCluster = clusters.get(pair.to);
                clusters.put(pair.to, newCluster);

                if (oldCluster != null) {
                    clusters.entrySet()
                            .stream()
                            .filter(e -> e.getValue().equals(oldCluster))
                            .forEach(e -> clusters.put(e.getKey(), newCluster));
                }
            });

            toCluster.ifPresent(newCluster -> {
                Integer oldCluster = clusters.get(pair.from);
                clusters.put(pair.from, newCluster);

                if (oldCluster != null) {
                    clusters.entrySet()
                            .stream()
                            .filter(e -> e.getValue().equals(oldCluster))
                            .forEach(e -> clusters.put(e.getKey(), newCluster));
                }
            });
        }

        return clusters;
    }

    private boolean evaluate(Pair query, Map<String, Integer> clusters) {
        if (query.from.equals(query.to)) {
            return true;
        }

        return clusters.get(query.from) != null && clusters.get(query.from).equals(clusters.get(query.to));
    }

    private static class Pair {

        public String from;
        public String to;

        public Pair(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }
}
