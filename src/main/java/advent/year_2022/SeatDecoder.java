package advent.year_2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SeatDecoder {

    public long decodeSeat(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        long maxId = 0;

        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                var row = binaryToLong(line.substring(0, 7), 'B');
                var col = binaryToLong(line.substring(7), 'R');
                maxId = Math.max(maxId, row * 8 + col);

                // read next line
                line = reader.readLine();
            }
        }

        return maxId;
    }

    private long binaryToLong(String text, char highChar) {
        var result = 0L;
        var chars = text.toCharArray();

        for (int i = chars.length - 1, exp = 0; i >= 0; i--, exp++) {
            if (chars[i] == highChar) {
                result += Math.pow(2, exp);
            }
        }
        return result;
    }
}
