package com.drobot.module3.parser.json;

import com.drobot.module3.entity.Location;
import com.drobot.module3.entity.Street;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationJsonParser extends JsonParser<Location> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationJsonParser.class);
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String STREET = "street";
    private final StreetJsonParser streetJsonParser = new StreetJsonParser();

    @Override
    public Location parse(JSONObject jsonObject) {
        float latitude = jsonObject.getFloat(LATITUDE);
        float longitude = jsonObject.getFloat(LONGITUDE);
        JSONObject streetJsonObject = jsonObject.getJSONObject(STREET);
        Street street = streetJsonParser.parse(streetJsonObject);
        Location location = new Location(latitude, street, longitude);
        LOGGER.debug("Location " + latitude + " " + longitude + " has been parsed");
        return location;
    }
}
