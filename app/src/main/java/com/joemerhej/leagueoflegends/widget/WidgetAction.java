package com.joemerhej.leagueoflegends.widget;

/**
 * Created by Joe Merhej on 4/14/18.
 */

public enum WidgetAction
{
    REFRESH("com.joemerhej.leagueoflegends.WIDGET_ACTION_REFRESH"),
    EDIT("com.joemerhej.leagueoflegends.WIDGET_ACTION_EDIT");

    private final String value;

    WidgetAction(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}


