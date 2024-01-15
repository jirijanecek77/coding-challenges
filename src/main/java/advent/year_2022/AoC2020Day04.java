package advent.year_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class AoC2020Day04 {

    public static final Set<String> PASSPORT_CODES = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    public long solve(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        int counter = 0;
        Set<String> expectedCodes = new HashSet<>(PASSPORT_CODES);

        try (reader) {
            while (true) {
                String line = reader.readLine();
                if (line == null || line.isBlank()) {
                    if (expectedCodes.isEmpty()) {
                        counter++;
                    }
                    if (line == null) break;
                    expectedCodes = new HashSet<>(PASSPORT_CODES);
                }

                //ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
                String[] pairs = line.split("[\s]+");

                for (String pair : pairs) {
                    String[] parts = pair.split(":");
                    if (parts.length == 2 && isValid(parts[0], parts[1])) {
                        expectedCodes.remove(parts[0]);
                    }
                }
            }
        }

        return counter;
    }

    private boolean isValid(String key, String value) {
//        byr (Birth Year) - four digits; at least 1920 and at most 2002.
//        iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//        eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//        hgt (Height) - a number followed by either cm or in:
//        If cm, the number must be at least 150 and at most 193.
//        If in, the number must be at least 59 and at most 76.
//        hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//        ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//        pid (Passport ID) - a nine-digit number, including leading zeroes.


        switch (key) {
            case "byr":
                return validateNumber(value, 1920, 2002);
            case "iyr":
                return validateNumber(value, 2010, 2020);
            case "eyr":
                return validateNumber(value, 2020, 2030);
            case "hgt":
                return value.length() > 2 &&
                        ((value.endsWith("cm") && validateNumber(value.substring(0, value.length() - 2), 150, 193))
                        || (value.endsWith("in") && validateNumber(value.substring(0, value.length() - 2), 59, 76)));
            case "hcl":
                return value.matches("#[0-9a-f]{6}");
            case "ecl":
                return value.matches("amb|blu|brn|gry|grn|hzl|oth");
            case "pid":
                return value.matches("[0-9]{9}");
            default:
                return false;
        }
    }


    private boolean validateNumber(String value, int from, int to) {
        try {
            int number = Integer.parseInt(value);
            return number >= from && number <= to;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}