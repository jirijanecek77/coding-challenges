package advent.year_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc2020Day03 {

    public long solve(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        int rowCounter = 0;
        Map<Integer, Long> results = new HashMap<>(Map.of(0, 0L, 1, 0L, 2, 0L, 3, 0L, 4, 0L));

        try (reader) {
            String line = reader.readLine();
            // # - tree
            // 0 - Right 1, down 1.
            // 1 - Right 3, down 1.
            // 2 - Right 5, down 1.
            // 3 - Right 7, down 1.
            // 4 - Right 1, down 2.
            List<Integer> rights = List.of(1, 3, 5, 7, 1);
            List<Integer> downs = List.of(1, 1, 1, 1, 2);
            Map<Integer, Integer> positions =
                    new HashMap<>(Map.of(0, 0, 1, 0, 2, 0, 3, 0, 4, 0));

            while (line != null) {
                int n = line.length();

                for (int i = 0; i < rights.size(); i++) {
                    if (rowCounter % downs.get(i) == 0) {
                        var position = positions.get(i);
                        if (line.charAt(position) == '#') {
                            results.put(i, results.get(i) + 1);
                        }
                        positions.put(i, (positions.get(i) + rights.get(i)) % n);
                    }
                }

                // read next line
                line = reader.readLine();
                rowCounter++;
            }
        }

        return results.values().stream().reduce(1L, (a, b) -> a * b);
    }
}
