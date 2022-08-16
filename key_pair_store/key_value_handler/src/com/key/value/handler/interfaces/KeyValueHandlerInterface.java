package com.key.value.handler.interfaces;
import com.key.value.handler.exceptions.*;
import java.util.*;
public interface KeyValueHandlerInterface
{
    public void set(String key,String value) throws KeyValueHandlerException;
    public void remove(String key) throws KeyValueHandlerException;
    public String get(String key) throws KeyValueHandlerException;
    //public void add(String key,String value) throws KeyValueException;
}