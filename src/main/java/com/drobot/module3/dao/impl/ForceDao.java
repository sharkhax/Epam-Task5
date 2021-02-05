package com.drobot.module3.dao.impl;

import com.drobot.module3.dao.TransactionDao;
import com.drobot.module3.dao.SqlStatement;
import com.drobot.module3.entity.Force;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public enum ForceDao implements TransactionDao<Force> {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(ForceDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    public int count() throws DaoException {
        int result;
        try {
            Query query = fluentJdbc.query();
            result = query.select(SqlStatement.COUNT_FORCES)
                    .singleResult(Mappers.singleInteger());
            LOGGER.debug("Forces have been counted: " + result + " total");
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public void addAll(Collection<Force> forces) throws DaoException {
        Query query = fluentJdbc.query();
        try {
            query.transaction().inNoResult(
                    () -> {
                        for (Force force : forces) {
                            query.update(SqlStatement.ADD_FORCE)
                                    .params(force.getId(), force.getName())
                                    .run();
                        }
                    }
            );
            LOGGER.debug(forces.size() + " forces have been added");
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
    }

    public boolean exists(String forceId) throws DaoException {
        Query query = fluentJdbc.query();
        boolean result;
        try {
            result = query.select(SqlStatement.EXISTS_FORCE)
                    .params(forceId)
                    .singleResult(Mappers.singleInteger()) != 0;
            String log = result ? "Force with id " + forceId + " exists"
                    : "Force with id " + forceId + " doesn't exist";
            LOGGER.debug(log);
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
