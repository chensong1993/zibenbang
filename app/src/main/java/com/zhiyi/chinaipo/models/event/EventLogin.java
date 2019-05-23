package com.zhiyi.chinaipo.models.event;

/**
 */

public class EventLogin {
    private String name;
    private String token;
    private String icon;
    private String login;

    public EventLogin() {
    }

    public EventLogin(String token) {
        this.token = token;
    }

    public EventLogin(String name, String icon, String login) {
        this.name = name;
        this.icon = icon;
        this.login = login;
    }

    public EventLogin(String name, String token, String icon, String login) {
        this.name = name;
        this.token = token;
        this.icon = icon;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
