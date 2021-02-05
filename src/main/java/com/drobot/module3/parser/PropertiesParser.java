package com.drobot.module3.parser;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesParser.class);
    private static final String DEFAULT_ARG_NAME = "property=value";
    private static final char DEFAULT_VALUE_SEPARATOR = ' ';
    private static final String OPTION_NAME = "P";

    public Map<String, String> parse(String[] args) throws ParseException {
        Map<String, String> result;
        Options options = setUpOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        result = wrapOptions(commandLine, PropertyKey.DATE, PropertyKey.DATE2,
                PropertyKey.COORDS_FILE, PropertyKey.TARGET_FILE, PropertyKey.API, PropertyKey.FORCE);
        return result;
    }

    private Options setUpOptions() {
        Options options = new Options();
        Option option = Option.builder()
                .longOpt(OPTION_NAME)
                .argName(DEFAULT_ARG_NAME)
                .hasArgs()
                .valueSeparator(DEFAULT_VALUE_SEPARATOR)
                .build();
        options.addOption(option);
        return options;
    }

    private Map<String, String> wrapOptions(CommandLine commandLine, String ... keys) {
        Map<String, String> result = new HashMap<>();
        if (commandLine.hasOption(OPTION_NAME)) {
            Properties properties = commandLine.getOptionProperties(OPTION_NAME);
            for (String key : keys) {
                result.put(key, properties.getProperty(key));
            }
            LOGGER.debug("Command line -" + OPTION_NAME + " properties have been parsed");
        } else {
            LOGGER.info("Command line does not contain -" + OPTION_NAME + " property");
        }
        return result;
    }
}
