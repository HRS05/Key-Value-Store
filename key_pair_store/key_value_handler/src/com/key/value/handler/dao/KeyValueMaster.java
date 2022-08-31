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
import java.sql.*;

public class KeyValueMaster implements KeyValueMasterInterface
{
    private String path;
    private ConcurrentMap<String,ArrayList<String> > masterMap=null;
    private ConcurrentMap<String,ConcurrentMap<String,KeyValueHandler> > master=null;
    private static KeyValueMaster keyValueMaster=null;


    private KeyValueMaster(String path)  throws KeyValueHandlerException
    {
        //check for path will add
        this.path=path;
        try
        {
            this.masterMap=new KeyValueDataMaster(this.path).populateMasterMap();
        }catch(KeyValueException exception)
        {
            System.out.println("Error : issue in populating master map --> "+exception.getMessage());
            throw new KeyValueHandlerException("Error : issue in populating master map --> "+exception.getMessage());
        }
        this.populateMaster();
    }

    public static KeyValueMaster getKeyValueMaster(String path)  throws KeyValueHandlerException
    {
        if(keyValueMaster != null) return keyValueMaster;
        try
        {
            keyValueMaster=new KeyValueMaster(path);
        }catch(KeyValueHandlerException exception)
        {
            throw new KeyValueHandlerException("Error in populating the master ----> "+exception.getMessage());
        }
        
        return keyValueMaster;
    } 

    private void populateMaster() throws KeyValueHandlerException
    {
        this.master=new ConcurrentHashMap<String,ConcurrentMap<String,KeyValueHandler> >();
        if(this.masterMap==null) return;
        
        Iterator<ConcurrentHashMap.Entry<String, ArrayList<String>> > masterMapIterator = this.masterMap.entrySet().iterator();
        while (masterMapIterator.hasNext()) 
        {
            ConcurrentHashMap.Entry<String, ArrayList<String> > entry = masterMapIterator.next();
            String dataBaseName=entry.getKey();
            
            ConcurrentMap<String,KeyValueHandler> obj=new ConcurrentHashMap<String,KeyValueHandler>();
            for(String tableName : entry.getValue())
            {
                try{
                KeyValueHandler keyValueHandler=new KeyValueHandler(this.path+dataBaseName+"//"+tableName);
                obj.put(dataBaseName+tableName,keyValueHandler);
                }catch(KeyValueHandlerException exception)
                {
                    throw new KeyValueHandlerException("Error in populating master for "+this.path+dataBaseName+"//"+tableName+" --> "+exception.getMessage());
                }
            }
            this.master.put(dataBaseName,obj);
        }

    }

    public void createDataBase(String dataBase) throws KeyValueHandlerException
    {
        if(dataBase==null || dataBase.trim().length()==0) throw new KeyValueHandlerException("error : from createDataBase the data base name is empty/null");
        dataBase=dataBase.trim();
        try
        {
            new KeyValueDataMaster(path).createDataBase(dataBase);
            this.masterMap.put(dataBase,new ArrayList<String>());
            this.master.put(dataBase,new ConcurrentHashMap<String,KeyValueHandler>());
        }catch(KeyValueException exception)
        {
            throw new KeyValueHandlerException("error : in creating database --> "+exception.getMessage());
        }
    }

    public void createTable(String dataBase,String table) throws KeyValueHandlerException
    {
        if(dataBase==null || dataBase.trim().length()==0) throw new KeyValueHandlerException("error : from createTabel the data base name is empty/null");
        if(table==null || table.trim().length()==0) throw new KeyValueHandlerException("error : from createTabel the tabel name is empty/null");
        dataBase=dataBase.trim();
        table=table.trim();
        if(this.master.containsKey(dataBase)==false) throw new KeyValueHandlerException("error : from createTabel the data base "+dataBase+" not exists to create tabel "+table+".");;
        ConcurrentMap<String,KeyValueHandler> obj=this.master.get(dataBase);
        if(obj.containsKey(table)==true) throw new KeyValueHandlerException("error : from createTabel the tabel "+table+" already exists.");
        try
        {
            new KeyValueDataMaster(path).createTable(dataBase, table);
            System.out.println("dataBase "+dataBase+"   "+"table  "+table);
            this.masterMap.get(dataBase).add(table);
            KeyValueHandler keyValueHandler=new KeyValueHandler(this.path+dataBase+File.separator+table+File.separator);
            this.master.get(dataBase).put(table,keyValueHandler);
        }catch(KeyValueException exception)
        {
            throw new KeyValueHandlerException("error : in creating database --> "+exception.getMessage());
        }
    }

    public void deleteDataBase(String dataBase) throws KeyValueHandlerException
    {
        if(dataBase==null || dataBase.trim().length()==0) throw new KeyValueHandlerException("error : from deleteDataBase the data base name is empty/null");
        if(this.master.containsKey(dataBase)==false) throw new KeyValueHandlerException("error : from deleteDataBase the data base "+dataBase+" not exists.");;
        dataBase=dataBase.trim();
        try
        {
            this.master.remove(dataBase);
            this.masterMap.remove(dataBase);
            new KeyValueDataMaster(path).createDataBase(dataBase);
        }catch(KeyValueException exception)
        {
            throw new KeyValueHandlerException("error : in creating database --> "+exception.getMessage());
        }
    }

    public void deleteTable(String dataBase,String table) throws KeyValueHandlerException
    {
        if(dataBase==null || dataBase.trim().length()==0) throw new KeyValueHandlerException("error : from deleteTable the data base name is empty/null");
        if(table==null || table.trim().length()==0) throw new KeyValueHandlerException("error : from deleteTable the tabel name is empty/null");
        dataBase=dataBase.trim();
        table=table.trim();
        if(this.master.containsKey(dataBase)==false) throw new KeyValueHandlerException("error : from deleteTable the data base "+dataBase+" not exists to delete tabel "+table+".");;
        ConcurrentMap<String,KeyValueHandler> obj=this.master.get(dataBase);
        if(obj.containsKey(table)==false) throw new KeyValueHandlerException("error : from deleteTable the tabel "+table+" not exists.");
        try
        {
            this.masterMap.get(dataBase).remove(table);
            this.master.get(dataBase).remove(table);
            new KeyValueDataMaster(path).deleteTable(dataBase, table);
            
        }catch(KeyValueException exception)
        {
            throw new KeyValueHandlerException("error : in creating database --> "+exception.getMessage());
        }
    }

    public KeyValueHandlerInterface getKeyValueHandler(String dataBase,String table) throws KeyValueHandlerException
    {
        if(this.masterMap.containsKey(dataBase)==false) throw new KeyValueHandlerException("master Map does not contain dataBase "+dataBase);
        if(this.master.containsKey(dataBase)==false) throw new KeyValueHandlerException("master Map does not contain dataBase "+dataBase);
        if(this.master.get(dataBase).containsKey(table)==false) throw new KeyValueHandlerException("master Map does not contain tabel "+table+" in database "+dataBase);
        return this.master.get(dataBase).get(table);
    }

}
