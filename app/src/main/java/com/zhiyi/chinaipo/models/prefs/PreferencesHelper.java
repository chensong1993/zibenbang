package com.zhiyi.chinaipo.models.prefs;

public interface PreferencesHelper {

    boolean getNoImageState();

    void setNoImageState(boolean state);

    boolean getAutoCacheState();

    void setAutoCacheState(boolean state);

    int getCurrentItem();

    void setCurrentItem(int item);

    boolean getLikePoint();

    void setLikePoint(boolean isFirst);

    boolean getVersionPoint();

    void setVersionPoint(boolean isFirst);

    boolean getManagerPoint();

    void setManagerPoint(boolean isFirst);

}
