package com.core.system.service;

public class CrudService {

    public <T> T preCreate(Class<T> clazz){
        T val = (T) clazz;
        return val;
    }

    public <T> T postCreate(Class<T> clazz){
        T val = (T) clazz;
        return val;
    }

    public <T> T preUpdate(Class<T> clazz){
        T val = (T) clazz;
        return val;
    }

    public <T> T postUpdate(Class<T> clazz){
        T val = (T) clazz;
        return val;
    }

    public <T> T getMeta(Class<T> clazz){
        T val = (T) clazz;
        return val;
    }
}
