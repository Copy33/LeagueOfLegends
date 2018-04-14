package com.joemerhej.leagueoflegends.sharedpreferences;

/**
 * Created by Joe Merhej on 4/14/18.
 */

public enum SPKey
{
    TEXT_KEY("text_"),
    COUNT_KEY("count_");

    private final String value;

    SPKey(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}




