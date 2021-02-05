package com.drobot.module3.service;

import com.drobot.module3.exception.ServiceException;

import java.util.Map;

public interface Downloader {

    void download(Map<String, String> params) throws ServiceException;
}
