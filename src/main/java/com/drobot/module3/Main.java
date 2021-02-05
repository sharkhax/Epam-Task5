package com.drobot.module3;

import com.drobot.module3.exception.ServiceException;
import com.drobot.module3.parser.ApiKey;
import com.drobot.module3.parser.PropertiesParser;
import com.drobot.module3.parser.PropertyKey;
import com.drobot.module3.service.Downloader;
import com.drobot.module3.service.impl.crime.CrimesDownloader;
import com.drobot.module3.service.impl.crime.CrimesToFileDownloader;
import com.drobot.module3.service.impl.StopsForceDownloader;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            PropertiesParser parser = new PropertiesParser();
            Map<String, String> params = parser.parse(args);
            String api = params.get(PropertyKey.API);
            if (api != null) {
                Downloader downloader = defineDownloader(api, params);
                if (downloader != null) {
                    downloader.download(params);
                } else {
                    LOGGER.error("API was not specified");
                }
            } else {
                LOGGER.error("API was not chosen");
            }
        } catch (ParseException e) {
            LOGGER.error("Error while parsing properties from command line", e);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static Downloader defineDownloader(String api, Map<String, String> params) throws ServiceException {
        Downloader downloader = null;
        switch (api) {
            case ApiKey.CRIMES_API: {
                downloader = params.get(PropertyKey.TARGET_FILE) != null
                        ? new CrimesToFileDownloader() : new CrimesDownloader();
                break;
            }
            case ApiKey.STOPS_FORCE_API: {
                downloader = new StopsForceDownloader();
                break;
            }
        }
        return downloader;
    }
}
