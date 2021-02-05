package com.drobot.module3.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataWriter.class);

    private DataWriter() {
    }

    public static void write(String filePath, List<String> content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : content) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            LOGGER.debug(content.size() + " lines has been written to the file " + filePath);
        }
    }
}
