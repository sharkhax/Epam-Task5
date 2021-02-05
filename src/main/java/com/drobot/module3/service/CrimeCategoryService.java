package com.drobot.module3.service;

import com.drobot.module3.client.HttpClient;
import com.drobot.module3.client.Response;
import com.drobot.module3.dao.impl.CrimeCategoryDao;
import com.drobot.module3.entity.CrimeCategory;
import com.drobot.module3.exception.ClientException;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.parser.json.CrimeCategoryJsonParser;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CrimeCategoryService {

    private static final String URL = "https://data.police.uk/api/crime-categories";
    private final CrimeCategoryDao categoryDao = CrimeCategoryDao.INSTANCE;

    public void downloadToDatabase() throws ServiceException {
        try {
            String json = requestJson();
            Set<CrimeCategory> categories = parseJson(json);
            removeExistingCategories(categories);
            categoryDao.addAll(categories);
        } catch (ClientException | DaoException e) {
            throw new ServiceException(e);
        }
    }

    private String requestJson() throws ClientException {
        HttpClient client = new HttpClient(URL, HttpClient.RequestMethod.GET);
        Response response = client.makeRequest();
        return client.parseResponse(response);
    }

    private Set<CrimeCategory> parseJson(String json) {
        CrimeCategoryJsonParser parser = new CrimeCategoryJsonParser();
        return parser.parseArray(json);
    }

    private void removeExistingCategories(Collection<CrimeCategory> categories) throws DaoException {
        Iterator<CrimeCategory> iterator = categories.iterator();
        while (iterator.hasNext()) {
            CrimeCategory category = iterator.next();
            if (categoryDao.exists(category.getUrl())) {
                iterator.remove();
            }
        }
    }
}
