package com.key.value.dataHandler.dao;
import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
import java.util.*;
import java.io.*;
import com.key.value.helper.pair.*;
import java.util.concurrent.*;
public class KeyValueDataMaster implements KeyValueDataMasterInterface
{
    private String path;
    public KeyValueDataMaster(String path)
    {
        this.path=path;
    }    

    public ConcurrentMap<String,ArrayList<String> > populateMasterMap() throws KeyValueException
    {
        ConcurrentMap<String,ArrayList<String> > masterMap=new ConcurrentHashMap<>();

        return masterMap;
    }

    public void createDataBase(String dataBase) throws KeyValueException
    {

    }

    public void createTable(String dataBase,String table) throws KeyValueException
    {

    }

    public void deleteDataBase(String dataBase) throws KeyValueException
    {

    }

    public void deleteTable(String table) throws KeyValueException
    {

    }

}
