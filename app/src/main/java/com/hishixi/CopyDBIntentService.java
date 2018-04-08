package com.hishixi;

import android.app.IntentService;
import android.content.Intent;


public class CopyDBIntentService extends IntentService {

    public CopyDBIntentService() {
        super("CopyDBIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String packageName = getApplication().getPackageName();
            DBManager dbManager = new DBManager(getApplication());
            dbManager.copyDB(packageName);
            CacheUtils.saveIfCopy(getApplication(),"1");
        }
    }

}
