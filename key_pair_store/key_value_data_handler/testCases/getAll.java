import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.dataHandler.dao.*;
import java.util.*;
import java.io.*;
class getAll
{
public static void main(String gg[])
{
try
{
KeyValueDataHandlerInterface kvd;
kvd=new KeyValueDataHandler("F://datafile//");

kvd.populateMap().forEach((code,pair)->{
System.out.println("code: "+code+" File name: "+pair.getKey()+" value: "+pair.getValue());});

//System.out.println(kvd.populateMap());
//System.out.println("key : "+key+" value "+value);
}catch(KeyValueException daoException)
{
//System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends

