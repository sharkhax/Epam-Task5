package com.drobot.module3.service.impl;

import com.drobot.module3.builder.RequestBuilder;
import com.drobot.module3.builder.RequestKey;
import com.drobot.module3.builder.impl.StopByForceRequestBuilder;
import com.drobot.module3.client.HttpClient;
import com.drobot.module3.client.Response;
import com.drobot.module3.dao.LocationDao;
import com.drobot.module3.dao.StopTypeDao;
import com.drobot.module3.dao.StreetDao;
import com.drobot.module3.dao.impl.ForceDao;
import com.drobot.module3.dao.impl.StopForceDao;
import com.drobot.module3.entity.Location;
import com.drobot.module3.entity.StopByForce;
import com.drobot.module3.entity.StopType;
import com.drobot.module3.entity.Street;
import com.drobot.module3.exception.ClientException;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.parser.PropertyKey;
import com.drobot.module3.parser.json.StopByForceJsonParser;
import com.drobot.module3.service.ForceService;
import com.drobot.module3.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class StopsForceDownloader extends BaseDownloader<StopByForce> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopsForceDownloader.class);
    private static final int FORCES_NUMBER = 44;
    private final StopForceDao stopForceDao = StopForceDao.INSTANCE;
    private final ForceDao forceDao = ForceDao.INSTANCE;

    @Override
    public void download(Map<String, String> params) throws ServiceException {
        List<StopByForce> stopList = downloadFromApi(params);
        saveToDatabase(stopList, stopForceDao);
        LOGGER.info("Downloading stops by force to database completed");
    }

    @Override
    protected void removeExistingObjects(List<StopByForce> stopList) throws ServiceException {
        try {
            Iterator<StopByForce> iterator = stopList.iterator();
            while (iterator.hasNext()) {
                StopByForce stopByForce = iterator.next();
                updateStopType(stopByForce);
                updateLocation(stopByForce);
                if (stopForceDao.exists(stopByForce)) {
                    iterator.remove();
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void updateStopType(StopByForce stopByForce) throws DaoException {
        StopTypeDao stopTypeDao = StopTypeDao.INSTANCE;
        StopType stopType = stopByForce.getType();
        String typeName = stopType.getName();
        long typeId = stopTypeDao.findByName(typeName);
        if (typeId == 0) {
            typeId = stopTypeDao.add(stopType);
        }
        stopType.setId(typeId);
    }

    private void updateLocation(StopByForce stopByForce) throws DaoException {
        Location location = stopByForce.getLocation();
        if (location != null) {
            Street street = location.getStreet();
            StreetDao streetDao = StreetDao.INSTANCE;
            streetDao.add(street);
            LocationDao locationDao = LocationDao.INSTANCE;
            long id = locationDao.add(location);
            location.setId(id);
        }
    }

    private List<StopByForce> downloadFromApi(Map<String, String> params) throws ServiceException {
        try {
            loadForcesIfAbsent();
            if (!areParamsValid(params)) {
                throw new ServiceException("Some required params are absent or invalid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        YearMonth[] dateInterval = parseDate(params);
        String forceId = params.get(PropertyKey.FORCE);
        List<String> jsons = requestJsons(dateInterval, forceId);
        if (jsons.isEmpty()) {
            throw new ServiceException("No stops by force have been downloaded");
        }
        return parseJsons(jsons, new StopByForceJsonParser());
    }

    private boolean areParamsValid(Map<String, String> params) throws DaoException {
        String date = params.get(PropertyKey.DATE);
        String date2 = params.get(PropertyKey.DATE2);
        String forceId = params.get(PropertyKey.FORCE);
        return RequestValidator.isDateValid(date) && forceId != null && forceDao.exists(forceId)
                && (date2 == null || RequestValidator.isDateValid(date2));
    }

    private void loadForcesIfAbsent() throws ServiceException, DaoException {
        if (forceDao.count() < FORCES_NUMBER) {
            LOGGER.debug("Downloading forces");
            ForceService forceService = new ForceService();
            forceService.downloadToDatabase();
        }
    }

    private List<String> requestJsons(YearMonth[] dateInterval, String forceId) {
        YearMonth fromDate = dateInterval[0];
        YearMonth toDate = dateInterval[1];
        List<String> jsons = new ArrayList<>();
        int monthDif = (int) fromDate.until(toDate, ChronoUnit.MONTHS);
        for (int i = 0; i <= monthDif; i++) {
            YearMonth currentDate = fromDate.plusMonths(i);
            try {
                String json = requestJson(currentDate, forceId);
                jsons.add(json);
            } catch (ClientException e) {
                LOGGER.error("Stops by force on " + currentDate + " were not downloaded", e);
            }
            considerCallLimit(i + 1);
        }
        return jsons;
    }

    private String requestJson(YearMonth date, String forceId) throws ClientException {
        RequestBuilder builder = StopByForceRequestBuilder.getInstance();
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(RequestKey.DATE, date.toString());
        requestParams.put(RequestKey.FORCE, forceId);
        String url = builder.build(requestParams);
        HttpClient client = new HttpClient(url, HttpClient.RequestMethod.GET);
        Response response = client.makeRequest();
        return client.parseResponse(response);
    }
}
