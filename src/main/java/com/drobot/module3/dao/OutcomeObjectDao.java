package com.drobot.module3.dao;

import com.drobot.module3.entity.OutcomeObject;
import com.drobot.module3.pool.ConnectionPool;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcException;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum OutcomeObjectDao {

    INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(OutcomeObjectDao.class);
    private final FluentJdbc fluentJdbc = ConnectionPool.getFluentJdbc();

    public void add(OutcomeObject outcomeObject, Query query) throws FluentJdbcException {
        if (outcomeObject == null) {
            return;
        }
        if (!exists(outcomeObject)) {
            query.update(SqlStatement.ADD_OUTCOME_OBJECT)
                    .params(outcomeObject.getId(), outcomeObject.getName())
                    .run();
        }
    }

    private boolean exists(OutcomeObject outcomeObject) throws FluentJdbcException {
        Query query = fluentJdbc.query();
        String id = outcomeObject.getId();
        boolean result = query.select(SqlStatement.EXISTS_OUTCOME_OBJECT)
                .params(id)
                .singleResult(Mappers.singleInteger()) != 0;
        String log = result ? "Outcome object " + id + " exists" : "Outcome object " + id + " doesn't exist";
        LOGGER.debug(log);
        return result;
    }
}
