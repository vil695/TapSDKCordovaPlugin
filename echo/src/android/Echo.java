package com.vil.echo;

import android.app.Activity;
import android.util.Log;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs; 
import org.apache.cordova.CordovaPlugin;

public class TapSDKCordovaPlugin extends CordovaPlugin {
    public final static String LOG_TAG = "plugin.Echo";

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        Log.d(LOG_TAG, LOG_TAG+" pluginInitialize");
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) {
        if (action.equals("updateGame")) {
            return this.updateGame(args, callbackContext);
        }
        return false;
    }

    private boolean updateGame(CordovaArgs args, CallbackContext callbackContext) {
        Log.d(LOG_TAG, LOG_TAG+"updateGame");

        callbackContext.success();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}