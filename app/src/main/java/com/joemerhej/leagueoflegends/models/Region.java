package com.joemerhej.leagueoflegends.models;

import com.joemerhej.leagueoflegends.enums.RegionCode;

/**
 * Created by Joe Merhej on 4/17/18.
 */
public class Region
{
    private String mName;
    private RegionCode mCode;
    private Integer mImageResourceId;

    public Region()
    {
    }

    public Region(String name, RegionCode code, Integer imageResourceId)
    {
        this.mName = name;
        this.mCode = code;
        this.mImageResourceId = imageResourceId;
    }

    @Override
    public String toString()
    {
        return "Region{" +
                "mName='" + mName + '\'' +
                ", mCode=" + mCode +
                ", mResourceId=" + mImageResourceId +
                '}';
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        this.mName = name;
    }

    public RegionCode getCode()
    {
        return mCode;
    }

    public void setCode(RegionCode code)
    {
        this.mCode = code;
    }

    public Integer getImageResourceId()
    {
        return mImageResourceId;
    }

    public void setImageResourceId(Integer imageResourceId)
    {
        this.mImageResourceId = imageResourceId;
    }
}
