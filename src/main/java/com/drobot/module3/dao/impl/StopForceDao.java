package com.drobot.module3.dao.impl;

import com.drobot.module3.dao.*;
import com.drobot.module3.entity.Location;
import com.drobot.module3.entity.OutcomeObject;
import com.drobot.module3.entity.StopByForce;
import com.drobot.module3.entity.Street;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public enum StopForceDao implements TransactionDao<StopByForce> {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(StopForceDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    @Override
    public void addAll(Collection<StopByForce> stops) throws DaoException {
        try {
            Query query = fluentJdbc.query();
            query.transaction().inNoResult(
                    () -> {
                        for (StopByForce stop : stops) {
                            Location location = stop.getLocation();
                            if (location != null) {
                                Street street = location.getStreet();
                                StreetDao streetDao = StreetDao.INSTANCE;
                                streetDao.add(street, query);
                                LocationDao locationDao = LocationDao.INSTANCE;
                                Long locationId = locationDao.add(location, query);
                                location.setId(locationId);
                            }
                            OutcomeObject outcomeObject = stop.getOutcomeObject();
                            OutcomeObjectDao outcomeObjectDao = OutcomeObjectDao.INSTANCE;
                            outcomeObjectDao.add(outcomeObject, query);
                            query.update(SqlStatement.ADD_STOP_BY_FORCE)
                                    .params(fillParams(stop))
                                    .run();
                        }
                    }
            );
            LOGGER.debug(stops.size() + " stops by force have been added");
        } catch (FluentJdbcException e) {
            throw new DaoException("Error while stops by force adding transaction", e);
        }
    }

    public boolean exists(StopByForce stopByForce) throws DaoException {
        Query query = fluentJdbc.query();
        try {
            return query.select(SqlStatement.EXISTS_STOP_BY_FORCE)
                    .params(fillParams(stopByForce))
                    .singleResult(Mappers.singleInteger()) != 0;
        } catch (FluentJdbcException e) {
            throw new DaoException("Error while finding stops by force", e);
        }
    }

    private Object[] fillParams(StopByForce stop) throws FluentJdbcException {
        String ageRange = stop.getAgeRage();
        String selfDefinedEthnicity = stop.getSelfDefinedEthnicity();
        Boolean outcomeLinkedToObjectOfSearch = stop.getOutcomeObjectLinkedToObjectOfSearch();
        String dateTime = stop.getDateTime().toString();
        Boolean removalOfMoreThanOuterClothing = stop.getRemovalOfMoreThanOuterClothing();
        Boolean operation = stop.getOperation();
        String officerDefinedEthnicity = stop.getOfficerDefinedEthnicity();
        String objectOfSearch = stop.getObjectOfSearch();
        boolean involvedPerson = stop.getInvolvedPerson();
        String gender = stop.getGender();
        String legislation = stop.getLegislation();
        Location location = stop.getLocation();
        Long locationId = null;
        if (location != null) {
            locationId = location.getId();
        }
        long typeId = stop.getType().getId();
        String operationName = stop.getOperationName();
        OutcomeObject outcomeObject = stop.getOutcomeObject();
        String outcomeObjectId = null;
        if (outcomeObject != null) {
            outcomeObjectId = outcomeObject.getId();
        }
        return new Object[]{
                ageRange, selfDefinedEthnicity, outcomeLinkedToObjectOfSearch, dateTime,
                removalOfMoreThanOuterClothing, operation, officerDefinedEthnicity, objectOfSearch,
                involvedPerson, gender, legislation, locationId, typeId, operationName, outcomeObjectId
        };
    }
}
