package com.example.Filters;

public class JwTUtil {
    public static final String SECRET = "mySecret123";

    public static final String AUTH_HEADER = "Authorization";
    public static final String PREFIX="Bearer ";
    public static final int EXPIRE_ACCESS_TOKEN = 24*60*60*1000;
    public static final int EXPIRE_REFRESH_TOKEN = 24*60*60*1000;
}
