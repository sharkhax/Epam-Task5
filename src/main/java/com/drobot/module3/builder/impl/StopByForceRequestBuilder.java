package com.drobot.module3.builder.impl;

import com.drobot.module3.builder.RequestBuilder;
import com.drobot.module3.builder.RequestKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StopByForceRequestBuilder implements RequestBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopByForceRequestBuilder.class);
    private static final String URL = "https://data.police.uk/api/stops-force";

    private static StopByForceRequestBuilder stopByForceRequestBuilder;

    private StopByForceRequestBuilder() {
    }

    public static StopByForceRequestBuilder getInstance() {
        if (stopByForceRequestBuilder == null) {
            stopByForceRequestBuilder = new StopByForceRequestBuilder();
        }
        return stopByForceRequestBuilder;
    }

    @Override
    public String build(Map<String, String> requestParams) {
        StringBuilder result = new StringBuilder(URL);
        String forceName = requestParams.get(RequestKey.FORCE);
        String date = requestParams.get(RequestKey.DATE);
        result.append(QUESTION_MARK).append(RequestKey.FORCE).append(EQUALS).append(forceName)
                .append(AMPERSAND).append(RequestKey.DATE).append(EQUALS).append(date);
        LOGGER.debug("Stops by force request has been built");
        return result.toString();
    }
}
