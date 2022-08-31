package com.key.value.handler.dao;

import java.util.concurrent.ConcurrentHashMap;
import com.key.value.handler.interfaces.*;
import com.key.value.handler.exceptions.*;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.dataHandler.dao.*;
import java.util.*;
import java.io.*;
import com.key.value.helper.pair.*;
import java.lang.*;
import java.util.concurrent.*;
import java.sql.*;

public class KeyValueMaster 
{
    private String path;
    private ConcurrentMap<String,ArrayList<String> > masterMap;
    private ConcurrentMap<String,ConcurrentMap<String,KeyValueHandler> > master;
    private static KeyValueMaster keyValueMaster=null;

    private KeyValueMaster(String path)
    {
        this.path=path;
    }

    private static KeyValueMaster getKeyValueMaster(String path)
    {
        if(keyValueMaster != null) return keyValueMaster;
        return new KeyValueMaster();
    } 

    public void createDataBase(String dataBase) throws KeyValueHandlerException
    {

    }

    public void createTable(String dataBase,String table) throws KeyValueHandlerException
    {

    }

    public void deleteDataBase(String dataBase) throws KeyValueHandlerException
    {

    }

    public void deleteTable(String table) throws KeyValueHandlerException
    {

    }


}
