package advent.year_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CheckPassword {

    public int checkPasswords(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        int counter = 0;

        try (reader) {
            String line = reader.readLine();

            while (line != null) {
                //5-6 c: cbccxc
                String[] parts = line.split("[-|:|\s]+");
                int min = Integer.parseInt(parts[0]);
                int max = Integer.parseInt(parts[1]);
                char ch = parts[2].charAt(0);
                String password = parts[3];

                if (isValid(min, max, ch, password)) counter++;

                // read next line
                line = reader.readLine();
            }
        }

        return counter;
    }

    private boolean isValid(int min, int max, char targetChar, String password) {
        int counter = 0;

        for (char c : password.toCharArray()) {
            if (c == targetChar) counter++;

            if (counter > max) {
                return false;
            }
        }

        return counter >= min;
    }

    private boolean isValid2(int first, int second, char targetChar, String password) {
        return password.charAt(first - 1) == targetChar ^ password.charAt(second - 1) == targetChar;
    }

}
