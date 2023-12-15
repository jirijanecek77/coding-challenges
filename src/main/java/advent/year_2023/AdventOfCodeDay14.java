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

    static long countRocks(String inputFileName, boolean allDirections) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Item> items = new ArrayList<>();

        int row = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                for (int col = 0; col < line.length(); col++) {
                    if (line.charAt(col) == '#') {
                        items.add(new Item(row, col, true));
                    } else if (line.charAt(col) == 'O') {
                        items.add(new Item(row, col, false));
                    }
                }
                row++;
                // read next line
                line = reader.readLine();
            }
        }
        if (allDirections) {
            for (int i = 0; i < 1000; i++) {
                calculateNorth(items, row);
                calculateWest(items, row);
                calculateSouth(items, row);
                calculateEast(items, row);
            }

            return calculateLoad(items, row);
        }

        calculateNorth(items, row);
        return calculateLoad(items, row);
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

        @Override
        public String toString() {
            return "(" + row + ", " + col + "), type: " + (isRock ? "rock" : "ball");
        }
    }
}
