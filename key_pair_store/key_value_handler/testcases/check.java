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
    KeyValueMaster kvm= KeyValueMaster.getKeyValueMaster("C:\\Users\\harsh\\Desktop\\DataFiles\\");
    System.out.println("control coms here");

    for(int i=0;i<105;i++)
{
    try{
        Thread.sleep(1);
    }catch(Exception e)
    {

    }
// kvm.getKeyValueHandler("database1--1","table1_0").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
// kvm.getKeyValueHandler("database1--1","table1_1").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444});
// kvm.getKeyValueHandler("database1--1","table1_-1").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
// kvm.getKeyValueHandler("database1--1","table1_2").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
// kvm.getKeyValueHandler("database2--2","table1_32").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
// kvm.getKeyValueHandler("database2--2","table1_33").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
// kvm.getKeyValueHandler("database2--2","table1_34").set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");

//kvd.set("harshjmhr@"+i,"{editing data brooooooooooooo AK}");
kvm.getKeyValueHandler("database1--1","table1_0").remove("harshjmhr@"+i);
kvm.getKeyValueHandler("database1--1","table1_1").remove("harshjmhr@"+i);
kvm.getKeyValueHandler("database1--1","table1_-1").remove("harshjmhr@"+i);
kvm.getKeyValueHandler("database1--1","table1_2").remove("harshjmhr@"+i);
kvm.getKeyValueHandler("database2--2","table1_32").remove("harshjmhr@"+i);
kvm.getKeyValueHandler("database2--2","table1_33").remove("harshjmhr@"+i);
kvm.getKeyValueHandler("database2--2","table1_34").remove("harshjmhr@"+i);

}

    //kvm.createDataBase("database1--1");
    //kvm.createDataBase("database2--2");
    
    // for(int i=-1;i>-100;i--)
    // {
    //     kvm.createTable("database1--1","table1_"+i);
    //     kvm.createTable("database2--2","table1_"+i); 
    // }
    // kvm.createTable("database1","table1_1");
    // kvm.createTable("database1","table1_2");
    // kvm.createTable("database2","table2_1");
    // kvm.createTable("database2","table2_2");

KeyValueHandlerInterface kvd;
//kvd=KeyValueHandler.getKeyValueHandler("C:\\Users\\harsh\\Desktop\\DataFiles\\");



//kvd=KeyValueHandler.getKeyValueHandlerObject();
// for(int i=0;i<5000;i++)
// {
//     try{
//         Thread.sleep(1);
//     }catch(Exception e)
//     {

//     }
// //kvd.set("harshjmhr@"+i,"{name : 'letssssssss check on 11"+i+i+"' sharma', age : 5444}");
// //kvd.set("harshjmhr@"+i,"{editing data brooooooooooooo AK}");
// kvd.remove("harshjmhr@"+i);

// }
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