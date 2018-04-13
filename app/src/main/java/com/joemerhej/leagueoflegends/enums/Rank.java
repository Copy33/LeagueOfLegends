package com.joemerhej.leagueoflegends.enums;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public enum Rank
{
    UNRANKED("UNRANKED"),
    BRONZE_5("BRONZE_V"),
    BRONZE_4("BRONZE_IV"),
    BRONZE_3("BRONZE_III"),
    BRONZE_2("BRONZE_II"),
    BRONZE_1("BRONZE_I"),
    SILVER_5("SILVER_V"),
    SILVER_4("SILVER_IV"),
    SILVER_3("SILVER_III"),
    SILVER_2("SILVER_II"),
    SILVER_1("SILVER_I"),
    GOLD_5("GOLD_V"),
    GOLD_4("GOLD_IV"),
    GOLD_3("GOLD_III"),
    GOLD_2("GOLD_II"),
    GOLD_1("GOLD_I"),
    PLATINUM_5("PLATINUM_V"),
    PLATINUM_4("PLATINUM_IV"),
    PLATINUM_3("PLATINUM_III"),
    PLATINUM_2("PLATINUM_II"),
    PLATINUM_1("PLATINUM_I"),
    DIAMOND_5("DIAMOND_V"),
    DIAMOND_4("DIAMOND_IV"),
    DIAMOND_3("DIAMOND_III"),
    DIAMOND_2("DIAMOND_II"),
    DIAMOND_1("DIAMOND_I"),
    MASTER("MASTER"),
    CHALLENGER("CHALLENGER_I");

    private final String code;

    Rank(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public static Rank from(String s)
    {
        switch(s)
        {
            case "BRONZE_V":
                return BRONZE_5;
            case "BRONZE_IV":
                return BRONZE_4;
            case "BRONZE_III":
                return BRONZE_3;
            case "BRONZE_II":
                return BRONZE_2;
            case "BRONZE_I":
                return BRONZE_1;
            case "SILVER_V":
                return SILVER_5;
            case "SILVER_IV":
                return SILVER_4;
            case "SILVER_III":
                return SILVER_3;
            case "SILVER_II":
                return SILVER_2;
            case "SILVER_I":
                return SILVER_1;
            case "GOLD_V":
                return GOLD_5;
            case "GOLD_IV":
                return GOLD_4;
            case "GOLD_III":
                return GOLD_3;
            case "GOLD_II":
                return GOLD_2;
            case "GOLD_I":
                return GOLD_1;
            case "PLATINUM_V":
                return PLATINUM_5;
            case "PLATINUM_IV":
                return PLATINUM_4;
            case "PLATINUM_III":
                return PLATINUM_3;
            case "PLATINUM_II":
                return PLATINUM_2;
            case "PLATINUM_I":
                return PLATINUM_1;
            case "DIAMOND_V":
                return DIAMOND_5;
            case "DIAMOND_IV":
                return DIAMOND_4;
            case "DIAMOND_III":
                return DIAMOND_3;
            case "DIAMOND_II":
                return DIAMOND_2;
            case "DIAMOND_I":
                return DIAMOND_1;
            case "MASTER_I":
                return MASTER;
            case "CHALLENGER_I":
                return CHALLENGER;
            case "UNRANKED":
                return UNRANKED;
        }

        return UNRANKED;
    }
}
