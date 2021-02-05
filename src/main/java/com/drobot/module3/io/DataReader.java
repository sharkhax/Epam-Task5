package com.drobot.module3.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataReader.class);

    private DataReader() {
    }

    public static List<String> readFile(String filePath) throws IOException {
        List<String> result;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            result = reader.lines().collect(Collectors.toList());
            LOGGER.debug("File " + filePath + " has been read: " + result.size() + " lines total");
        }
        return result;
    }
}
