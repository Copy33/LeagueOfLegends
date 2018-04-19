package com.joemerhej.leagueoflegends.models;

/**
 * Created by Joe Merhej on 4/19/18.
 */
public class WidgetListItem
{
    private int widgetId;
    private String summonerName;
    private int rankImageId;


    public WidgetListItem()
    {
    }

    public WidgetListItem(int widgetId, String summonerName, int rankImageId)
    {
        this.widgetId = widgetId;
        this.summonerName = summonerName;
        this.rankImageId = rankImageId;
    }

    @Override
    public String toString()
    {
        return "WidgetListItem{" +
                "widgetId=" + widgetId +
                ", summonerName='" + summonerName + '\'' +
                ", rankImageId=" + rankImageId +
                '}';
    }

    public int getWidgetId()
    {
        return widgetId;
    }

    public void setWidgetId(int widgetId)
    {
        this.widgetId = widgetId;
    }

    public String getSummonerName()
    {
        return summonerName;
    }

    public void setSummonerName(String summonerName)
    {
        this.summonerName = summonerName;
    }

    public int getRankImageId()
    {
        return rankImageId;
    }

    public void setRankImageId(int rankImageId)
    {
        this.rankImageId = rankImageId;
    }
}
