package com.joemerhej.leagueoflegends.sharedpreferences;

/**
 * Created by Joe Merhej on 4/14/18.
 */

public enum SharedPreferencesKey
{
    TEXT_KEY("text_"),
    COUNT_KEY("count_");

    private final String value;

    SharedPreferencesKey(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}




