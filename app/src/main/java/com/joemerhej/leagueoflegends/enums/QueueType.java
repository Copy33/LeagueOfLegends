package com.joemerhej.leagueoflegends.enums;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public enum QueueType
{
    SOLO_DUO("RANKED_FLEX_SR"),
    FLEX_5V5("RANKED_SOLO_5x5"),
    FLEX_3V3("RANKED_FLEX_TT");

    private final String value;

    QueueType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static QueueType from(String s)
    {
        switch(s)
        {
            case "RANKED_FLEX_SR":
                return FLEX_5V5;
            case "RANKED_SOLO_5x5":
                return SOLO_DUO;
            case "RANKED_FLEX_TT":
                return FLEX_3V3;
            default:
                return SOLO_DUO;
        }
    }
}
