package com.key.value.dataHandler.interfaces;
import com.key.value.dataHandler.exceptions.*;
import java.util.*;
public interface KeyValueDataHandlerInterface
{
    public String add(String key,String value,String fileName) throws KeyValueException;
    public void edit(String key,String value,String fileName) throws KeyValueException;
    public void delete(String key,String value,String fileName) throws KeyValueException;
    public void get(String key) throws KeyValueException;
    //public void add(String key,String value) throws KeyValueException;
}