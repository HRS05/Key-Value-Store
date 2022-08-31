import com.key.value.dataHandler.dao.*;
import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
class keyValueDataMasterDeleteTable
{
public static void main(String gg[])
{

try
{
String dataBase=gg[0];
String table=gg[1];
KeyValueDataMasterInterface keyValueDataMaster=new KeyValueDataMaster("F:/datafile");
keyValueDataMaster.deleteTable(dataBase,table);
}catch(KeyValueException kve)
{
System.out.println(kve.getMessage());
}
System.out.println("test for KeyValueDataMaster.......");
}
}