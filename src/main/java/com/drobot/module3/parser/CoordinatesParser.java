package com.drobot.module3.parser;

import com.drobot.module3.entity.Coordinate;
import com.drobot.module3.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoordinatesParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoordinatesParser.class);
    private static final int LATITUDE_INDEX = 0;
    private static final int LONGITUDE_INDEX = 1;
    private static final String SPLIT_REGEX = " ";

    public Coordinate parse(String line) {
        String[] coordinates = line.split(SPLIT_REGEX);
        Coordinate result = null;
        try {
            String latitude = coordinates[LATITUDE_INDEX];
            String longitude = coordinates[LONGITUDE_INDEX];
            if (RequestValidator.isCoordinateValid(latitude) && RequestValidator.isCoordinateValid(longitude)) {
                result = new Coordinate(latitude, longitude);
                LOGGER.debug("Coordinates have been parsed");
            } else {
                LOGGER.debug("Coordinates is not valid");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.debug("Can't parse coordinates from line " + line);
        }
        return result;
    }
}
