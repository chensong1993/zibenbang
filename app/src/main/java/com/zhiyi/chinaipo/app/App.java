package com.zhiyi.chinaipo.app;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.components.InitializeService;
import com.zhiyi.chinaipo.injections.components.AppComponent;
import com.zhiyi.chinaipo.injections.components.DaggerAppComponent;
import com.zhiyi.chinaipo.injections.modules.AppModule;
import com.zhiyi.chinaipo.injections.modules.HttpModule;
import com.zhiyi.chinaipo.models.greendao.DaoMaster;
import com.zhiyi.chinaipo.models.greendao.DaoSession;
import com.zhiyi.chinaipo.models.greendao.NewsSaveDao;
import com.zhiyi.chinaipo.models.greendao.NicknameEntityDao;
import com.zhiyi.chinaipo.models.greendao.PortraitEntityDao;
import com.zhiyi.chinaipo.models.greendao.SearchSaveDao;
import com.zhiyi.chinaipo.models.greendao.StockCodeEntityDao;
import com.zhiyi.chinaipo.util.LogUtil;

import org.android.agoo.xiaomi.MiPushRegistar;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import me.shaohui.shareutil.ShareConfig;
import me.shaohui.shareutil.ShareManager;

public class App extends Application {
    private static final String TAG = App.class.getName();
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    private Handler handler;
    private StockCodeEntityDao collectDao;
    private SearchSaveDao mSearchSaveDao;
    private NewsSaveDao mNewsSaveDao;
    private PortraitEntityDao mPortraitEntityDao;
    private NicknameEntityDao mNicknameEntityDao;
    private static App instance=null;
    public static AppComponent appComponent;
    private Set<Activity> allActivities;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static App getInstance(){
        if(instance==null){
            synchronized (App.class){
                if (instance==null){
                    instance=new App();
                }
            }
        }
        return instance;
    }
//    public static synchronized App getInstance() {
//
//        return instance;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        instance = this;
        MMKV.initialize(instance);
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(false);
        //初始化屏幕宽高
        getScreenSize();
        //初始化数据库
        Realm.init(getApplicationContext());
        //在子线程中完成其他初始化
        InitializeService.start(this);
        //log开关
        LogUtil.isShowLog =true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
       // Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
        // 初始化shareUtil
        ShareConfig config = ShareConfig.instance()
                .qqId("1105110346")
                .weiboId("2490597118")
                .wxId("wx86251dddaf9cbe00")
                .weiboRedirectUrl("http://sns.whalecloud.com/")
                .wxSecret("b9dcc49b3d9f1773f5d519697dccf3d9");
        ShareManager.init(config);
        try {
            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
            Field[] fs = aClass.getDeclaredFields();
            for (Field f : fs) {
                LogUtil.d("xxxxxx", "ff=" + f.getName() + "   " + f.getType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5a165f6cb27b0a0e3900026f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "4022355f67e723990fa5a5e7220b803e");
        //PushSDK初始化(如使用推送SDK，必须调用此方法)
        initUpush();
        //bugly注册
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
         */
       // Beta.canShowUpgradeActs.add(MainActivity.class);
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        Bugly.setAppChannel(getApplicationContext(), channel);
        Bugly.init(getApplicationContext(), "d651589135", false);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 安装tinker
        Beta.installTinker();
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }


    private void initUpush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        handler = new Handler(getMainLooper());

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        // sdk关闭通知声音
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // 通知声音由服务端控制
        // mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);

        // mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        // mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        mPushAgent.setDisplayNotificationNumber(3);
        mPushAgent.setMuteDurationSeconds(30);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            /**
             * 通知的回调方法（通知送达时会回调）
             */
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                //调用super，会展示通知，不调用super，则不展示通知。
                super.dealWithNotificationMessage(context, msg);
            }

            /**
             * 自定义消息的回调方法
             */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(),
                                R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon,
                                getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {

                LogUtil.i(TAG, "device token: " + deviceToken);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));

            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.i(TAG, "register failed: " + s + " " + s1);
                LogUtil.i(TAG, "onFailure: ");
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });

        //使用完全自定义处理
        //mPushAgent.setPushIntentServiceClass(UmengNotificationService.class);

        //小米通道
        MiPushRegistar.register(this, "2882303761517429082", "5481742981082");
        //华为通道
        // HuaWeiRegister.register(this);
        //魅族通道
        //MeizuRegister.register(this, MEIZU_APPID, MEIZU_APPKEY);
    }

    //关注的个股
    public StockCodeEntityDao getStockCodeDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(instance, "stockFollowed.db");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        DaoSession session = master.newSession();
        collectDao = session.getStockCodeEntityDao();
        return collectDao;
    }

    //搜索记录
    public SearchSaveDao getSearchSaveDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(instance, "searchSave");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        DaoSession session = master.newSession();
        mSearchSaveDao = session.getSearchSaveDao();
        return mSearchSaveDao;
    }

    //已读新闻
    public NewsSaveDao getNewsSaveDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(instance, "newsSave");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        DaoSession session = master.newSession();
        mNewsSaveDao = session.getNewsSaveDao();
        return mNewsSaveDao;
    }

    //头像数据库
    public PortraitEntityDao getPortraitEntityDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(instance, "portraitSave");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        DaoSession session = master.newSession();
        mPortraitEntityDao = session.getPortraitEntityDao();
        return mPortraitEntityDao;
    }

    //昵称数据库
    public NicknameEntityDao getNicknameEntityDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(instance, "nicknameSave");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        DaoSession session = master.newSession();
        mNicknameEntityDao = session.getNicknameEntityDao();
        return mNicknameEntityDao;
    }
}
