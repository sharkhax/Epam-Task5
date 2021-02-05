package com.drobot.module3.service.impl.crime;

import com.drobot.module3.builder.RequestKey;
import com.drobot.module3.builder.impl.CrimeRequestBuilder;
import com.drobot.module3.client.HttpClient;
import com.drobot.module3.client.Response;
import com.drobot.module3.dao.impl.CrimeCategoryDao;
import com.drobot.module3.dao.impl.CrimeDao;
import com.drobot.module3.entity.Coordinate;
import com.drobot.module3.entity.Crime;
import com.drobot.module3.entity.CrimeCategory;
import com.drobot.module3.exception.ClientException;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.parser.CoordinatesParser;
import com.drobot.module3.parser.PropertyKey;
import com.drobot.module3.parser.json.CrimeJsonParser;
import com.drobot.module3.io.DataReader;
import com.drobot.module3.service.CrimeCategoryService;
import com.drobot.module3.service.impl.BaseDownloader;
import com.drobot.module3.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CrimesDownloader extends BaseDownloader<Crime> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrimesDownloader.class);
    private static final int CRIME_CATEGORIES_NUMBER = 15;
    private final CrimeDao crimeDao = CrimeDao.INSTANCE;
    private final CrimeCategoryDao categoryDao = CrimeCategoryDao.INSTANCE;

    @Override
    public void download(Map<String, String> params) throws ServiceException {
        List<Crime> crimes = downloadFromApi(params);
        saveToDatabase(crimes, crimeDao);
        LOGGER.info("Downloading crimes to database completed");
    }

    protected List<Crime> downloadFromApi(Map<String, String> params) throws ServiceException {
        if (!areParamsValid(params)) {
            throw new ServiceException("Some required params are absent or invalid");
        }
        YearMonth[] dateInterval = parseDate(params);
        YearMonth fromDate = dateInterval[0];
        YearMonth toDate = dateInterval[1];
        String coordsFile = params.get(PropertyKey.COORDS_FILE);
        List<Coordinate> coordinates = getCoordinatesList(coordsFile);
        loadCrimeCategoriesIfAbsent();
        List<String> jsons = requestJsons(fromDate, toDate, coordinates);
        if (jsons.isEmpty()) {
            throw new ServiceException("No crimes have been downloaded");
        }
        return parseJsons(jsons, new CrimeJsonParser());
    }

    @Override
    protected void removeExistingObjects(List<Crime> crimes) throws ServiceException {
        try {
            Iterator<Crime> iterator = crimes.iterator();
            while (iterator.hasNext()) {
                Crime crime = iterator.next();
                if (crimeDao.exists(crime.getId())) {
                    iterator.remove();
                } else {
                    updateCrimeCategory(crime);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    protected void updateCrimeCategory(Crime crime) throws ServiceException {
        try {
            String categoryUrl = crime.getCrimeCategory().getUrl();
            Optional<CrimeCategory> category = categoryDao.findByCategoryUrl(categoryUrl);
            if (category.isPresent()) {
                crime.setCrimeCategory(category.get());
            } else {
                throw new ServiceException("Crime category " + categoryUrl + " does not exist");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean areParamsValid(Map<String, String> params) {
        boolean result = false;
        if (params.containsKey(PropertyKey.DATE) && params.containsKey(PropertyKey.COORDS_FILE)) {
            String fromDate = params.get(PropertyKey.DATE);
            String toDate = params.get(PropertyKey.DATE2);
            result = RequestValidator.isDateValid(fromDate)
                    && (toDate == null || RequestValidator.isDateValid(toDate));
        }
        return result;
    }

    private List<Coordinate> getCoordinatesList(String filePath) throws ServiceException {
        try {
            List<String> fileContent = DataReader.readFile(filePath);
            List<Coordinate> result = new ArrayList<>();
            CoordinatesParser parser = new CoordinatesParser();
            for (String line : fileContent) {
                Coordinate coordinate = parser.parse(line);
                result.add(coordinate);
            }
            return result;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    private List<String> requestJsons(YearMonth fromDate, YearMonth toDate, List<Coordinate> coordinates) {
        List<String> jsons = new ArrayList<>();
        int monthDif = (int) fromDate.until(toDate, ChronoUnit.MONTHS);
        int requestIteration = 1;
        for (int i = 0; i <= monthDif; i++) {
            for (Coordinate coordinate : coordinates) {
                YearMonth currentDate = fromDate.plusMonths(i);
                try {
                    String json = requestJson(coordinate, currentDate);
                    jsons.add(json);
                } catch (ClientException e) {
                    LOGGER.error("Crimes on " + currentDate + " at " + coordinate + " were not downloaded", e);
                }
                considerCallLimit(requestIteration++);
            }
        }
        return jsons;
    }

    private void loadCrimeCategoriesIfAbsent() throws ServiceException {
        try {
            if (categoryDao.count() < CRIME_CATEGORIES_NUMBER) {
                LOGGER.debug("Downloading crime categories");
                CrimeCategoryService categoryService = new CrimeCategoryService();
                categoryService.downloadToDatabase();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private String requestJson(Coordinate coordinate, YearMonth date) throws ClientException {
        CrimeRequestBuilder requestBuilder = CrimeRequestBuilder.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put(RequestKey.DATE, date.toString());
        params.put(RequestKey.LONGITUDE, coordinate.getLongitude());
        params.put(RequestKey.LATITUDE, coordinate.getLatitude());
        String url = requestBuilder.build(params);
        HttpClient client = new HttpClient(url, HttpClient.RequestMethod.GET);
        Response response = client.makeRequest();
        return client.parseResponse(response);
    }
}