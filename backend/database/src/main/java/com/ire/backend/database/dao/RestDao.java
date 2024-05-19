package com.ire.backend.database.dao;

public interface RestDao<T> {

    String delete(String s);

    T get(String s);

    String update(T t);

    String insert(T t);
}
