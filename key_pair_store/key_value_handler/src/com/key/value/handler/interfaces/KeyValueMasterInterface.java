package com.key.value.handler.interfaces;
import com.key.value.handler.exceptions.*;
import java.util.*;
public interface KeyValueMasterInterface
{
    public void createDataBase(String dataBase) throws KeyValueHandlerException;
    public void createTable(String dataBase,String table) throws KeyValueHandlerException;
    public void deleteDataBase(String dataBase) throws KeyValueHandlerException;
    public void deleteTable(String dataBase,String table) throws KeyValueHandlerException;
    public KeyValueHandlerInterface getKeyValueHandler(String dataBase,String table) throws KeyValueHandlerException;
}