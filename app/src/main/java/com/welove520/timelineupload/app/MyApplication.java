package com.welove520.timelineupload.app;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.welove520.timelineupload.task.DaoMaster;
import com.welove520.timelineupload.task.DaoSession;

import org.greenrobot.greendao.database.Database;


/**
 * Created by Raomengyang on 18-8-3.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
public class MyApplication extends Application {
    public static final boolean ENCRYPTED = true;
    private static MyApplication INSTANCE = null;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initStetho();
        initGreenDao();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "welove-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApplication get() {
        return INSTANCE;
    }
}
