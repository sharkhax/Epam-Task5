package com.drobot.module3.parser.json;

import com.drobot.module3.entity.CrimeCategory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrimeCategoryJsonParser extends JsonParser<CrimeCategory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeCategoryJsonParser.class);
    private static final String CATEGORY_URL = "url";
    private static final String NAME = "name";

    @Override
    public CrimeCategory parse(JSONObject jsonObject) {
        String categoryUrl = jsonObject.getString(CATEGORY_URL);
        String name = jsonObject.getString(NAME);
        CrimeCategory crimeCategory = new CrimeCategory(categoryUrl, name);
        LOGGER.debug("Crime category " + categoryUrl + " " + name + " has been parsed");
        return crimeCategory;
    }
}
