package com.joemerhej.leagueoflegends.utils;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.enums.RegionCode;
import com.joemerhej.leagueoflegends.models.Region;

/**
 * Created by Joe Merhej on 4/17/18.
 */
public final class Regions
{
    public final static Region NORTH_AMERICA = new Region("North America", RegionCode.NA, R.drawable.flag_na);
    public final static Region KOREA = new Region("Korea", RegionCode.KR, R.drawable.flag_kr);
    public final static Region JAPAN = new Region("Japan", RegionCode.JP, R.drawable.flag_jp);
    public final static Region EUROPE_WEST = new Region("EU West", RegionCode.EUW, R.drawable.flag_euw);
    public final static Region EUROPE_NORDIC_AND_EAST = new Region("EU Nordic & East", RegionCode.EUNE, R.drawable.flag_eune);
    public final static Region OCEANIA = new Region("Oceania", RegionCode.OCE, R.drawable.flag_oce);
    public final static Region BRAZIL = new Region("Brazil", RegionCode.BR, R.drawable.flag_br);
    public final static Region LATIN_AMERICA_SOUTH = new Region("LAS", RegionCode.LAS, R.drawable.flag_las);
    public final static Region LATIN_AMERICA_NORTH = new Region("LAN", RegionCode.LAN, R.drawable.flag_lan);
    public final static Region RUSSIA = new Region("Russia", RegionCode.RU, R.drawable.flag_ru);
    public final static Region TURKEY = new Region("Turkey", RegionCode.TR, R.drawable.flag_tr);
    public final static Region PUBLIC_BETA = new Region("PBE", RegionCode.PBE, R.drawable.flag_pbe);
}
