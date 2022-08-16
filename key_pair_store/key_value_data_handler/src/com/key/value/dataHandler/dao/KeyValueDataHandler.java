package com.key.value.dataHandler.dao;
import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
import java.util.*;
import java.io.*;
public class KeyValueDataHandler implements KeyValueDataHandlerInterface
{
public String add(String key,String value,String fileName) throws KeyValueException
{
    if(key==null || key.length()==0) throw new KeyValueException("Key data is null/size=0");
    if(value==null || value.length()==0) throw new KeyValueException("value data is null/size=0");
    if(fileName!=null && fileName.length()==0) throw new KeyValueException("File name to add value is size=0");
    String newFileName=null;
    String currentDirectory = System.getProperty("user.dir");
        
    if(fileName==null)
    {
        newFileName= UUID.randomUUID().toString()+".data";
	    currentDirectory+="\\dataFiles\\"+newFileName;
        System.out.println(currentDirectory);
        try
        {
            File file=new File(currentDirectory);
            RandomAccessFile randomAccessFile;
            randomAccessFile=new RandomAccessFile(file,"rw");
            randomAccessFile.writeBytes("1");
            randomAccessFile.writeBytes("\n");
            randomAccessFile.writeBytes(key);
            randomAccessFile.writeBytes("\n");
            randomAccessFile.writeBytes(value);
            randomAccessFile.writeBytes("\n");
            randomAccessFile.close(); 
            return newFileName;
        }catch(IOException ioException)
        {
            throw new KeyValueException("error : not expected (adding data in file) --> "+ioException.getMessage());
        }
    }
    else
    {
        try
        {
        File currentFile=new File(currentDirectory+"\\dataFiles\\"+fileName);
        if(currentFile.exists()==false)  throw new KeyValueException("error : not expected (file to add not exists.)");
        RandomAccessFile currentRandomAccessFile;
        currentRandomAccessFile=new RandomAccessFile(currentFile,"rw");
        if(currentRandomAccessFile.length()==0)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : not expected (file to add not exists.)");
        }
        int dataCount=Integer.parseInt(currentRandomAccessFile.readLine().trim());

        if(dataCount==100)
        {
            currentRandomAccessFile.close();
            newFileName= UUID.randomUUID().toString()+".data";
	        currentDirectory+="\\dataFiles\\"+newFileName;
            System.out.println(currentDirectory);
            try
            {
                File file=new File(currentDirectory);
                RandomAccessFile randomAccessFile;
                randomAccessFile=new RandomAccessFile(file,"rw");
                randomAccessFile.writeBytes("1");
                randomAccessFile.writeBytes("\n");
                randomAccessFile.writeBytes(key);
                randomAccessFile.writeBytes("\n");
                randomAccessFile.writeBytes(value);
                randomAccessFile.writeBytes("\n");
                randomAccessFile.close(); 
                return newFileName;
            }catch(IOException ioException)
            {
                throw new KeyValueException("error : not expected (adding data in file) --> "+ioException.getMessage());
            }
        }
        else
        {
            while(currentRandomAccessFile.getFilePointer()<currentRandomAccessFile.length())
            {
                currentRandomAccessFile.readLine();
            }
            currentRandomAccessFile.writeBytes(key);
            currentRandomAccessFile.writeBytes("\n");
            currentRandomAccessFile.writeBytes(value);
            currentRandomAccessFile.writeBytes("\n");
	        currentRandomAccessFile.seek(0);
            currentRandomAccessFile.writeBytes(String.valueOf(dataCount+1));
            currentRandomAccessFile.writeBytes("\n");
            currentRandomAccessFile.close();
            return fileName;
        }
        }
        catch(IOException ioException)
        {
            throw new KeyValueException("error : not expected (adding data in file) --> "+ioException.getMessage());
        }
    }
}
public void edit(String key,String value,String fileName) throws KeyValueException
{
    throw new KeyValueException("Method not written");
}
public void delete(String key,String value,String fileName) throws KeyValueException
{
    throw new KeyValueException("Method not written");
}
public void get(String key) throws KeyValueException
{
    throw new KeyValueException("Method not written");
}

}