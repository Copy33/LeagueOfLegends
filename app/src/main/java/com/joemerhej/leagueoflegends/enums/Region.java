package com.joemerhej.leagueoflegends.enums;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public enum Region
{
    BR("br1"),
    EUNE("eun1"),
    EUW("euw1"),
    JP("jp1"),
    KR("kr"),
    LAN("la1"),
    LAS("la2"),
    NA("na1"),
    OCE("oc1"),
    TR("tr1"),
    RU("ru"),
    PBE("pbe1");

    private final String code;

    Region(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}
