package com.joemerhej.leagueoflegends.models;

import com.joemerhej.leagueoflegends.enums.QueueType;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public class Profile
{
    private String name;
    private Long id;
    private Long profileIconId;
    private Long summonerLevel;
    private QueueRank SoloDuo;
    private QueueRank Flex5;
    private QueueRank Flex3;


    public Profile()
    {
        name = "";
        id = 0L;
        profileIconId = 0L;
        summonerLevel = 0L;
        setRanks(new QueueRank(QueueType.SOLO_DUO), new QueueRank(QueueType.FLEX_5V5), new QueueRank(QueueType.FLEX_3V3));
    }

    public Profile(String name, Long id, Long profileIconId, Long summonerLevel, QueueRank soloDuo, QueueRank flex5, QueueRank flex3)
    {
        this.name = name;
        this.id = id;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        SoloDuo = soloDuo;
        Flex5 = flex5;
        Flex3 = flex3;
    }

    @Override
    public String toString()
    {
        return "Profile{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", profileIconId=" + profileIconId +
                ", summonerLevel=" + summonerLevel +
                ", SoloDuo=" + SoloDuo +
                ", Flex5=" + Flex5 +
                ", Flex3=" + Flex3 +
                '}';
    }

    public void set(String name, Long id, Long profileIconId, Long summonerLevel, QueueRank soloDuo, QueueRank flex5, QueueRank flex3)
    {
        this.name = name;
        this.id = id;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        SoloDuo = soloDuo;
        Flex5 = flex5;
        Flex3 = flex3;
    }

    public void set(String name, Long id, Long profileIconId, Long summonerLevel)
    {
        this.name = name;
        this.id = id;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
    }

    public void setRanks(QueueRank soloDuo, QueueRank flex5, QueueRank flex3)
    {
        SoloDuo = soloDuo;
        Flex5 = flex5;
        Flex3 = flex3;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getProfileIconId()
    {
        return profileIconId;
    }

    public void setProfileIconId(Long profileIconId)
    {
        this.profileIconId = profileIconId;
    }

    public Long getSummonerLevel()
    {
        return summonerLevel;
    }

    public void setSummonerLevel(Long summonerLevel)
    {
        this.summonerLevel = summonerLevel;
    }

    public QueueRank getSoloDuo()
    {
        return SoloDuo;
    }

    public void setSoloDuo(QueueRank soloDuo)
    {
        SoloDuo = soloDuo;
    }

    public QueueRank getFlex5()
    {
        return Flex5;
    }

    public void setFlex5(QueueRank flex5)
    {
        Flex5 = flex5;
    }

    public QueueRank getFlex3()
    {
        return Flex3;
    }

    public void setFlex3(QueueRank flex3)
    {
        Flex3 = flex3;
    }
}
