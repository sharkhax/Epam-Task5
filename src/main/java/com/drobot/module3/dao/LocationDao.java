package com.drobot.module3.dao;

import com.drobot.module3.entity.Location;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum LocationDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(LocationDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    public Long add(Location location) throws DaoException {
        Query query = fluentJdbc.query();
        try {
            return add(location, query);
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
    }

    public Long add(Location location, Query query) throws FluentJdbcException {
        Long locationId = null;
        if (location == null) {
            return locationId;
        }
        if (!exists(location)) {
            float latitude = location.getLatitude();
            long streetId = location.getStreet().getId();
            float longitude = location.getLongitude();
            locationId = query.update(SqlStatement.ADD_LOCATION)
                    .params(latitude, streetId, longitude)
                    .runFetchGenKeys(Mappers.singleLong())
                    .generatedKeys().get(0);
            location.setId(locationId);
            LOGGER.debug("Location " + latitude + " " + longitude + " has been added");
        } else {
            locationId = location.getId();
        }
        return locationId;
    }

    private boolean exists(Location location) throws FluentJdbcException {
        float latitude = location.getLatitude();
        float longitude = location.getLongitude();
        Query query = fluentJdbc.query();
        Optional<Long> optionalId = query.select(SqlStatement.EXISTS_LOCATION)
                .params(latitude, longitude)
                .firstResult(Mappers.singleLong());
        String log;
        if (optionalId.isPresent()) {
            log = "Location " + latitude + " " + longitude + " exists";
            location.setId(optionalId.get());
        } else {
            log = "Location " + latitude + " " + longitude + " doesn't exist";
        }
        LOGGER.debug(log);
        return optionalId.isPresent();
    }
}
