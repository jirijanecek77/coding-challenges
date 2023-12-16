package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;


public class AdventOfCodeDay15 {

    private static final int SLOTS = 256;

    static long calcHash(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        try (reader) {
            String line = reader.readLine();

            return Arrays.stream(line.split(",")).mapToInt(AdventOfCodeDay15::hash).sum();
        }
    }

    private static int hash(String str) {
        int result = 0;

        for (char ch : str.toCharArray()) {
            result += ch;
            result *= 17;
            result %= 256;
        }

        return result;
    }

    public static int calcFocusingPower(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        try (reader) {
            String line = reader.readLine();

            List<LinkedHashMap<String, Integer>> lenses = new ArrayList<>(SLOTS);
            for (int i = 0; i < SLOTS; i++) {
                lenses.add(new LinkedHashMap<>());
            }

            Arrays.stream(line.split(",")).forEach(step -> performStep(step, lenses));
            return IntStream.range(0, SLOTS).map(i -> calculateFocusingPower(i, lenses.get(i))).sum();
        }
    }

    private static void performStep(String step, List<LinkedHashMap<String, Integer>> lenses) {
        int addOperation = step.indexOf('=');
        if (addOperation >= 0) {
            String label = step.substring(0, addOperation);
            lenses.get(hash(label)).put(label, Integer.parseInt(step.substring(addOperation + 1)));
        } else {
            int deleteOperation = step.indexOf('-');
            String label = step.substring(0, deleteOperation);
            lenses.get(hash(label)).remove(label);
        }
    }

    private static int calculateFocusingPower(int index, LinkedHashMap<String, Integer> lens) {
        int sum = 0;

        int slotIndex = 1;
        for (Map.Entry<String, Integer> slot : lens.entrySet()) {
            sum += (index + 1) * slotIndex * slot.getValue();
            slotIndex++;
        }

        return sum;
    }
}
