package com.zhiyi.chinaipo.models.event;


public class AuthenticatedEvent {

    public String getAuthMessage() {
        return authMessage;
    }

    public void setAuthMessage(String authMessage) {
        this.authMessage = authMessage;
    }

    public AuthEventType getAuthEvent() {
        return authEvent;
    }

    public void setAuthEvent(AuthEventType authEvent) {
        this.authEvent = authEvent;
    }

    public enum AuthEventType {
        LOGIN, LOGIN_SUCCEED, LOGIN_FAILED,
        LOGOUT, LOGOUT_DONE,
        CHANGE_PASSWD, CHANGE_PASSWD_SUCCEED, CHANGE_PASSWD_FAILED,
        CHANGE_PROFILE, CHANGE_PROFILE_SUCCEED, CHANGE_PROFILE_FAILED,
        USER_INFO_SUCCEED, USER_INFO_FAILED, USER_TOKEN

    }

    public AuthenticatedEvent(AuthEventType eventType, String eventMsg) {
        this.authEvent = eventType;
        this.authMessage = eventMsg;
    }

    public AuthenticatedEvent(String authMessage, AuthEventType authEvent, String userToken) {
        this.authMessage = authMessage;
        this.authEvent = authEvent;
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private String authMessage;
    private AuthEventType authEvent;
    private String userToken;
}
