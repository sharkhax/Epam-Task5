package com.drobot.module3.service;

import com.drobot.module3.client.HttpClient;
import com.drobot.module3.client.Response;
import com.drobot.module3.dao.impl.ForceDao;
import com.drobot.module3.entity.Force;
import com.drobot.module3.exception.ClientException;
import com.drobot.module3.exception.DaoException;
import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.parser.json.ForceJsonParser;

import java.util.Collection;
import java.util.Set;

public class ForceService {

    private static final String URL = "https://data.police.uk/api/forces";
    private final ForceDao forceDao = ForceDao.INSTANCE;

    public void downloadToDatabase() throws ServiceException {
        try {
            String json = requestJson();
            Set<Force> forces = parseJson(json);
            removeExistingCategories(forces);
            forceDao.addAll(forces);
        } catch (ClientException | DaoException e) {
            throw new ServiceException(e);
        }
    }

    private String requestJson() throws ClientException {
        HttpClient client = new HttpClient(URL, HttpClient.RequestMethod.GET);
        Response response = client.makeRequest();
        return client.parseResponse(response);
    }

    private Set<Force> parseJson(String json) {
        ForceJsonParser parser = new ForceJsonParser();
        return parser.parseArray(json);
    }

    private void removeExistingCategories(Collection<Force> forces) throws DaoException {
        for (Force force : forces) {
            if (forceDao.exists(force.getId())) {
                forces.iterator().remove();
            }
        }
    }
}
