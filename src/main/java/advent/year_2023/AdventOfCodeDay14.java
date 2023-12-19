package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class AdventOfCodeDay14 {

    private int n = 0;

    long countRocks(String inputFileName, boolean allDirections) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Item> items = new ArrayList<>();

        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                for (int col = 0; col < line.length(); col++) {
                    if (line.charAt(col) == '#') {
                        items.add(new Item(n, col, true));
                    } else if (line.charAt(col) == 'O') {
                        items.add(new Item(n, col, false));
                    }
                }
                n++;
                // read next line
                line = reader.readLine();
            }
        }
        if (allDirections) {
            return calculateResult(iterateItems(items));
        }

        return calculateResult(calculateNorth(items));
    }

    private List<Item> iterateItems(List<Item> tortoiseState) {
        // Floyd’s Cycle Finding Algorithm

        List<Item> hareState = makeIteration(tortoiseState);

        int beforeCycleCounter = 0;
        while (!hareState.equals(tortoiseState)) {
            tortoiseState = makeIteration(tortoiseState);
            hareState = makeIteration(makeIteration(hareState));

            beforeCycleCounter++;
        }

        int cycleCounter = 1;
        tortoiseState = makeIteration(tortoiseState);

        while (!hareState.equals(tortoiseState)) {
            tortoiseState = makeIteration(tortoiseState);
            cycleCounter++;
        }

        int remainsSteps = (1000000000 - beforeCycleCounter) % cycleCounter;
        for (int i = 0; i < remainsSteps; i++) {
            tortoiseState = makeIteration(tortoiseState);
        }

        return tortoiseState;
    }

    private List<Item> makeIteration(List<Item> items) {
        items = calculateNorth(items);
        items = calculateWest(items);
        items = calculateSouth(items);
        return calculateEast(items);
    }

    private List<Item> calculateNorth(List<Item> items) {
        items.sort(Comparator.comparing(e -> e.row));

        int[] minPos = new int[n];
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.isRock) {
                result.add(item);
                minPos[item.col] = item.row + 1;
            } else {
                result.add(new Item(minPos[item.col], item.col, false));
                minPos[item.col]++;
            }
        }
        return result;
    }

    private List<Item> calculateSouth(List<Item> items) {
        items.sort(Comparator.<Item, Integer>comparing(e -> e.row).reversed());

        int[] minPos = new int[n];
        Arrays.fill(minPos, n - 1);
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.isRock) {
                result.add(item);
                minPos[item.col] = item.row - 1;
            } else {
                result.add(new Item(minPos[item.col], item.col, false));
                minPos[item.col]--;
            }
        }
        return result;
    }

    private List<Item> calculateWest(List<Item> items) {
        items.sort(Comparator.comparing(e -> e.col));

        int[] minPos = new int[n];
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.isRock) {
                result.add(item);
                minPos[item.row] = item.col + 1;
            } else {
                result.add(new Item(item.row, minPos[item.row], false));
                minPos[item.row]++;
            }
        }
        return result;
    }

    private List<Item> calculateEast(List<Item> items) {
        items.sort(Comparator.<Item, Integer>comparing(e -> e.col).reversed());

        int[] minPos = new int[n];
        Arrays.fill(minPos, n - 1);
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.isRock) {
                result.add(item);
                minPos[item.row] = item.col - 1;
            } else {
                result.add(new Item(item.row, minPos[item.row], false));
                minPos[item.row]--;
            }
        }
        return result;
    }

    private long calculateResult(List<Item> item) {
        return item.stream().filter(e -> !e.isRock).mapToInt(e -> n - e.row).sum();
    }

    private record Item(int row, int col, boolean isRock) {
    }
}
