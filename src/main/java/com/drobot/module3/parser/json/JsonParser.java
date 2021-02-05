package com.drobot.module3.parser.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public abstract class JsonParser<E> {

    public abstract E parse(JSONObject jsonObject);

    public Set<E> parseArray(String text) {
        final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
        Set<E> result = new HashSet<>();
        JSONArray jsonArray = new JSONArray(text);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            E e = parse(jsonObject);
            result.add(e);
        }
        LOGGER.debug("Json array has been parsed");
        return result;
    }

    protected String parseNullableString(JSONObject jsonObject, String key) {
        String string;
        try {
            string = jsonObject.getString(key);
        } catch (JSONException e) {
            string = null;
        }
        return string;
    }

    protected Boolean parseNullableBoolean(JSONObject jsonObject, String key) {
        Boolean bool;
        try {
            bool = jsonObject.getBoolean(key);
        } catch (JSONException e) {
            bool = null;
        }
        return bool;
    }

    protected <T> T parseNullableObject(JSONObject jsonObject, String key, JsonParser<T> parser) {
        T t;
        try {
            JSONObject object = jsonObject.getJSONObject(key);
            t = parser.parse(object);
        } catch (JSONException e) {
            t = null;
        }
        return t;
    }
}
