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

    public String value()
    {
        return code;
    }

    public static RegionCode from(String code)
    {
        switch(code)
        {
            case "na1":
                return NA;
            case "kr":
                return KR;
            case "jp1":
                return JP;
            case "euw1":
                return EUW;
            case "eun1":
                return EUNE;
            case "oc1":
                return OCE;
            case "br1":
                return BR;
            case "la2":
                return LAS;
            case "la1":
                return LAN;
            case "pbe1":
                return PBE;
            case "ru":
                return RU;
            case "tr1":
                return TR;
            default:
                return NA;

        }
    }
}
