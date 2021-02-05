package com.drobot.module3.dao;

public final class SqlStatement {

    public static final String FIND_CRIME_CATEGORY_BY_ITS_URL =
            "SELECT crime_category_id, category_url, crime_category_name FROM police.crime_categories WHERE category_url = ?";
    public static final String ADD_CRIME = "INSERT INTO police.crimes(crime_id, crime_category, location_type, " +
            "location, context, outcome_status, persistent_id, location_subtype, month) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String ADD_LOCATION =
            "INSERT INTO police.locations(latitude, street, longitude) VALUES(?, ?, ?);";
    public static final String ADD_OUTCOME_STATUS =
            "INSERT INTO police.outcome_statuses(category, date) VALUES(?, ?);";
    public static final String ADD_STREET =
            "INSERT INTO police.streets(street_id, street_name) VALUES(?, ?);";
    public static final String ADD_CRIME_CATEGORY =
            "INSERT INTO police.crime_categories(category_url, crime_category_name) VALUES(?, ?);";
    public static final String COUNT_CRIME_CATEGORIES = "SELECT COUNT(*) AS label FROM police.crime_categories;";
    public static final String EXISTS_CRIME = "SELECT COUNT(*) AS label FROM police.crimes WHERE crime_id = ?;";
    public static final String EXISTS_STREET = "SELECT COUNT(*) AS label FROM police.streets WHERE street_id = ?;";
    public static final String EXISTS_LOCATION =
            "SELECT location_id FROM police.locations WHERE latitude = ? AND longitude = ?;";
    public static final String EXISTS_OUTCOME_STATUS =
            "SELECT outcome_status_id FROM police.outcome_statuses WHERE category = ? AND date = ?;";
    public static final String EXISTS_CRIME_CATEGORY =
            "SELECT COUNT(*) AS label FROM police.crime_categories WHERE category_url = ?";
    public static final String ADD_FORCE = "INSERT INTO police.forces(force_id, force_name) VALUES(?, ?);";
    public static final String COUNT_FORCES = "SELECT COUNT(*) AS label FROM police.forces;";
    public static final String EXISTS_FORCE = "SELECT COUNT(*) AS label FROM police.forces WHERE force_id = ?;";
    public static final String ADD_STOP_TYPE = "INSERT INTO police.stop_types(type_name) VALUES(?);";
    public static final String FIND_STOP_TYPE_BY_NAME = "SELECT type_id FROM police.stop_types WHERE type_name = ?;";
    public static final String ADD_OUTCOME_OBJECT =
            "INSERT INTO police.outcome_objects(outcome_object_id, outcome_object_name) VALUES(?, ?);";
    public static final String EXISTS_OUTCOME_OBJECT =
            "SELECT COUNT(*) AS label FROM police.outcome_objects WHERE outcome_object_id = ?";
    public static final String ADD_STOP_BY_FORCE =
            "INSERT INTO police.stops_force(age_range, self_defined_ethnicity, outcome_linked_to_object_of_search, " +
                    "datetime, removal_of_more_than_outer_clothing, operation, officer_defined_ethnicity, " +
                    "object_of_search, involved_person, gender, legislation, location, type, operation_name, " +
                    "outcome_object) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String EXISTS_STOP_BY_FORCE =
            "SELECT COUNT(*) AS label FROM police.stops_force WHERE age_range IS NOT DISTINCT FROM ?" +
                    " AND self_defined_ethnicity IS NOT DISTINCT FROM ?" +
                    " AND outcome_linked_to_object_of_search IS NOT DISTINCT FROM ?" +
                    " AND datetime = ?" +
                    " AND removal_of_more_than_outer_clothing IS NOT DISTINCT FROM ?" +
                    " AND operation IS NOT DISTINCT FROM ?" +
                    " AND officer_defined_ethnicity IS NOT DISTINCT FROM ?" +
                    " AND object_of_search IS NOT DISTINCT FROM ?" +
                    " AND involved_person = ?" +
                    " AND gender IS NOT DISTINCT FROM ?" +
                    " AND legislation IS NOT DISTINCT FROM ?" +
                    " AND location IS NOT DISTINCT FROM ?" +
                    " AND type = ?" +
                    " AND operation_name IS NOT DISTINCT FROM ?" +
                    " AND outcome_object IS NOT DISTINCT FROM ?;";

    private SqlStatement() {
    }
}
