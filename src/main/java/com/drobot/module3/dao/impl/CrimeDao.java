package com.drobot.module3.dao.impl;

import com.drobot.module3.dao.TransactionDao;
import com.drobot.module3.dao.LocationDao;
import com.drobot.module3.dao.OutcomeStatusDao;
import com.drobot.module3.dao.SqlStatement;
import com.drobot.module3.dao.StreetDao;
import com.drobot.module3.entity.Crime;
import com.drobot.module3.entity.CrimeCategory;
import com.drobot.module3.entity.Location;
import com.drobot.module3.entity.OutcomeStatus;
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

public enum CrimeDao implements TransactionDao<Crime> {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(CrimeDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    @Override
    public void addAll(Collection<Crime> crimes) throws DaoException {
        Query query = fluentJdbc.query();
        try {
            query.transaction().inNoResult(
                    () -> {
                        for (Crime crime : crimes) {
                            CrimeCategory crimeCategory = crime.getCrimeCategory();
                            Location location = crime.getLocation();
                            OutcomeStatus outcomeStatus = crime.getOutcomeStatus();
                            Street street = location.getStreet();
                            StreetDao streetDao = StreetDao.INSTANCE;
                            streetDao.add(street, query);
                            LocationDao locationDao = LocationDao.INSTANCE;
                            long locationId = locationDao.add(location, query);
                            OutcomeStatusDao outcomeStatusDao = OutcomeStatusDao.INSTANCE;
                            Long outcomeStatusId = outcomeStatusDao.add(outcomeStatus, query);
                            query.update(SqlStatement.ADD_CRIME)
                                    .params(crime.getId(), crimeCategory.getId(), crime.getLocationType().toString(),
                                            locationId, crime.getContext(), outcomeStatusId, crime.getPersistentId(),
                                            crime.getLocationSubtype(), crime.getMonth())
                                    .run();
                        }
                    }
            );
            LOGGER.debug(crimes.size() + " crimes have been added");
        } catch (FluentJdbcException e) {
            throw new DaoException("Error while crime adding transaction", e);
        }
    }

    public boolean exists(long id) throws DaoException {
        boolean result;
        try {
            Query query = fluentJdbc.query();
            result = query.select(SqlStatement.EXISTS_CRIME)
                    .params(id)
                    .singleResult(Mappers.singleInteger()) != 0;
            LOGGER.debug("Crime with id " + id + " exists: " + result);
        } catch (FluentJdbcException e) {
            throw new DaoException("Error while finding a crime by ID: " + id, e);
        }
        return result;
    }
}
