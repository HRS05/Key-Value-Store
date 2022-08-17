package com.key.value.helper.pair;
public class Pair
{
    private String key;
    private String value;
    public Pair(String key,String value)
    {
        this.key=key;
        this.value=value;
    }
    public String getKey()
    {
        return this.key;
    }
    public String getValue()
    {
        return this.value;
    }
    public void setKey(String key)
    {
        this.key=key;
    }
    public void setValue(String value)
    {
        this.value=value;
    } 
}