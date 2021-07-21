package com.me.demo.util;

/**
 * Create by lzf on 2021-03-30
 */
public class Config {
    public final static int FRAGMENT_TAG_CALENDAR = 0;
    public final static int FRAGMENT_TAG_DATABASE = 1;
    public final static int FRAGMENT_TAG_WEBDAV = 2;
    public final static int FRAGMENT_TAG_UDP_CLIENT = 3;
    public final static int FRAGMENT_TAG_UDP_SERVER = 4;
    public final static int FRAGMENT_TAG_WEBSOCKET = 5;
    public final static int FRAGMENT_TAG_HTTP = 6;
    public final static int FRAGMENT_TAG_OKHTTP = 7;
    public final static int FRAGMENT_TAG_SURFACE = 8;

    /*******************************UDP相关*******************************/
    public final static int UDP_CLIENT_PORT = 8890;
    public final static int UDP_SERVER_PORT = 8891;

    /*******************************TCP相关*******************************/
    public final static int TCP_SERVER_PORT = 10010;

    /*******************************IP相关*******************************/
    public final static String DEFAULT_IP = "192.168.";
    public final static String DEFAULT_WEBSOCKET_IP = "ws://192.168.";
}
