package com.zhiyi.chinaipo.models.entity.auth;

/**
 * @author chensong
 * @date 2018/3/29 17:26
 */
public class CaptchaEntity {
    private String image_url;

    private String audio_url;

    private String image2x_url;

    private String hashkey;

    public void setImage_url(String image_url){
        this.image_url = image_url;
    }
    public String getImage_url(){
        return this.image_url;
    }
    public void setAudio_url(String audio_url){
        this.audio_url = audio_url;
    }
    public String getAudio_url(){
        return this.audio_url;
    }
    public void setImage2x_url(String image2x_url){
        this.image2x_url = image2x_url;
    }
    public String getImage2x_url(){
        return this.image2x_url;
    }
    public void setHashkey(String hashkey){
        this.hashkey = hashkey;
    }
    public String getHashkey(){
        return this.hashkey;
    }

}
