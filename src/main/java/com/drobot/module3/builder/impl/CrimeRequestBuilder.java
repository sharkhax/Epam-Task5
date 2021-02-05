package com.drobot.module3.builder.impl;

import com.drobot.module3.builder.RequestBuilder;
import com.drobot.module3.builder.RequestKey;
import com.drobot.module3.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CrimeRequestBuilder implements RequestBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeRequestBuilder.class);
    private static final String URL = "https://data.police.uk/api/crimes-street/all-crime";

    private static CrimeRequestBuilder instance;

    private CrimeRequestBuilder() {
    }

    public static CrimeRequestBuilder getInstance() {
        if (instance == null) {
            instance = new CrimeRequestBuilder();
        }
        return instance;
    }

    @Override
    public String build(Map<String, String> params) {
        StringBuilder result = new StringBuilder(URL);
        if (RequestValidator.isCrimesRequestMapValid(params)) {
            String date = params.get(RequestKey.DATE);
            String longitude = params.get(RequestKey.LONGITUDE);
            String latitude = params.get(RequestKey.LATITUDE);
            result.append(QUESTION_MARK).append(RequestKey.LATITUDE).append(EQUALS).append(latitude)
                    .append(AMPERSAND).append(RequestKey.LONGITUDE).append(EQUALS).append(longitude)
                    .append(AMPERSAND).append(RequestKey.DATE).append(EQUALS).append(date);
            LOGGER.debug("Crimes request has been built");
        }
        return result.toString();
    }
}