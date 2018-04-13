package com.joemerhej.leagueoflegends.models;

import com.joemerhej.leagueoflegends.enums.QueueType;
import com.joemerhej.leagueoflegends.enums.Rank;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public class QueueRank
{
    private QueueType queueType;
    private Rank rank;
    private Long leaguePoints;
    private Boolean hotStreak;


    public QueueRank()
    {
    }

    public QueueRank(QueueType queueType, String tier, String rankNumber, Long leaguePoints, Boolean hotStreak) throws IllegalArgumentException
    {
        this.queueType = queueType;
        this.leaguePoints = leaguePoints;
        this.hotStreak = hotStreak;

        String rankName = tier + "_" + rankNumber;
        this.rank = Rank.from(rankName);
    }

    @Override
    public String toString()
    {
        return "QueueRank{" +
                "queueType=" + queueType +
                ", rank=" + rank +
                ", leaguePoints=" + leaguePoints +
                ", hotStreak=" + hotStreak +
                '}';
    }

    public void set(QueueType queueType, String tier, String rankNumber, Long leaguePoints, Boolean hotStreak) throws IllegalArgumentException
    {
        this.queueType = queueType;
        this.leaguePoints = leaguePoints;
        this.hotStreak = hotStreak;

        String rankName = tier + "_" + rankNumber;
        this.rank = Rank.from(rankName);
    }

    public QueueType getQueueType()
    {
        return queueType;
    }

    public void setQueueType(QueueType queueType)
    {
        this.queueType = queueType;
    }

    public Long getLeaguePoints()
    {
        return leaguePoints;
    }

    public void setLeaguePoints(Long leaguePoints)
    {
        this.leaguePoints = leaguePoints;
    }

    public Boolean getHotStreak()
    {
        return hotStreak;
    }

    public void setHotStreak(Boolean hotStreak)
    {
        this.hotStreak = hotStreak;
    }
}
