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
kvd=KeyValueHandler.getKeyValueHandler("C:\\Users\\harsh\\Desktop\\DataFiles\\");
//kvd=KeyValueHandler.getKeyValueHandlerObject();
for(int i=0;i<5000;i++)
{
    try{
        Thread.sleep(1);
    }catch(Exception e)
    {

    }
//kvd.set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
//kvd.set("harshjmhr@"+i,"{editing data brooooooooooooo AK}");
kvd.remove("harshjmhr@"+i);

}
// kvd.set("harshjmhr@207","{editing 207}");
// kvd.set("harshjmhr@105","{editing 105}");
// kvd.set("harshjmhr@97","{editing 97}");
// kvd.remove("harshjmhr@207");
// kvd.remove("harshjmhr@105");
// kvd.remove("harshjmhr@97");

}catch(KeyValueHandlerException daoException)
{
System.out.println(daoException.getMessage());
}
}//main Ends
}//class Ends