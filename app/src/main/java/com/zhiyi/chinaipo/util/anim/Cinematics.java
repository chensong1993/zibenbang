package com.zhiyi.chinaipo.util.anim;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.Window;
import android.widget.ImageView;

import com.zhiyi.chinaipo.ui.activity.search.SearchActivity;

/**
 * @author chensong
 * @date 2018/10/26 10:11
 */
public class Cinematics {
    public static void searchCinematics(Context context, ImageView imageView) {
        if (Build.VERSION.SDK_INT > 20) {
            context.startActivity(new Intent(context, SearchActivity.class), ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                    new Pair[]{Pair.create(imageView, "search")})
                    .toBundle());
        } else {
            context.startActivity(new Intent(context, SearchActivity.class));
        }
    }

    public static void requestFeature(Activity activity) {
        if (Build.VERSION.SDK_INT > 20) {
            activity.getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
    }

}
