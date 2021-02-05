package com.drobot.module3.parser.json;

import com.drobot.module3.entity.Location;
import com.drobot.module3.entity.OutcomeObject;
import com.drobot.module3.entity.StopByForce;
import com.drobot.module3.entity.StopType;
import org.json.JSONObject;

import java.time.OffsetDateTime;

public class StopByForceJsonParser extends JsonParser<StopByForce> {

    private static final String AGE_RANGE_KEY = "age_range";
    private static final String SELF_DEFINED_ETHNICITY_KEY = "self_defined_ethnicity";
    private static final String OUTCOME_OBJECT_LINKED_TO_OBJECT_OF_SEARCH_KEY = "outcome_linked_to_object_of_search";
    private static final String DATE_TIME_KEY = "datetime";
    private static final String REMOVAL_OF_MORE_THAN_OUTER_CLOTHING = "removal_of_more_than_outer_clothing";
    private static final String OPERATION_KEY = "operation";
    private static final String OFFICER_DEFINED_ETHNICITY_KEY = "officer_defined_ethnicity";
    private static final String OBJECT_OF_SEARCH_KEY = "object_of_search";
    private static final String INVOLVED_PERSON_KEY = "involved_person";
    private static final String GENDER_KEY = "gender";
    private static final String LEGISLATION_KEY = "legislation";
    private static final String LOCATION_KEY = "location";
    private static final String TYPE_KEY = "type";
    private static final String OPERATION_NAME_KEY = "operation_name";
    private static final String OUTCOME_OBJECT_KEY = "outcome_object";
    private final LocationJsonParser locationJsonParser = new LocationJsonParser();
    private final OutcomeObjectJsonParser outcomeObjectJsonParser = new OutcomeObjectJsonParser();

    @Override
    public StopByForce parse(JSONObject jsonObject) {
        String ageRage = parseNullableString(jsonObject, AGE_RANGE_KEY);
        String selfDefinedEthnicity = parseNullableString(jsonObject, SELF_DEFINED_ETHNICITY_KEY);
        Boolean outcomeObjectLinkedToObjectOfSearch
                = parseNullableBoolean(jsonObject, OUTCOME_OBJECT_LINKED_TO_OBJECT_OF_SEARCH_KEY);
        String stringDateTime = jsonObject.getString(DATE_TIME_KEY);
        OffsetDateTime dateTime = OffsetDateTime.parse(stringDateTime);
        Boolean removalOfMoreThanOuterClothing = parseNullableBoolean(jsonObject, REMOVAL_OF_MORE_THAN_OUTER_CLOTHING);
        Boolean operation = parseNullableBoolean(jsonObject, OPERATION_KEY);
        String officerDefinedEthnicity = parseNullableString(jsonObject, OFFICER_DEFINED_ETHNICITY_KEY);
        String objectOfSearch = parseNullableString(jsonObject, OBJECT_OF_SEARCH_KEY);
        boolean involvedPerson = jsonObject.getBoolean(INVOLVED_PERSON_KEY);
        String gender = parseNullableString(jsonObject, GENDER_KEY);
        String legislation = parseNullableString(jsonObject, LEGISLATION_KEY);
        Location location = parseNullableObject(jsonObject, LOCATION_KEY, locationJsonParser);
        String typeName = jsonObject.getString(TYPE_KEY);
        StopType type = new StopType(typeName);
        String operationName = parseNullableString(jsonObject, OPERATION_NAME_KEY);
        OutcomeObject outcomeObject = parseNullableObject(jsonObject, OUTCOME_OBJECT_KEY, outcomeObjectJsonParser);
        StopByForce stopByForce = new StopByForce();
        stopByForce = stopByForce.builder()
                .buildAgeRange(ageRage)
                .buildSelfDefinedEthnicity(selfDefinedEthnicity)
                .buildOutcomeObjectLinkedToObjectOfSearch(outcomeObjectLinkedToObjectOfSearch)
                .buildDateTime(dateTime)
                .buildRemovalOfMoreThanOuterClothing(removalOfMoreThanOuterClothing)
                .buildOperation(operation)
                .buildOfficerDefinedEthnicity(officerDefinedEthnicity)
                .buildObjectOfSearch(objectOfSearch)
                .buildInvolvedPerson(involvedPerson)
                .buildGender(gender)
                .buildLegislation(legislation)
                .buildLocation(location)
                .buildType(type)
                .buildOperationName(operationName)
                .buildOutcomeObject(outcomeObject)
                .buildOutcomeObject(outcomeObject)
                .build();
        return stopByForce;
    }
}
