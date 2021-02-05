package com.drobot.module3.builder;

import java.util.Map;

public interface RequestBuilder {

    String QUESTION_MARK = "?";
    String AMPERSAND = "&";
    String EQUALS = "=";

    String build(Map<String, String> requestParams);
}
