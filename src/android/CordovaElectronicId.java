package pluginCordovaElectronicId;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import pluginCordovaElectronicId.ElectronicIdLauncherActivity;

/**
 * This class echoes a string called from JavaScript.
 */
public class CordovaElectronicId extends CordovaPlugin {

    private CallbackContext mCallbackContext;

    private static final int REQUEST_CODE_ELECTRONICID = 20;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("launchElectronicIdProcess")) {
            Context context = this.cordova.getActivity().getApplicationContext();
            mCallbackContext = callbackContext;
            this.launchElectronicIdProcess(callbackContext, context, args);
            return true;
        }
        return false;
    }

    private void launchElectronicIdProcess(CallbackContext callbackContext, Context context, JSONArray args) throws JSONException {
        JSONObject jsonobject = args.getJSONObject(0);

        String ARGS_ELECTRONIC_ID_BASE_URL = jsonobject.has("endpoint") ? jsonobject.getString("endpoint") : "";
        String ARGS_ELECTRONIC_ID_REGISTRATION_AUTHORITY = jsonobject.has("rAuthority") ? jsonobject.getString("rAuthority") : "";
        String ARGS_ELECTRONIC_ID_BEARER_ID = jsonobject.has("bearer") ? jsonobject.getString("bearer") : "";
        String ARGS_ELECTRONIC_ID_TENANT_ID = jsonobject.has("tenant") ? jsonobject.getString("tenant") : "";
        String ARGS_ELECTRONIC_ID_VIDEO_MODE = jsonobject.has("videoMode") ? jsonobject.getString("videoMode") : "";
        String ARGS_ELECTRONIC_ID_MIN_SIMILARITY = jsonobject.has("minSimilarity") ? jsonobject.getString("minSimilarity") : "";
        String ARGS_ELECTRONIC_ID_AUTH_LANGUAGE = jsonobject.has("language") ? jsonobject.getString("language") : "";
        String ARGS_ELECTRONIC_ID_DOCUMENT_TYPE = jsonobject.has("documentType") ? jsonobject.getString("documentType") : "";
        
        // INTENT
        Intent intent = new Intent(context, ElectronicIdLauncherActivity.class);

        // Param values
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_BASE_URL, ARGS_ELECTRONIC_ID_BASE_URL);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_REGISTRATION_AUTHORITY, ARGS_ELECTRONIC_ID_REGISTRATION_AUTHORITY);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_BEARER_ID, ARGS_ELECTRONIC_ID_BEARER_ID);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_TENANT_ID, ARGS_ELECTRONIC_ID_TENANT_ID);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_VIDEO_MODE, ARGS_ELECTRONIC_ID_VIDEO_MODE);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_MIN_SIMILARITY, ARGS_ELECTRONIC_ID_MIN_SIMILARITY);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_AUTH_LANGUAGE, ARGS_ELECTRONIC_ID_AUTH_LANGUAGE);
        intent.putExtra(ElectronicIdLauncherActivity.ARGS_ELECTRONIC_ID_DOCUMENT_TYPE, ARGS_ELECTRONIC_ID_DOCUMENT_TYPE);

        this.cordova.getActivity().startActivityForResult(intent, REQUEST_CODE_ELECTRONICID);
        this.cordova.setActivityResultCallback(this);
    }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODE_ELECTRONICID && data != null) {

      if (resultCode == Activity.RESULT_OK) {
        String successMessage = data.getStringExtra(ElectronicIdLauncherActivity.RESULT_MESSAGE);
        mCallbackContext.success(successMessage);
      } else if (resultCode == Activity.RESULT_CANCELED) {
        String errorMessage = data.getStringExtra(ElectronicIdLauncherActivity.RESULT_MESSAGE);
        mCallbackContext.error(errorMessage);
      }

    }

  }
}
