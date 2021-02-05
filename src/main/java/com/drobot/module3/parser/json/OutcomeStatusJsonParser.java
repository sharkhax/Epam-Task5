package com.drobot.module3.parser.json;

import com.drobot.module3.entity.OutcomeStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutcomeStatusJsonParser extends JsonParser<OutcomeStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutcomeStatusJsonParser.class);
    private static final String CATEGORY = "category";
    private static final String DATE = "date";

    @Override
    public OutcomeStatus parse(JSONObject jsonObject) {
        String category = jsonObject.getString(CATEGORY);
        String date = jsonObject.getString(DATE);
        OutcomeStatus outcomeStatus = new OutcomeStatus(category, date);
        LOGGER.debug("Outcome status " + category + " " + date + " has been parsed");
        return outcomeStatus;
    }
}
