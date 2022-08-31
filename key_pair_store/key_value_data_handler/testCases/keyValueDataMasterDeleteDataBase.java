import com.key.value.dataHandler.dao.*;
import com.key.value.dataHandler.interfaces.*;
import com.key.value.dataHandler.exceptions.*;
class keyValueDataMasterDeleteDataBase
{
public static void main(String gg[])
{

try
{
String dataBase=gg[0];
KeyValueDataMasterInterface keyValueDataMaster=new KeyValueDataMaster("F:/datafile");
keyValueDataMaster.deleteDataBase(dataBase);
}catch(KeyValueException kve)
{
System.out.println(kve.getMessage());
}
System.out.println("test for KeyValueDataMaster deleteDataBase.......");
}
}