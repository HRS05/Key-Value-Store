import com.key.value.handler.interfaces.*;
import com.key.value.handler.exceptions.*;
import com.key.value.handler.dao.*;
import java.util.*;
import java.io.*;
class check2
{
public static void main(String gg[])
{
try
{	
    KeyValueMaster kvm= KeyValueMaster.getKeyValueMaster("C:\\Users\\hussa\\Desktop\\data\\");
    System.out.println("control coms here");

    for(int i=0;i<105;i++)
{
    try{
        Thread.sleep(1);
    }catch(Exception e)
    {

    }



}

 
    
    for(int i=0;i<10;i++)
    {
      kvm.createTable("database1--1","table1_"+i);
   kvm.createTable("database2--2","table1_"+i); 
  }


}catch(KeyValueHandlerException daoException)
{
System.out.println("**********");
System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends