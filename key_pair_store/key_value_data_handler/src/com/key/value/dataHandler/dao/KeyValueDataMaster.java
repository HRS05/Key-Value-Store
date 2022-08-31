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
        if(dataBase==null) throw new KeyValueException("error : database name empty");
        if(dataBase.length()==0) throw new KeyValueException("error : database name empty");
        File folderDataBase=new File(this.path+File.separator+dataBase);
        if(folderDataBase.exists()==true) throw new KeyValueException("error : database ("+dataBase+") already exist");
        folderDataBase.mkdirs();
    }

    public void createTable(String dataBase,String table) throws KeyValueException
    {
        if(dataBase==null && table==null) throw new KeyValueException("error: Names of dataBase and table are empty");
        else if(dataBase==null || dataBase.length()==0) throw new KeyValueException("error: dataBase name empty");
        else if(table==null || table.length()==0) throw new KeyValueException("error : table name empty");
        
        File folderDataBase=new File(this.path+File.separator+dataBase);

        if(folderDataBase.exists()==false) throw new KeyValueException("error: dataBase ("+dataBase+") not exits can not make table ("+table+")"); 
        
        File folderTable=new File(this.path+File.separator+dataBase+File.separator+table);
        if(folderTable.exists()==true) throw new KeyValueException("error: table ("+table+") already exists");
        folderTable.mkdirs();
    }

    public void deleteDataBase(String dataBase) throws KeyValueException
    {
        if(dataBase==null || dataBase.length()==0) throw new KeyValueException("error : dataBase name empty");
        File folderDataBase=new File(this.path+File.separator+dataBase);
        if(folderDataBase.exists()==false) throw new KeyValueException("error: dataBase("+dataBase+") does not exists");
        KeyValueDataMaster.deleteDirectory(folderDataBase);
        folderDataBase.delete();
    }

    public void deleteTable(String dataBase,String table) throws KeyValueException
    {

        if(dataBase==null && table==null) throw new KeyValueException("error: Names of dataBase and table are empty");
        else if(dataBase==null || dataBase.length()==0) throw new KeyValueException("error: dataBase name empty");
        else if(table==null || table.length()==0) throw new KeyValueException("error : table name empty");

        File folderDataBase=new File(this.path+File.separator+dataBase);

        if(folderDataBase.exists()==false) throw new KeyValueException("error: dataBase ("+dataBase+") not exits can not delete table ("+table+")"); 
        
        File folderTable=new File(this.path+File.separator+dataBase+File.separator+table);
        if(folderTable.exists()==false) throw new KeyValueException("error: table ("+table+")does not exists, can not delete table");
        KeyValueDataMaster.deleteDirectory(folderTable);
        folderTable.delete();

    }

    private static void deleteDirectory(File directory) // used to delete contents of given directory completely
    {
        for(File subFile: directory.listFiles())
        {
            if(subFile.isDirectory()) deleteDirectory(subFile);
            subFile.delete();
        }
    }

}
