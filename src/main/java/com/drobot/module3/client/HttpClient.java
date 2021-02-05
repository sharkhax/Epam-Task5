package com.drobot.module3.client;

import com.drobot.module3.exception.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

    public enum RequestMethod {
        GET, POST
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;
    private RequestMethod requestMethod;
    private URL url;

    public HttpClient(String url, RequestMethod requestMethod) throws ClientException {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new ClientException("Error while creating URL object from url: " + url, e);
        }
        this.requestMethod = requestMethod;
    }

    public HttpClient(URL url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public void setUrl(String url) throws ClientException {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new ClientException("Error while creating URL object from url: " + url, e);
        }
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String parseResponse(Response response) throws ClientException {
        if (response.getStatusCode() != HttpURLConnection.HTTP_OK) {
            throw new ClientException("Status code: " + response.getStatusCode() + ", message: "
                    + response.getMessage());
        } else {
            return response.getMessage();
        }
    }

    public Response makeRequest() throws ClientException {
        if (requestMethod == null || url == null) {
            throw new ClientException("Url or request method is null");
        }
        Response response;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            LOGGER.debug("Connection has been opened");
            connection.setRequestMethod(requestMethod.toString());
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            response = readContent(connection);
        } catch (IOException e) {
            throw new ClientException("Error while opening a connection", e);
        } finally {
            close(connection);
        }
        return response;
    }

    private Response readContent(HttpURLConnection connection) throws IOException {
        Response response;
        int status = connection.getResponseCode();
        LOGGER.debug("Response code is " + status);
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = status == HttpURLConnection.HTTP_OK
                    ? connection.getInputStream() : connection.getErrorStream();
            Reader reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            StringBuilder message = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                message.append(line);
                line = bufferedReader.readLine();
            }
            LOGGER.debug("Input stream has been read");
            response = new Response(status, message.toString());
        } finally {
            close(bufferedReader);
        }
        return response;
    }

    private void close(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
            LOGGER.debug("Connection has been closed");
        }
    }

    private void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error while closing reader", e);
        }
    }
}
