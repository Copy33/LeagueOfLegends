package com.joemerhej.leagueoflegends.enums;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public enum RegionCode
{
    NA("na1"),
    KR("kr"),
    JP("jp1"),
    EUW("euw1"),
    EUNE("eun1"),
    OCE("oc1"),
    BR("br1"),
    LAS("la2"),
    LAN("la1"),
    PBE("pbe1"),
    RU("ru"),
    TR("tr1");

    private final String code;

    RegionCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}
