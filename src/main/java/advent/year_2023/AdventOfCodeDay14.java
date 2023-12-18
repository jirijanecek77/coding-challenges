package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class AdventOfCodeDay14 {

    static long countRocks(String inputFileName, boolean allDirections) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Item> items = new ArrayList<>();

        int n = 0;
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
            iterateItems(items, n);
            return calculateLoad(items, n);
        }

        calculateNorth(items, n);
        return calculateLoad(items, n);
    }

    private static void iterateItems(List<Item> tortoiseState, int size) {
        // Floyd’s Cycle Finding Algorithm

        List<Item> hareState = new ArrayList<>();
        tortoiseState.forEach(item -> hareState.add(new Item(item)));
        makeStep(hareState, size);

        int beforeCycleCounter = 0;
        while (!hareState.equals(tortoiseState)) {
            makeStep(tortoiseState, size);

            makeStep(hareState, size);
            makeStep(hareState, size);

            beforeCycleCounter++;
        }

        List<Item> currentState = new ArrayList<>();
        tortoiseState.forEach(item -> currentState.add(new Item(item)));

        int cycleCounter = 1;
        makeStep(tortoiseState, size);

        while (!currentState.equals(tortoiseState)) {
            makeStep(tortoiseState, size);
            cycleCounter++;
        }

        int remainsSteps = (1000000000 - beforeCycleCounter) % cycleCounter;
        for (int i = 0; i < remainsSteps; i++) {
            makeStep(tortoiseState, size);
        }
    }

    private static void makeStep(List<Item> turtleState, int n) {
        calculateNorth(turtleState, n);
        calculateWest(turtleState, n);
        calculateSouth(turtleState, n);
        calculateEast(turtleState, n);
    }

    private static void calculateNorth(List<Item> items, int n) {
        items.sort(Comparator.<Item, Integer>comparing(e -> e.row).thenComparing(e -> e.col));

        int[] minPos = new int[n];
        for (Item item : items) {
            if (item.isRock) {
                minPos[item.col] = item.row + 1;
            } else {
                item.row = minPos[item.col];
                minPos[item.col]++;
            }
        }
    }

    private static void calculateSouth(List<Item> items, int n) {
        items.sort(Comparator.<Item, Integer>comparing(e -> e.row).reversed().thenComparing(e -> e.col));

        int[] minPos = new int[n];
        Arrays.fill(minPos, n - 1);
        for (Item item : items) {
            if (item.isRock) {
                minPos[item.col] = item.row - 1;
            } else {
                item.row = minPos[item.col];
                minPos[item.col]--;
            }
        }
    }

    private static void calculateWest(List<Item> items, int n) {
        items.sort(Comparator.<Item, Integer>comparing(e -> e.col).thenComparing(e -> e.row));

        int[] minPos = new int[n];
        for (Item item : items) {
            if (item.isRock) {
                minPos[item.row] = item.col + 1;
            } else {
                item.col = minPos[item.row];
                minPos[item.row]++;
            }
        }
    }

    private static void calculateEast(List<Item> items, int n) {
        items.sort(Comparator.<Item, Integer>comparing(e -> e.col).reversed().thenComparing(e -> e.row));

        int[] minPos = new int[n];
        Arrays.fill(minPos, n - 1);
        for (Item item : items) {
            if (item.isRock) {
                minPos[item.row] = item.col - 1;
            } else {
                item.col = minPos[item.row];
                minPos[item.row]--;
            }
        }
    }

    private static long calculateLoad(List<Item> item, int n) {
        return item.stream().filter(e -> !e.isRock).mapToInt(e -> n - e.row).sum();
    }

    private static class Item {
        public int row;
        public int col;
        public boolean isRock;

        public Item(int row, int col, boolean isRock) {
            this.isRock = isRock;
            this.row = row;
            this.col = col;
        }

        public Item(Item item) {
            this.isRock = item.isRock;
            this.row = item.row;
            this.col = item.col;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + col + "), type: " + (isRock ? "rock" : "ball");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return row == item.row && col == item.col && isRock == item.isRock;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col, isRock);
        }
    }
}
