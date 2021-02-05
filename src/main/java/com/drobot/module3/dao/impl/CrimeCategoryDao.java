package com.drobot.module3.dao.impl;

import com.drobot.module3.dao.TransactionDao;
import com.drobot.module3.dao.SqlStatement;
import com.drobot.module3.entity.CrimeCategory;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Mapper;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public enum CrimeCategoryDao implements TransactionDao<CrimeCategory> {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(CrimeCategoryDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();
    private final Mapper<CrimeCategory> mapper = (resultSet -> new CrimeCategory(
            resultSet.getLong("crime_category_id"),
            resultSet.getString("category_url"),
            resultSet.getString("crime_category_name")));

    public int count() throws DaoException {
        int result;
        try {
            Query query = fluentJdbc.query();
            result = query.select(SqlStatement.COUNT_CRIME_CATEGORIES)
                    .singleResult(Mappers.singleInteger());
            LOGGER.debug("Crime categories have been counted: " + result + " total");
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public void addAll(Collection<CrimeCategory> crimeCategories) throws DaoException {
        Query query = fluentJdbc.query();
        try {
            query.transaction().inNoResult(
                    () -> {
                        for (CrimeCategory crimeCategory : crimeCategories) {
                            query.update(SqlStatement.ADD_CRIME_CATEGORY)
                                    .params(crimeCategory.getUrl(), crimeCategory.getName())
                                    .run();
                        }
                    }
            );
            LOGGER.debug(crimeCategories.size() + " crime categories have been added");
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
    }

    public Optional<CrimeCategory> findByCategoryUrl(String categoryUrl) throws DaoException {
        Optional<CrimeCategory> result;
        Query query = fluentJdbc.query();
        try {
            result = query.select(SqlStatement.FIND_CRIME_CATEGORY_BY_ITS_URL)
                    .params(categoryUrl)
                    .firstResult(mapper);
            String log = result.isPresent() ? "Crime category with url \"" + categoryUrl + "\" has been found"
                    : "Crime category with url \"" + categoryUrl + "\" has not been found";
            LOGGER.debug(log);
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
        return result;
    }

    public boolean exists(String categoryUrl) throws DaoException {
        Query query = fluentJdbc.query();
        boolean result;
        try {
            result = query.select(SqlStatement.EXISTS_CRIME_CATEGORY)
                    .params(categoryUrl)
                    .singleResult(Mappers.singleInteger()) != 0;
            String log = result ? "Crime category with url " + categoryUrl + " exists"
                    : "Crime category with url " + categoryUrl + " doesn't exist";
            LOGGER.debug(log);
        } catch (FluentJdbcException e) {
            throw new DaoException(e);
        }
        return result;
    }
}