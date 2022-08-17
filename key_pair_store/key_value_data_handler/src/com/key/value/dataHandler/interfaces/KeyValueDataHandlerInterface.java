package com.key.value.dataHandler.interfaces;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.helper.pair.*;
import java.util.*;
import java.util.concurrent.*;
public interface KeyValueDataHandlerInterface
{
    public String add(String key,String value,String fileName) throws KeyValueException;
    public void edit(String key,String value,String fileName) throws KeyValueException;
    public void delete(String key,String fileName) throws KeyValueException;
    public String get(String key,String fileName) throws KeyValueException;
    public ConcurrentMap<String,Pair> populateMap() throws KeyValueException;
}