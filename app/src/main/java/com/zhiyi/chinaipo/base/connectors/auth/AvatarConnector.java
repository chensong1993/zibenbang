package com.zhiyi.chinaipo.base.connectors.auth;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.models.entity.auth.captchaAuthEntity;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public interface AvatarConnector {

    interface View extends BaseView {
        void inProcessing(Throwable statusMsg);
        void loginSuccess(HashKeyEntity mResult);
        void avatarFile(String mfilePath);
    }

    interface Presenter extends BasePresenter<View> {
        void getAvatar(String token,MultipartBody.Part file);
        void getAvatarUpload(String token,RequestBody file);
    }
}
