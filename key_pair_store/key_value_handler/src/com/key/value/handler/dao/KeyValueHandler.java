package com.key.value.handler.dao;
import com.key.value.handler.interfaces.*;
import com.key.value.handler.exceptions.*;
import java.util.*;
import java.io.*;
//import javafx.util.Pair;
import java.lang.*;
public class KeyValueHandler implements KeyValueHandlerInterface
{
    private String fileName;
    private Map<String,Pair<String,String> > keyValueMap;
public void set(String key,String value) throws KeyValueException
{
    throw new KeyValueException("Method not written");
}
public void delete(String key) throws KeyValueException
{
    throw new KeyValueException("Method not written");
}
public void get(String key) throws KeyValueException
{
    throw new KeyValueException("Method not written");
}

}