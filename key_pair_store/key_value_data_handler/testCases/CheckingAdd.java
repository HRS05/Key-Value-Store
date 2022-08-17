import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.dataHandler.dao.*;
import java.util.*;
import java.io.*;
public class CheckingAdd
{
public static void main(String gg[])
{
try
{
KeyValueDataHandlerInterface kvd;
String directory="C:\\git key value\\Key-Value-Store\\dataFiles\\";
System.out.println(directory);
kvd=new KeyValueDataHandler(directory);
String key,value;
key=gg[0];
value=gg[1];
System.out.println(kvd.add(key,value,"dfa8d3a6-4d80-4672-91be-cc01de5ef426.data"));

//System.out.println("key : "+key+" value "+value);
}catch(KeyValueException daoException)
{
System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends