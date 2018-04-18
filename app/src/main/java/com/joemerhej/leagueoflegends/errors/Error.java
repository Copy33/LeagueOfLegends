package com.joemerhej.leagueoflegends.errors;

/**
 * Created by Joe Merhej on 4/18/18.
 */
public enum Error
{
    GENERIC_ERROR(0),
    NO_INTERNET(1),
    INVALID_SUMMONER_NAME(2),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    LIMIT_EXCEEDED(429),
    INTERNAL_SERVER_ERROR(500),
    SERVICE_UNAVAILABLE(503);

    private final int code;

    Error(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public static Error from(int code)
    {
        switch(code)
        {
            case 400:
                return BAD_REQUEST;
            case 401:
                return UNAUTHORIZED;
            case 403:
                return FORBIDDEN;
            case 404:
                return NOT_FOUND;
            case 429:
                return LIMIT_EXCEEDED;
            case 500:
                return INTERNAL_SERVER_ERROR;
            case 503:
                return SERVICE_UNAVAILABLE;
            default:
                return GENERIC_ERROR;
        }
    }
}

