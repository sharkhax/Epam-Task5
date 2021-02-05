package com.drobot.module3.dao;

import com.drobot.module3.entity.Street;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum StreetDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(StreetDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    public boolean add(Street street) throws DaoException {
        Query query = fluentJdbc.query();
        try {
            return add(street, query);
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
    }

    public boolean add(Street street, Query query) throws FluentJdbcException {
        boolean result = false;
        long streetId = street.getId();
        if (!exists(streetId)) {
            query.update(SqlStatement.ADD_STREET)
                    .params(streetId, street.getName())
                    .run();
            result = true;
            LOGGER.debug("Street " + streetId + " has been added");
        }
        return result;
    }

    private boolean exists(long id) throws FluentJdbcException {
        Query query = fluentJdbc.query();
        int counter = query.select(SqlStatement.EXISTS_STREET)
                .params(id)
                .singleResult(Mappers.singleInteger());
        String log = counter != 0 ? "Street with id " + id + " exists" : "Street with id " + id + " doesn't exist";
        LOGGER.debug(log);
        return counter != 0;
    }
}
