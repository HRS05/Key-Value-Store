import com.key.value.handler.interfaces.*;
import com.key.value.handler.exceptions.*;
import com.key.value.handler.dao.*;
import java.util.*;
import java.io.*;
class check1
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

    kvm.createDataBase("database1--1");
    kvm.createDataBase("database2--2");
    
 }catch(KeyValueHandlerException daoException)
{
System.out.println("**********");
System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends