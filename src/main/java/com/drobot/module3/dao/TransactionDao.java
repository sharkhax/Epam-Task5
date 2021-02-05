package com.drobot.module3.dao;

import com.drobot.module3.exception.DaoException;

import java.util.Collection;

public interface TransactionDao<E> {

    void addAll(Collection<E> objects) throws DaoException;
}
