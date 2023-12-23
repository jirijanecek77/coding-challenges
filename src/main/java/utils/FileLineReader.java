package utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FileLineReader implements Iterable<String> {

    private final String fileName;

    private FileLineReader(String fileName) {
        this.fileName = fileName;
    }

    public static FileLineReader of(String fileName) {
        return new FileLineReader("src/test/resources/" + fileName);
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new LineIterator(fileName);
    }

    private static class LineIterator implements Iterator<String> {

        private final BufferedReader reader;
        private String line;

        public LineIterator(String fileName) {
            try {
                reader = Files.newBufferedReader(Paths.get(fileName));
                line = reader.readLine();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public boolean hasNext() {
            return line != null;
        }

        @Override
        public String next() {
            if (hasNext()) {
                String currentLine = this.line;
                try {
                    this.line = reader.readLine();
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
                return currentLine;
            }
            throw new NoSuchElementException();
        }
    }
}


