import com.key.value.handler.interfaces.*;
import com.key.value.handler.exceptions.*;
import com.key.value.handler.dao.*;
import java.util.*;
import java.io.*;
class check
{
public static void main(String gg[])
{
try
{
KeyValueHandlerInterface kvd;
kvd=KeyValueHandler.getKeyValueHandlerObject();
kvd=KeyValueHandler.getKeyValueHandlerObject();
kvd.set("harshjmhr@","{name : 'harsh sharma', age : 11}");
}catch(KeyValueHandlerException daoException)
{
System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends