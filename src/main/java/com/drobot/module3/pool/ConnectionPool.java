package com.drobot.module3.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String PROPERTIES_FILE = "datasource.properties";
    private static HikariConfig config;
    private static HikariDataSource datasource;
    private static FluentJdbc fluentJdbc;

    static {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            LOGGER.error("Properties are not found: " + PROPERTIES_FILE, e);
        }
        config = new HikariConfig(properties);
        datasource = new HikariDataSource(config);
        fluentJdbc = new FluentJdbcBuilder()
                .connectionProvider(datasource)
                .build();
    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    public static DataSource getDataSource() {
        return datasource;
    }

    public static FluentJdbc getFluentJdbc() {
        return fluentJdbc;
    }
}
