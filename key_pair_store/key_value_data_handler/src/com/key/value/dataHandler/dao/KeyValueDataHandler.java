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
            randomAccessFile.writeBytes("1  ");
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
                randomAccessFile.writeBytes("1  ");
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
            String strDataCount=String.valueOf(dataCount+1);
            while(strDataCount.length()<3) strDataCount+=" ";
            currentRandomAccessFile.writeBytes(strDataCount);
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
    if(key==null || key.length()==0) throw new KeyValueException("Key data is null/size=0");
    if(value==null || value.length()==0) throw new KeyValueException("value data is null/size=0");
    if(fileName==null || fileName.length()==0) throw new KeyValueException("File name to update value is size=0");
    String currentDirectory = System.getProperty("user.dir");
    RandomAccessFile currentRandomAccessFile=null;
    RandomAccessFile tempRandomAccessFile=null;

    try
    {
        boolean boolKeyFound=false;
        long filePointerPosition=0;
        String fileKey;

        File currentFile=new File(currentDirectory+"\\dataFiles\\"+fileName);
        if(currentFile.exists()==false)  throw new KeyValueException("error: file does not exists to update");
        currentRandomAccessFile=new RandomAccessFile(currentFile,"rw");

        if(currentRandomAccessFile.length()==0)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("file is empty");
        }

        int dataCount=Integer.parseInt(currentRandomAccessFile.readLine().trim());
        if(dataCount==0) 
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : key does not exists to update");
        }

                           //Now searching of key begins......
        
        while(currentRandomAccessFile.length()>currentRandomAccessFile.getFilePointer())
        {
            fileKey=currentRandomAccessFile.readLine().trim();
            if(fileKey.equalsIgnoreCase(key))
            {
                boolKeyFound=true;
                filePointerPosition=currentRandomAccessFile.getFilePointer();
                break;
            }
            
        }

        if(boolKeyFound==false)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("erroe : Key not found for updation");
        }

        
        File tempFile=new File("tempUFile.temp");       //Copying data from current file to temp file....
        if(tempFile.exists()) tempFile.delete();
        tempRandomAccessFile=new RandomAccessFile(tempFile,"rw");

        currentRandomAccessFile.readLine();
        tempRandomAccessFile.writeBytes(value);
        tempRandomAccessFile.writeBytes("\n");
        while(currentRandomAccessFile.length()>currentRandomAccessFile.getFilePointer())
        {
            tempRandomAccessFile.writeBytes(currentRandomAccessFile.readLine());
            tempRandomAccessFile.writeBytes("\n");
        }

                                                             // now updation in current file......
        currentRandomAccessFile.setLength(filePointerPosition);
        currentRandomAccessFile.seek(filePointerPosition);
        
        tempRandomAccessFile.seek(0);
        while(tempRandomAccessFile.length()>tempRandomAccessFile.getFilePointer())
        {
            currentRandomAccessFile.writeBytes(tempRandomAccessFile.readLine());
            currentRandomAccessFile.writeBytes("\n");
        }

        currentRandomAccessFile.close();
        tempRandomAccessFile.close();
        return;
      
    }catch(IOException ioException)
    {
        if(currentRandomAccessFile!=null || tempRandomAccessFile!=null)
        {
            try
            {
               if(currentRandomAccessFile!=null) currentRandomAccessFile.close();
               if(tempRandomAccessFile!=null) tempRandomAccessFile.close();
            }catch(IOException ioe)
            {}

        }

       throw new KeyValueException("error : not expected (updating data in file) --> "+ioException.getMessage());
    }
    
}
public void delete(String key,String fileName) throws KeyValueException
{
 

    if(key==null || key.length()==0) throw new KeyValueException("Key data is null/size=0");
    if(fileName==null || fileName.length()==0) throw new KeyValueException("File name to delete value is size=0");
    String currentDirectory = System.getProperty("user.dir");
    RandomAccessFile currentRandomAccessFile=null;
    RandomAccessFile tempRandomAccessFile=null;

    try
    {
        boolean boolKeyFound=false;
        long filePointerPosition=0;        
        String fileKey;

        File currentFile=new File(currentDirectory+"\\dataFiles\\"+fileName);
        if(currentFile.exists()==false)  throw new KeyValueException("error : not expected (file not exists.) can not delete");
       
        currentRandomAccessFile=new RandomAccessFile(currentFile,"rw");

        if(currentRandomAccessFile.length()==0)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : not expected (file not exists.)");
        }

        int dataCount=Integer.parseInt(currentRandomAccessFile.readLine().trim());
        if(dataCount==0) 
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : not expected (file not exists.) can not delete");
        }
                           //Now searching of key begins......
        
        while(currentRandomAccessFile.length()>currentRandomAccessFile.getFilePointer())
        {
            filePointerPosition=currentRandomAccessFile.getFilePointer();
            fileKey=currentRandomAccessFile.readLine().trim();

            if(fileKey.equalsIgnoreCase(key))
            {
                boolKeyFound=true;
                break;
            }
            
        }

        if(boolKeyFound==false)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : key to delete does not exists");
        }

        
        File tempFile=new File("tempUFile.temp");       //Copying data from current file to temp file....
        if(tempFile.exists()) tempFile.delete();
        tempRandomAccessFile=new RandomAccessFile(tempFile,"rw");

        currentRandomAccessFile.readLine();
        while(currentRandomAccessFile.length()>currentRandomAccessFile.getFilePointer())
        {
            tempRandomAccessFile.writeBytes(currentRandomAccessFile.readLine());
            tempRandomAccessFile.writeBytes("\n");
        }

                                                             // now updation in current file......
        currentRandomAccessFile.setLength(filePointerPosition);
        currentRandomAccessFile.seek(filePointerPosition);
        //currentRandomAccessFile.writeBytes("\n");
        tempRandomAccessFile.seek(0);
        while(tempRandomAccessFile.length()>tempRandomAccessFile.getFilePointer())
        {
            currentRandomAccessFile.writeBytes(tempRandomAccessFile.readLine());
            currentRandomAccessFile.writeBytes("\n");
        }

    
        String strDataCount=String.valueOf(dataCount-1);
        while(strDataCount.length()<3) strDataCount+=" ";
        currentRandomAccessFile.seek(0);
        currentRandomAccessFile.writeBytes(strDataCount);
        currentRandomAccessFile.close();
        tempRandomAccessFile.close();
        return;
      
    }catch(IOException ioException)
    {

        if(currentRandomAccessFile!=null || tempRandomAccessFile!=null)
        {
            try
            {
                if(currentRandomAccessFile!=null) currentRandomAccessFile.close();
                if(tempRandomAccessFile!=null) tempRandomAccessFile.close();
            }catch(IOException ioe)
            {}
        }
       throw new KeyValueException("error : not expected (delete data in file) --> "+ioException.getMessage());
    }
}

public String get(String key,String fileName) throws KeyValueException
{
    if(key==null || key.length()==0) throw new KeyValueException("error : key is null/size=0");
    if(fileName==null || fileName.length()==0) throw new KeyValueException("File name to delete value is size=0");
    String currentDirectory = System.getProperty("user.dir");
    RandomAccessFile currentRandomAccessFile=null;
    
    try
    {
        boolean boolKeyFound=false;    
        String fileKey;
        String fileData;
        File currentFile=new File(currentDirectory+"\\dataFiles\\"+fileName);
        if(currentFile.exists()==false)  throw new KeyValueException("error : not expected (file not exists.) can not delete");
        currentRandomAccessFile=new RandomAccessFile(currentFile,"rw");

        if(currentRandomAccessFile.length()==0)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : Unable to get (file is empty)");
        }

        int dataCount=Integer.parseInt(currentRandomAccessFile.readLine().trim());
        if(dataCount==0) 
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : Unable to get (number of entries zero)");
        }
                           //Now searching of key begins......
        
        while(currentRandomAccessFile.length()>currentRandomAccessFile.getFilePointer())
        {
            fileKey=currentRandomAccessFile.readLine().trim();
            if(fileKey.equalsIgnoreCase(key))
            {
                boolKeyFound=true;
                break;
            }
        }

        if(boolKeyFound==false)
        {
            currentRandomAccessFile.close();
            throw new KeyValueException("error : key does not exists");
        }
        fileData=currentRandomAccessFile.readLine();
        currentRandomAccessFile.close();
        return fileData;
      
    }catch(IOException ioException)
    {
          if(currentRandomAccessFile!=null)
          {
            try
            {
                currentRandomAccessFile.close();
            }catch(IOException ioe)
            {}
          }
       throw new KeyValueException("error : not expected (delete data in file) --> "+ioException.getMessage());
    }

}
}