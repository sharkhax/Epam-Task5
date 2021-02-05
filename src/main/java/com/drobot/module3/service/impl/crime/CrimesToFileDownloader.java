package com.drobot.module3.service.impl.crime;

import com.drobot.module3.entity.Crime;
import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.io.DataWriter;
import com.drobot.module3.parser.PropertyKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CrimesToFileDownloader extends CrimesDownloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrimesToFileDownloader.class);

    @Override
    public void download(Map<String, String> params) throws ServiceException {
        String filePath = params.get(PropertyKey.TARGET_FILE);
        if (filePath == null) {
            throw new ServiceException("Target file path is null");
        }
        List<Crime> crimes = downloadFromApi(params);
        writeToFile(crimes, filePath);
        LOGGER.info("Downloading crimes to file complete");
    }

    private void writeToFile(List<Crime> crimes, String filePath) throws ServiceException {
        try {
            List<String> content = new ArrayList<>();
            for (Crime crime : crimes) {
                updateCrimeCategory(crime);
                content.add(crime.toString());
            }
            DataWriter.write(filePath, content);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}
