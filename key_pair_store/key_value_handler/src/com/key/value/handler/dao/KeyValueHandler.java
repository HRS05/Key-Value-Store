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

class TimeStampBasedComparator implements Comparator<OperationObject>
{
    public int compare(OperationObject obj_1, OperationObject obj_2) 
    {
        if(obj_1==null && obj_2==null) return 0;
        if(obj_1==null) return 1;
        if(obj_2==null) return -1;
        long obj_1_time_stamp_value=obj_1.getTimestampValue();
        long obj_2_time_stamp_value=obj_2.getTimestampValue();
        
        if (obj_1_time_stamp_value > obj_2_time_stamp_value)
            return 1;
        else if (obj_1_time_stamp_value < obj_2_time_stamp_value)
            return -1;
        return 0;
    }
}
 

public class KeyValueHandler extends Thread implements KeyValueHandlerInterface
{
    private String fileName;
    private String directory;
    private ConcurrentMap<String,Pair> keyValueMap=null;
    private static KeyValueHandler keyValueHandler=null;
    //private Queue<OperationObject> queue;
    private PriorityQueue<OperationObject> queue=null;
    private KeyValueHandler(String directory)
    {
        this.fileName=null;
        this.directory=directory;
        //this.queue=new LinkedList<>();
        this.queue=new PriorityQueue<OperationObject>(new TimeStampBasedComparator ());
        start();

        try{
            this.keyValueMap=new KeyValueDataHandler(this.directory).populateMap();
        }catch(Exception exception){
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

    public void run() 
    {
        while(true)
        {
            System.out.println("queue on call");
            while(this.queue.size()>0)
            {
                try
                {
                    Thread.sleep(1);
                }
                catch(Exception e)
                {}

                OperationObject OJ=this.queue.peek(); this.queue.remove();
                String type=OJ.getType();
                String fileName=OJ.getFileName();
                String key=OJ.getKey();
                String value=OJ.getValue();
                if(type=="add"){
                    try
                    {
                        System.out.println("queue KeyValueHandler add operation got called -> key : "+key+" value : "+value+" fileName "+this.fileName);
                        this.fileName=new KeyValueDataHandler(this.directory).add(key,value,this.fileName);
                        String CurrentValue=this.keyValueMap.get(key).getValue();
                        this.keyValueMap.replace(key, new Pair(null,CurrentValue), new Pair(this.fileName,CurrentValue));
                    }catch(Exception exception)
                    {
                        this.queue.add(OJ);
                        System.out.println("Exception from thread :-> "+exception.getMessage());
                    }
                }
                else if(type=="edit"){
                    try
                    {
                        Pair pair;
                        if(fileName==null || fileName.length()==0){
                            pair=this.keyValueMap.get(key);
                            if(pair.getKey()==null || pair.getKey().length()==0) {
                                this.queue.add(OJ);
                                continue;
                            }
                            fileName=pair.getKey();
                            OJ.setFileName(fileName);
                        }
                        System.out.println("queue KeyValueHandler edit operation got called -> key : "+key+" value : "+value+" fileName "+fileName);
                        new KeyValueDataHandler(this.directory).edit(key,value,fileName);
                    }catch(Exception exception)
                    {
                        this.queue.add(OJ);
                        System.out.println("Exception from thread :-> "+exception.getMessage());
                    }
                }
                else if(type=="remove"){
                    try
                    {
                        Pair pair;
                        if(fileName==null || fileName.length()==0){
                            pair=this.keyValueMap.get(key);
                            if(pair.getKey()==null || pair.getKey().length()==0) {
                                this.queue.add(OJ);
                                continue;
                            }
                            fileName=pair.getKey();
                            OJ.setFileName(fileName);
                        }
                        System.out.println("queue KeyValueHandler remove operation got called -> key : "+key+" value : "+value+" fileName "+fileName);
                        new KeyValueDataHandler(this.directory).delete(key,fileName);
                    }catch(Exception exception)
                    {
                        this.queue.add(OJ);
                        System.out.println("Exception from thread :-> "+exception.getMessage());
                    }
                }
                else{
                System.out.println("Invalid data in queue");
                }
            }
        }
    }





    public void set(String key,String value) throws KeyValueHandlerException
    {
        System.out.println("KeyValueHandler set operation got called -> key : "+key+" value : "+value);
        if(key==null || key.trim().length()==0) throw new KeyValueHandlerException("Key data is null/size=0");
        if(value==null || value.trim().length()==0) throw new KeyValueHandlerException("value data is null/size=0");
        key=key.trim();
        value=value.trim();
        if(this.keyValueMap.containsKey(key))
        {    
            Pair pair=this.keyValueMap.get(key);
            String oldFileName=pair.getKey();
            this.keyValueMap.replace(key, pair, new Pair(oldFileName,value));
            OperationObject OJ=new OperationObject();
            OJ.setFileName(oldFileName);
            OJ.setType("edit");
            OJ.setKey(key);
            OJ.setValue(value);
            OJ.setTimestampValue(new Timestamp(System.currentTimeMillis()).getTime());
            this.queue.add(OJ);
            return;
        }
        else
        {
            this.keyValueMap.put(key,new Pair(null,value));
            OperationObject OJ=new OperationObject();
            OJ.setFileName(this.fileName);
            OJ.setType("add");
            OJ.setKey(key);
            OJ.setValue(value);
            OJ.setTimestampValue(new Timestamp(System.currentTimeMillis()).getTime());
            this.queue.add(OJ);
            return;
        }

    }
    public void remove(String key) throws KeyValueHandlerException
    {
        System.out.println("KeyValueHandler remove operation got called -> key : "+key);
        if(key==null || key.trim().length()==0) throw new KeyValueHandlerException("Key data is null/size=0");
        key=key.trim();
        if(this.keyValueMap.containsKey(key))
        {
            Pair pair=this.keyValueMap.get(key);
            String oldFileName=pair.getKey();
            String value=pair.getValue();
            this.keyValueMap.remove(key, pair);
            OperationObject OJ=new OperationObject();
            OJ.setFileName(oldFileName);
            OJ.setType("remove");
            OJ.setKey(key);
            OJ.setValue(value);
            OJ.setTimestampValue(new Timestamp(System.currentTimeMillis()).getTime());
            this.queue.add(OJ);
            return;
        }
        else
        {
            throw new KeyValueHandlerException("Key is not in store");
        }
    }
    public String get(String key) throws KeyValueHandlerException
    {
        System.out.println("KeyValueHandler get operation got called -> key : "+key);
        if(this.keyValueMap.containsKey(key))
        {
        return this.keyValueMap.get(key).getValue();
        }
        return null;
    }

}