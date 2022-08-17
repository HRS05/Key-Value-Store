package com.key.value.handler.dao;
import com.key.value.handler.interfaces.*;
import com.key.value.handler.exceptions.*;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.dataHandler.dao.*;
import java.util.*;
import java.io.*;
import com.key.value.helper.pair.*;
import java.lang.*;
import java.util.concurrent.*;

public class KeyValueHandler implements KeyValueHandlerInterface
{
    private String fileName;
    private String directory;
    private ConcurrentMap<String,Pair> keyValueMap=null;
    private static KeyValueHandler keyValueHandler=null;

    private KeyValueHandler(String directory)
    {
        this.fileName=null;
        this.directory=directory;
        try{
            this.keyValueMap=new KeyValueDataHandler(this.directory).populateMap();
        }catch(KeyValueException exception){
            this.keyValueMap=new ConcurrentHashMap<String,Pair>();
        }
        
    }

    public static KeyValueHandler getKeyValueHandler(String directory) throws KeyValueHandlerException
    {
        if(directory==null || directory.trim().length()==0) throw new KeyValueHandlerException("directory is null/size=0");
        if(keyValueHandler==null)
        {
            keyValueHandler=new KeyValueHandler(directory);
            return keyValueHandler;
        }
        return keyValueHandler;
    }

    public void set(String key,String value) throws KeyValueHandlerException
    {
        if(key==null || key.trim().length()==0) throw new KeyValueHandlerException("Key data is null/size=0");
        if(value==null || value.trim().length()==0) throw new KeyValueHandlerException("value data is null/size=0");
        key=key.trim();
        value=value.trim();
        if(this.keyValueMap.isEmpty()){
            try{
                this.fileName=new KeyValueDataHandler(this.directory).add(key,value,null);
                this.keyValueMap.put(key,new Pair(this.fileName,value));
                return;
            }
            catch(KeyValueException exception)
            {
                throw new KeyValueHandlerException("Error : unable to add data in null file system -->"+exception.getMessage());
            }
        }
        if(this.keyValueMap.containsKey(key)){
            try{
                Pair pair=this.keyValueMap.get(key);
                String oldFileName=pair.getKey();
                new KeyValueDataHandler(this.directory).edit(key,value,oldFileName);
                this.keyValueMap.replace(key, pair, new Pair(oldFileName,value));
                return;
            }
            catch(KeyValueException exception)
            {
                throw new KeyValueHandlerException("Error : unable to edit data -->"+exception.getMessage());
            }
        }
        else
        {
            try{
                this.fileName=new KeyValueDataHandler(this.directory).add(key,value,this.fileName);
                this.keyValueMap.put(key,new Pair(this.fileName,value));
                return;
            }
            catch(KeyValueException exception)
            {
                throw new KeyValueHandlerException("Error : unable to add data file system -->"+exception.getMessage());
            }
        }

    }
    public void remove(String key) throws KeyValueHandlerException
    {
        if(key==null || key.trim().length()==0) throw new KeyValueHandlerException("Key data is null/size=0");
        key=key.trim();
        if(this.keyValueMap.containsKey(key)){
            try{
                Pair pair=this.keyValueMap.get(key);
                String oldFileName=pair.getKey();
                new KeyValueDataHandler(this.directory).delete(key,oldFileName);
                this.keyValueMap.remove(key, pair);
                return;
            }
            catch(KeyValueException exception)
            {
                throw new KeyValueHandlerException("Error : unable to remove data from file -->"+exception.getMessage());
            }
        }
        return;
    }
    public String get(String key) throws KeyValueHandlerException
    {
        if(this.keyValueMap.containsKey(key);)
        {
        return this.keyValueMap.get(key).getValue();
        }
        return null;
    }

}