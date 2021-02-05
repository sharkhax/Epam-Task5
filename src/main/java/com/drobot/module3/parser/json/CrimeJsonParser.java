package com.drobot.module3.parser.json;

import com.drobot.module3.entity.Crime;
import com.drobot.module3.entity.CrimeCategory;
import com.drobot.module3.entity.Location;
import com.drobot.module3.entity.LocationType;
import com.drobot.module3.entity.OutcomeStatus;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrimeJsonParser extends JsonParser<Crime> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrimeJsonParser.class);
    private static final String CRIME_ID = "id";
    private static final String CATEGORY = "category";
    private static final String LOCATION_TYPE = "location_type";
    private static final String LOCATION = "location";
    private static final String CONTEXT = "context";
    private static final String OUTCOME_STATUS = "outcome_status";
    private static final String PERSISTENT_ID = "persistent_id";
    private static final String LOCATION_SUBTYPE = "location_subtype";
    private static final String MONTH = "month";
    private final LocationJsonParser locationJsonParser = new LocationJsonParser();
    private final OutcomeStatusJsonParser outcomeStatusJsonParser = new OutcomeStatusJsonParser();

    @Override
    public Crime parse(JSONObject jsonObject) {
        long crimeId = jsonObject.getLong(CRIME_ID);
        String categoryUrl = jsonObject.getString(CATEGORY);
        CrimeCategory category = new CrimeCategory(categoryUrl);
        String stringLocationType = jsonObject.getString(LOCATION_TYPE);
        LocationType locationType = LocationType.valueOf(stringLocationType.toUpperCase());
        JSONObject locationJsonObject = jsonObject.getJSONObject(LOCATION);
        Location location = locationJsonParser.parse(locationJsonObject);
        String context = jsonObject.getString(CONTEXT);
        OutcomeStatus outcomeStatus = parseNullableObject(jsonObject, OUTCOME_STATUS, outcomeStatusJsonParser);
        String persistentId = jsonObject.getString(PERSISTENT_ID);
        String locationSubtype = jsonObject.getString(LOCATION_SUBTYPE);
        String month = jsonObject.getString(MONTH);
        Crime crime = new Crime();
        crime = new Crime.Builder(crime)
                .buildId(crimeId)
                .buildCategory(category)
                .buildLocationType(locationType)
                .buildLocation(location)
                .buildContext(context)
                .buildOutcomeStatus(outcomeStatus)
                .buildPersistentId(persistentId)
                .buildLocationSubtype(locationSubtype)
                .buildMonth(month)
                .build();
        LOGGER.debug("Crime " + crimeId + " has been parsed");
        return crime;
    }
}
