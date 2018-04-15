package com.joemerhej.leagueoflegends.enums;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public enum Rank
{
    UNRANKED("Unranked"),
    BRONZE_5("Bronze V"),
    BRONZE_4("Bronze IV"),
    BRONZE_3("Bronze III"),
    BRONZE_2("Bronze II"),
    BRONZE_1("Bronze I"),
    SILVER_5("Silver V"),
    SILVER_4("Silver IV"),
    SILVER_3("Silver III"),
    SILVER_2("Silver II"),
    SILVER_1("Silver I"),
    GOLD_5("Gold V"),
    GOLD_4("Gold IV"),
    GOLD_3("Gold III"),
    GOLD_2("Gold II"),
    GOLD_1("Gold I"),
    PLATINUM_5("Platinum V"),
    PLATINUM_4("Platinum IV"),
    PLATINUM_3("Platinum III"),
    PLATINUM_2("Platinum II"),
    PLATINUM_1("Platinum I"),
    DIAMOND_5("Diamond V"),
    DIAMOND_4("Diamond IV"),
    DIAMOND_3("Diamond III"),
    DIAMOND_2("Diamond II"),
    DIAMOND_1("Diamond I"),
    MASTER("Master"),
    CHALLENGER("Challenger");

    private final String name;

    Rank(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
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
