package com.joemerhej.leagueoflegends.sharedpreferences;

/**
 * Created by Joe Merhej on 4/14/18.
 */

public enum SharedPreferencesKey
{
    COUNT("c_"),
    RANK_IMAGE_RES_ID("riri"),
    SUMMONER_NAME("sn_"),
    SUMMONER_ID("sid_"),
    SUMMONER_ICON_ID("siid_"),
    SUMMONER_LEVEL("sl_"),
    SUMMONER_SOLO_DUO_RANK("ssdr_"),
    SUMMONER_SOLO_DUO_LP("ssdl_"),
    SUMMONER_SOLO_DUO_HOTSTREAK("ssdh_"),
    SUMMONER_FLEX_5_RANK("sf5r_"),
    SUMMONER_FLEX_5_LP("sf5l_"),
    SUMMONER_FLEX_5_HOTSTREAK("sf5h_"),
    SUMMONER_FLEX_3_RANK("sf3r_"),
    SUMMONER_FLEX_3_LP("sf3l_"),
    SUMMONER_FLEX_3_HOTSTREAK("sf3h_");


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




