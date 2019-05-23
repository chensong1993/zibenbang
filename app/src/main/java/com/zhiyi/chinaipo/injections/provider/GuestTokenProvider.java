package com.zhiyi.chinaipo.injections.provider;

import com.github.pwittchen.prefser.library.Prefser;
import com.zhiyi.chinaipo.app.Constants;


public class GuestTokenProvider implements TokenProvider {
    private Prefser prefser;

    public GuestTokenProvider(Prefser prefser) {
        this.prefser = prefser;
    }

    @Override
    public String getToken() {
        if (prefser != null) {
            return prefser.get(Constants.GUEST_TOKEN_KEY, String.class, "");
        }
        return null;
    }
}