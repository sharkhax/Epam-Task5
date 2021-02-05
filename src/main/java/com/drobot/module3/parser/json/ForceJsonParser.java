package com.drobot.module3.parser.json;

import com.drobot.module3.entity.Force;
import org.json.JSONObject;

public class ForceJsonParser extends JsonParser<Force> {

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";

    @Override
    public Force parse(JSONObject jsonObject) {
        String id = jsonObject.getString(ID_KEY);
        String name = jsonObject.getString(NAME_KEY);
        return new Force(id, name);
    }
}
