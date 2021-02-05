package com.drobot.module3.dao;

import com.drobot.module3.entity.StopType;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum StopTypeDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(StopTypeDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    public long add(StopType stopType) throws DaoException {
        Query query = fluentJdbc.query();
        String typeName = stopType.getName();
        long typeId;
        try {
            typeId = query.update(SqlStatement.ADD_STOP_TYPE)
                    .params(typeName)
                    .runFetchGenKeys(Mappers.singleLong())
                    .generatedKeys().get(0);
            LOGGER.debug("Stop type has been added with id: " + typeId);
        } catch (FluentJdbcException e) {
            throw new DaoException("Error while adding a stop type", e);
        }
        return typeId;
    }

    public long findByName(String typeName) throws DaoException {
        Query query = fluentJdbc.query();
        Optional<Long> optionalId;
        try {
            optionalId = query.select(SqlStatement.FIND_STOP_TYPE_BY_NAME)
                    .params(typeName)
                    .firstResult(Mappers.singleLong());
            String log = optionalId.isPresent() ? "Stop type with name " + typeName + " was found"
                    : "Stop type with name " + typeName + " was not found";
            LOGGER.debug(log);
        } catch (FluentJdbcException e) {
            throw new DaoException("Error while adding a stop type", e);
        }
        return optionalId.orElse(0L);
    }
}
