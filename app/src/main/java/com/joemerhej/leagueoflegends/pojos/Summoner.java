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
        "profileIconId",
        "name",
        "summonerLevel",
        "accountId",
        "id",
        "revisionDate"
})
public class Summoner
{

    @JsonProperty("profileIconId")
    private Long profileIconId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("summonerLevel")
    private Long summonerLevel;
    @JsonProperty("accountId")
    private Long accountId;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("revisionDate")
    private Long revisionDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     * No args constructor for use in serialization
     *
     */
    public Summoner() {
    }

    /**
     *
     * @param id
     * @param accountId
     * @param name
     * @param profileIconId
     * @param revisionDate
     * @param summonerLevel
     */
    public Summoner(Long profileIconId, String name, Long summonerLevel, Long accountId, Long id, Long revisionDate) {
        super();
        this.profileIconId = profileIconId;
        this.name = name;
        this.summonerLevel = summonerLevel;
        this.accountId = accountId;
        this.id = id;
        this.revisionDate = revisionDate;
    }

    @Override
    public String toString()
    {
        return "Summoner{" +
                "profileIconId=" + profileIconId +
                ", name='" + name + '\'' +
                ", summonerLevel=" + summonerLevel +
                ", accountId=" + accountId +
                ", id=" + id +
                ", revisionDate=" + revisionDate +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("profileIconId")
    public Long getProfileIconId()
    {
        return profileIconId;
    }

    @JsonProperty("profileIconId")
    public void setProfileIconId(Long profileIconId)
    {
        this.profileIconId = profileIconId;
    }

    @JsonProperty("name")
    public String getName()
    {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name)
    {
        this.name = name;
    }

    @JsonProperty("summonerLevel")
    public Long getSummonerLevel()
    {
        return summonerLevel;
    }

    @JsonProperty("summonerLevel")
    public void setSummonerLevel(Long summonerLevel)
    {
        this.summonerLevel = summonerLevel;
    }

    @JsonProperty("accountId")
    public Long getAccountId()
    {
        return accountId;
    }

    @JsonProperty("accountId")
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    @JsonProperty("id")
    public Long getId()
    {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id)
    {
        this.id = id;
    }

    @JsonProperty("revisionDate")
    public Long getRevisionDate()
    {
        return revisionDate;
    }

    @JsonProperty("revisionDate")
    public void setRevisionDate(Long revisionDate)
    {
        this.revisionDate = revisionDate;
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