package com.drobot.module3.dao;

import com.drobot.module3.entity.OutcomeStatus;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public enum OutcomeStatusDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(OutcomeStatusDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    public Long add(OutcomeStatus outcomeStatus, Query query) throws FluentJdbcException {
        Long id = null;
        if (outcomeStatus == null) {
            return id;
        }
        if (!exists(outcomeStatus)) {
            String category = outcomeStatus.getCategory();
            String date = outcomeStatus.getDate();
            id = query.update(SqlStatement.ADD_OUTCOME_STATUS)
                    .params(category, date)
                    .runFetchGenKeys(Mappers.singleLong())
                    .generatedKeys().get(0);
            outcomeStatus.setId(id);
            LOGGER.debug("Outcome status " + category + " " + date + " has been added");
        } else {
            id = outcomeStatus.getId();
        }
        return id;
    }

    private boolean exists(OutcomeStatus outcomeStatus) throws FluentJdbcException {
        String category = outcomeStatus.getCategory();
        String date = outcomeStatus.getDate();
        Query query = fluentJdbc.query();
        Optional<Long> optionalId = query.select(SqlStatement.EXISTS_OUTCOME_STATUS)
                .params(category, date)
                .firstResult(Mappers.singleLong());
        String log;
        if (optionalId.isPresent()) {
            outcomeStatus.setId(optionalId.get());
            log = "Outcome status " + category + " " + date + " exists";
        } else {
            log = "Outcome status " + category + " " + date + " doesn't exist";
        }
        LOGGER.debug(log);
        return optionalId.isPresent();
    }
}
