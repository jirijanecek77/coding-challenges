package challenge;

import domain.Pair;

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
                List<Pair<String, String>> synonyms = readWords(reader, synLen);

                Map<String, Integer> clusters = clusterSynonyms(synonyms);

                int queryLen = Integer.parseInt(reader.readLine());
                List<Pair<String, String>> queries = readWords(reader, queryLen);

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

    private List<Pair<String, String>> readWords(BufferedReader reader, int synLen) throws IOException {
        List<Pair<String, String>> result = new ArrayList<>();

        for (int i = 0; i < synLen; i++) {
            var line = reader.readLine();
            String[] words = line.split(" ");

            result.add(new Pair<>(words[0].toUpperCase(), words[1].toUpperCase()));
        }

        return result;
    }

    private Map<String, Integer> clusterSynonyms(List<Pair<String, String>> synonyms) {
        int clusterNr = 1;
        Map<String, Integer> clusters = new HashMap<>();

        for (Pair<String, String> pair : synonyms) {
            Optional<Integer> fromCluster = clusters.entrySet().stream()
                    .filter(from -> from.getKey().equals(pair.first()))
                    .findFirst()
                    .map(Map.Entry::getValue);

            Optional<Integer> toCluster = clusters.entrySet().stream()
                    .filter(to -> to.getKey().equals(pair.second()))
                    .findFirst()
                    .map(Map.Entry::getValue);

            if (fromCluster.isEmpty() && toCluster.isEmpty()) {
                clusters.put(pair.first(), clusterNr);
                clusters.put(pair.second(), clusterNr);
                clusterNr++;
                continue;
            }

            fromCluster.ifPresent(newCluster -> {
                Integer oldCluster = clusters.get(pair.second());
                clusters.put(pair.second(), newCluster);

                if (oldCluster != null) {
                    clusters.entrySet()
                            .stream()
                            .filter(e -> e.getValue().equals(oldCluster))
                            .forEach(e -> clusters.put(e.getKey(), newCluster));
                }
            });

            toCluster.ifPresent(newCluster -> {
                Integer oldCluster = clusters.get(pair.first());
                clusters.put(pair.first(), newCluster);

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
        if (query.first().equals(query.second())) {
            return true;
        }

        return clusters.get(query.first()) != null && clusters.get(query.first()).equals(clusters.get(query.second()));
    }


}
