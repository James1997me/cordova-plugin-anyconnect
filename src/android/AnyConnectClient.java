package com.otp.plugins;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;


public class AnyConnectClient extends CordovaPlugin {

  //init private class variables
  private static final String ACTION_LAUNCH_ANYCONNECT = "launchanyconnect";
  private static final String TAG = "OtpGenerator";
  private JSONObject responseJSON;
  private Context context;
  private CallbackContext callback;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
      super.initialize(cordova, webView);
  }

  //plugin main interface function
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    //init response json object and context
    responseJSON = new JSONObject();
    context = this.cordova.getActivity();
    callback = callbackContext;

    try {
      Log.i(TAG, "Invoking AnyConnect Cordova Plugin");
      //check incoming actions
      if(ACTION_LAUNCH_ANYCONNECT.equals(action)) {

        //get the vpn details
        String vpnUrl = ((JSONObject)args.get(0)).getString("url");
        String userName = ((JSONObject)args.get(0)).getString("username");
        String userPassword = ((JSONObject)args.get(0)).getString("password");

        //init the url and launch anyconnect
        String url = "anyconnect://connect?host=" + vpnUrl + "&prefill_username=" + userName + "&prefill_password=" + userPassword;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        this.cordova.getActivity().startActivity(i);

        //send back response
        responseJSON.put("success", true);
        callback.success(responseJSON);
      }
      else {
        Log.i(TAG, "OTP command not found");
        //default action
        responseJSON.put("success", false);
        callback.error(responseJSON);
      }
    } catch(Exception e) {
        Log.e(TAG, "Exception: " + e.getMessage());

        //init response json object
        responseJSON.put("success", false);
        responseJSON.put("Error", e.getMessage());

        //send back the response with json object
        callback.error(responseJSON);
    }
    return true;
  }
}
