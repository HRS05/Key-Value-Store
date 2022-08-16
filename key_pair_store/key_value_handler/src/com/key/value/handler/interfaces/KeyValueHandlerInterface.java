package com.key.value.handler.interfaces;
import com.key.value.handler.exceptions.*;
import java.util.*;
public interface KeyValueHandlerInterface
{
    public void set(String key,String value) throws KeyValueException;
    public void delete(String key) throws KeyValueException;
    public void get(String key) throws KeyValueException;
    //public void add(String key,String value) throws KeyValueException;
}