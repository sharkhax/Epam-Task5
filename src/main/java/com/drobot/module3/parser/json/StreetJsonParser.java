package com.drobot.module3.parser.json;

import com.drobot.module3.entity.Street;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreetJsonParser extends JsonParser<Street> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreetJsonParser.class);
    private static final String STREET_ID = "id";
    private static final String STREET_NAME = "name";

    @Override
    public Street parse(JSONObject jsonObject) {
        long id = jsonObject.getLong(STREET_ID);
        String name = jsonObject.getString(STREET_NAME);
        Street street = new Street(id, name);
        LOGGER.debug("Street " + id + " " + name + " has been parsed");
        return street;
    }
}
