package ru.vasilev.IO;

import ru.vasilev.entity.Transaction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reader of transactions from file.
 */
public class FileReader implements IReader {

    /**
     * File reader.
     */
    private BufferedReader br;
    /**
     * Filename.
     */
    private final FileInputStream fileReader;

    /**
     * Creates {@link FileReader} instance.
     *
     * @param fileName Path to file.
     * @throws IOException Throws, when file not found.
     */
    public FileReader(String fileName) throws IOException {
        fileReader = new FileInputStream(fileName);
        br = new BufferedReader(new InputStreamReader(fileReader));
    }

    /**
     * Gets next parsed transaction from file.
     *
     * @return Ready transaction.
     * @throws IOException Throws when I/O error occurs.
     */
    @Override
    public Transaction next() throws IOException {
        final String line = br.readLine();
        if (line != null) {
            return new Transaction(line);
        } else {
            return null;
        }
    }

    /**
     * Resets reader to start file.
     *
     * @throws IOException Throws when I/O error occurs.
     */
    @Override
    public void reset() throws IOException {
        fileReader.getChannel().position(0);
        br = new BufferedReader(new InputStreamReader(fileReader));
    }
}
