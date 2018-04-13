package com.joemerhej.leagueoflegends.pojos;

/**
 * Created by Joe Merhej on 4/13/18.
 */


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "queueType",
        "hotStreak",
        "wins",
        "veteran",
        "losses",
        "playerOrTeamId",
        "leagueName",
        "playerOrTeamName",
        "inactive",
        "rank",
        "freshBlood",
        "leagueId",
        "tier",
        "leaguePoints"
})
public class RankedData
{

    @JsonProperty("queueType")
    private String queueType;
    @JsonProperty("hotStreak")
    private Boolean hotStreak;
    @JsonProperty("wins")
    private Long wins;
    @JsonProperty("veteran")
    private Boolean veteran;
    @JsonProperty("losses")
    private Long losses;
    @JsonProperty("playerOrTeamId")
    private String playerOrTeamId;
    @JsonProperty("leagueName")
    private String leagueName;
    @JsonProperty("playerOrTeamName")
    private String playerOrTeamName;
    @JsonProperty("inactive")
    private Boolean inactive;
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("freshBlood")
    private Boolean freshBlood;
    @JsonProperty("leagueId")
    private String leagueId;
    @JsonProperty("tier")
    private String tier;
    @JsonProperty("leaguePoints")
    private Long leaguePoints;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public RankedData()
    {
    }

    /**
     * @param hotStreak
     * @param leagueName
     * @param tier
     * @param freshBlood
     * @param playerOrTeamId
     * @param leaguePoints
     * @param inactive
     * @param leagueId
     * @param rank
     * @param veteran
     * @param queueType
     * @param losses
     * @param playerOrTeamName
     * @param wins
     */
    public RankedData(String queueType, Boolean hotStreak, Long wins, Boolean veteran, Long losses, String playerOrTeamId, String leagueName, String playerOrTeamName, Boolean inactive, String rank, Boolean freshBlood, String leagueId, String tier, Long leaguePoints)
    {
        super();
        this.queueType = queueType;
        this.hotStreak = hotStreak;
        this.wins = wins;
        this.veteran = veteran;
        this.losses = losses;
        this.playerOrTeamId = playerOrTeamId;
        this.leagueName = leagueName;
        this.playerOrTeamName = playerOrTeamName;
        this.inactive = inactive;
        this.rank = rank;
        this.freshBlood = freshBlood;
        this.leagueId = leagueId;
        this.tier = tier;
        this.leaguePoints = leaguePoints;
    }

    @Override
    public String toString()
    {
        return "RankedData{" +
                "queueType='" + queueType + '\'' +
                ", hotStreak=" + hotStreak +
                ", wins=" + wins +
                ", veteran=" + veteran +
                ", losses=" + losses +
                ", playerOrTeamId='" + playerOrTeamId + '\'' +
                ", leagueName='" + leagueName + '\'' +
                ", playerOrTeamName='" + playerOrTeamName + '\'' +
                ", inactive=" + inactive +
                ", rank='" + rank + '\'' +
                ", freshBlood=" + freshBlood +
                ", leagueId='" + leagueId + '\'' +
                ", tier='" + tier + '\'' +
                ", leaguePoints=" + leaguePoints +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("queueType")
    public String getQueueType()
    {
        return queueType;
    }

    @JsonProperty("queueType")
    public void setQueueType(String queueType)
    {
        this.queueType = queueType;
    }

    @JsonProperty("hotStreak")
    public Boolean getHotStreak()
    {
        return hotStreak;
    }

    @JsonProperty("hotStreak")
    public void setHotStreak(Boolean hotStreak)
    {
        this.hotStreak = hotStreak;
    }

    @JsonProperty("wins")
    public Long getWins()
    {
        return wins;
    }

    @JsonProperty("wins")
    public void setWins(Long wins)
    {
        this.wins = wins;
    }

    @JsonProperty("veteran")
    public Boolean getVeteran()
    {
        return veteran;
    }

    @JsonProperty("veteran")
    public void setVeteran(Boolean veteran)
    {
        this.veteran = veteran;
    }

    @JsonProperty("losses")
    public Long getLosses()
    {
        return losses;
    }

    @JsonProperty("losses")
    public void setLosses(Long losses)
    {
        this.losses = losses;
    }

    @JsonProperty("playerOrTeamId")
    public String getPlayerOrTeamId()
    {
        return playerOrTeamId;
    }

    @JsonProperty("playerOrTeamId")
    public void setPlayerOrTeamId(String playerOrTeamId)
    {
        this.playerOrTeamId = playerOrTeamId;
    }

    @JsonProperty("leagueName")
    public String getLeagueName()
    {
        return leagueName;
    }

    @JsonProperty("leagueName")
    public void setLeagueName(String leagueName)
    {
        this.leagueName = leagueName;
    }

    @JsonProperty("playerOrTeamName")
    public String getPlayerOrTeamName()
    {
        return playerOrTeamName;
    }

    @JsonProperty("playerOrTeamName")
    public void setPlayerOrTeamName(String playerOrTeamName)
    {
        this.playerOrTeamName = playerOrTeamName;
    }

    @JsonProperty("inactive")
    public Boolean getInactive()
    {
        return inactive;
    }

    @JsonProperty("inactive")
    public void setInactive(Boolean inactive)
    {
        this.inactive = inactive;
    }

    @JsonProperty("rank")
    public String getRank()
    {
        return rank;
    }

    @JsonProperty("rank")
    public void setRank(String rank)
    {
        this.rank = rank;
    }

    @JsonProperty("freshBlood")
    public Boolean getFreshBlood()
    {
        return freshBlood;
    }

    @JsonProperty("freshBlood")
    public void setFreshBlood(Boolean freshBlood)
    {
        this.freshBlood = freshBlood;
    }

    @JsonProperty("leagueId")
    public String getLeagueId()
    {
        return leagueId;
    }

    @JsonProperty("leagueId")
    public void setLeagueId(String leagueId)
    {
        this.leagueId = leagueId;
    }

    @JsonProperty("tier")
    public String getTier()
    {
        return tier;
    }

    @JsonProperty("tier")
    public void setTier(String tier)
    {
        this.tier = tier;
    }

    @JsonProperty("leaguePoints")
    public Long getLeaguePoints()
    {
        return leaguePoints;
    }

    @JsonProperty("leaguePoints")
    public void setLeaguePoints(Long leaguePoints)
    {
        this.leaguePoints = leaguePoints;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties()
    {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value)
    {
        this.additionalProperties.put(name, value);
    }

}
