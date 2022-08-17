import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
import com.key.value.dataHandler.dao.*;
import java.util.*;
import java.io.*;
public class Delete
{
public static void main(String gg[])
{
try
{
String key=gg[0];
KeyValueDataHandlerInterface kvd;
kvd=new KeyValueDataHandler("F://datafile//");
kvd.delete(key,"52f92de9-4799-4fe9-a91d-1dbf0be645ce.data");
System.out.println("deleted");
//System.out.println("key : "+key+" value "+value);
}catch(KeyValueException daoException)
{
System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends