package com.drobot.module3.service.impl;

import com.drobot.module3.dao.TransactionDao;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.parser.PropertyKey;
import com.drobot.module3.parser.json.JsonParser;
import com.drobot.module3.service.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class BaseDownloader<E> implements Downloader {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final int REQUEST_LIMIT = 15;

    protected YearMonth[] parseDate(Map<String, String> params) {
        String date = params.get(PropertyKey.DATE);
        String date2 = params.get(PropertyKey.DATE2);
        YearMonth fromDate = YearMonth.parse(date);
        YearMonth toDate = fromDate;
        if (date2 != null) {
            toDate = YearMonth.parse(date2);
            if (fromDate.isAfter(toDate)) {
                toDate = fromDate;
                fromDate = YearMonth.parse(date2);
            }
        }
        return new YearMonth[]{fromDate, toDate};
    }

    protected List<E> parseJsons(List<String> jsons, JsonParser<E> jsonParser) {
        List<E> result = new ArrayList<>();
        for (String json : jsons) {
            Set<E> objects = jsonParser.parseArray(json);
            result.addAll(objects);
        }
        return result;
    }

    protected void saveToDatabase(List<E> objectList, TransactionDao<E> dao) throws ServiceException {
        int initialSize = objectList.size();
        removeExistingObjects(objectList);
        int currentSize = objectList.size();
        if (initialSize != currentSize) {
            LOGGER.info(initialSize - currentSize + " objects are already contained in database");
        }
        if (!objectList.isEmpty()) {
            try {
                dao.addAll(objectList);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            LOGGER.info("All requested objects are already contained in database");
        }
    }

    protected void considerCallLimit(int iteration) {
        if (iteration % REQUEST_LIMIT == 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
    }

    protected abstract void removeExistingObjects(List<E> objectList) throws ServiceException;
}
