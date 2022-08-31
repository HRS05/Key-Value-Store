package com.key.value.dataHandler.interfaces;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.helper.pair.*;
import java.util.*;
import java.util.concurrent.*;
public interface KeyValueDataMasterInterface
{
    public void createDataBase(String dataBase) throws KeyValueException;
    public void deleteDataBase(String dataBase) throws KeyValueException;
    public void createTable(String dataBase,String table) throws KeyValueException;
    public void deleteTable(String dataBase,String table) throws KeyValueException;
    public ConcurrentMap<String,ArrayList<String> > populateMasterMap() throws KeyValueException;
}