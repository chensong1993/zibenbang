package com.zhiyi.chinaipo.injections.qualifier;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({
        AuthType.AUTH_TYPE_GUEST,
        AuthType.AUTH_TYPE_USER,
        AuthType.AUTH_TYPE_REFRESH
})
public @interface AuthType {
    String AUTH_TYPE_GUEST = "client_credentials";

    String AUTH_TYPE_USER = "login_token";

    String AUTH_TYPE_REFRESH = "refresh_token";
}
