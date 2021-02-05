package com.drobot.module3.util;

import com.drobot.module3.builder.RequestKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class RequestValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);

    private RequestValidator() {
    }

    public static boolean isDateValid(String date) {
        boolean result = false;
        try {
            if (date != null) {
                YearMonth.parse(date);
                result = true;
                LOGGER.debug("Date " + date + " is valid");
            }
        } catch (DateTimeParseException e) {
            LOGGER.debug("Date " + date + " is not valid");
        }
        return result;
    }

    public static boolean isCoordinateValid(String coordinate) {
        boolean result = false;
        try {
            Float.parseFloat(coordinate);
            result = true;
            LOGGER.debug("Coordinate " + coordinate + " is valid");
        } catch (NumberFormatException e) {
            LOGGER.debug("Coordinate " + coordinate + " is not valid");
        }
        return result;
    }

    public static boolean isCrimesRequestMapValid(Map<String, String> params) {
        boolean result = false;
        if (params != null && !params.isEmpty()) {
            if (params.containsKey(RequestKey.LATITUDE)
                    && params.containsKey(RequestKey.LONGITUDE)
                    && params.containsKey(RequestKey.DATE)) {
                String latitude = params.get(RequestKey.LATITUDE);
                String longitude = params.get(RequestKey.LONGITUDE);
                String date = params.get(RequestKey.DATE);
                result = isCoordinateValid(latitude) & isCoordinateValid(longitude) & isDateValid(date);
            } else {
                LOGGER.debug("Map doesn't contain all required values");
            }
        } else {
            LOGGER.debug("Map is null or empty");
        }
        return result;
    }
}
