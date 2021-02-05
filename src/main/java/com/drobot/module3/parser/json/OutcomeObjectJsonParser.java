package com.drobot.module3.parser.json;

import com.drobot.module3.entity.OutcomeObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutcomeObjectJsonParser extends JsonParser<OutcomeObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutcomeObjectJsonParser.class);
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";

    @Override
    public OutcomeObject parse(JSONObject jsonObject) {
        String id = jsonObject.getString(ID_KEY);
        String name = jsonObject.getString(NAME_KEY);
        LOGGER.debug("Outcome object has been parsed");
        return new OutcomeObject(id, name);
    }
}
