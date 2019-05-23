package com.zhiyi.chinaipo.push;

import android.content.Intent;
import android.text.TextUtils;

import com.umeng.message.UmengNotifyClickActivity;
import com.umeng.message.entity.UMessage;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.ui.activity.NewsDetailActivity;
import com.zhiyi.chinaipo.ui.activity.misc.WebActivity;
import com.zhiyi.chinaipo.util.FormatUtil;
import com.zhiyi.chinaipo.util.LogUtil;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MipushTestActivity extends UmengNotifyClickActivity {
    private static String TAG = MipushTestActivity.class.getName();
    // private TextView mipushTextView;

   /* @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mipush);
        LogUtil.i(TAG, "onCreate: ");
     //   mipushTextView = findViewById(R.id.mipushTextView);
    }*/

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject object = new JSONObject(body);
                        UMessage msg = new UMessage(object);
                        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {

                            String key = entry.getKey();
                            String value = entry.getValue();
                            LogUtil.i("UPush_Message", value);
                            if (key.equals("url")) {
                                Intent intent = new Intent(MipushTestActivity.this, WebActivity.class);
                                intent.putExtra(Constants.GOTO_WEB_URL, value);
                                startActivity(intent);
                            } else {
                                if (value != null && FormatUtil.isNumeric(value)) {
                                    Intent intent1 = new Intent(MipushTestActivity.this, NewsDetailActivity.class);
                                    intent1.putExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, Integer.valueOf(value));
                                    startActivity(intent1);
                                }
                            }

                            finish();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }

    }
}