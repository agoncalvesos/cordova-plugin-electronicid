package pluginCordovaElectronicId;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pluginCordovaElectronicId.model.ElectronicIdAuthorizationRequest;
import pluginCordovaElectronicId.model.ElectronicIdAuthorizationResponse;
import pluginCordovaElectronicId.model.ElectronicIdVideoDataRequest;

import com.google.gson.Gson;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import eu.electronicid.sdk.videoid.VideoIDActivity;
import eu.electronicid.sdk.video.config.Environment;

public class ElectronicIdLauncherActivity extends AppCompatActivity {
    public static final String ARGS_ELECTRONIC_ID_BASE_URL = "ARGS_ELECTRONIC_ID_BASE_URL";
    public static final String ARGS_ELECTRONIC_ID_REGISTRATION_AUTHORITY = "ARGS_ELECTRONIC_ID_REGISTRATION_AUTHORITY";
    public static final String ARGS_ELECTRONIC_ID_BEARER_ID = "ARGS_ELECTRONIC_ID_BEARER_ID";
    public static final String ARGS_ELECTRONIC_ID_TENANT_ID = "ARGS_ELECTRONIC_ID_TENANT_ID";
    public static final String ARGS_ELECTRONIC_ID_VIDEO_MODE = "ARGS_ELECTRONIC_ID_VIDEO_MODE";
    public static final String ARGS_ELECTRONIC_ID_MIN_SIMILARITY = "ARGS_ELECTRONIC_ID_MIN_SIMILARITY";
    public static final String ARGS_ELECTRONIC_ID_AUTH_LANGUAGE = "ARGS_ELECTRONIC_ID_AUTH_LANGUAGE";
    public static final String ARGS_ELECTRONIC_ID_DOCUMENT_TYPE = "ARGS_ELECTRONIC_ID_DOCUMENT_TYPE";

    public static final String RESULT_MESSAGE = "RESULT_MESSAGE";

    private static final int REQUEST_CODE_ELECTRONIC_ID_ACTIVITY = 10;

    private static final int DEFAULT_AUTH_DOCUMENT_TYPE = 62;
    private static final String DEFAULT_AUTH_LANGUAGE = "en";
    private static final String DEFAULT_VIDEO_MODE = "Unattended";
    private static final String DEFAULT_MIN_SIMILARITY = "Low";

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_BEARER = "Bearer %s";

    private static final String ELECTRONIC_ID_AUTHORIZATION_URL = "/videoid.request";
    private static final String ELECTRONIC_ID_VIDEO_DATA_URL = "/videoid/%s";

    private static final String VIDEOID_ARGS_ENVIRONMENT = "eu.electronicid.sdk.videoid.ENVIRONMENT";
    private static final String VIDEOID_ARGS_INTENT_DOCUMENT_TYPE = "eu.electronicid.sdk.videoid.INTENT_DOCUMENT_TYPE";
    private static final String VIDEOID_ARGS_INTENT_MIN_SIMILARITY = "eu.electronicid.sdk.videoid.INTENT_MIN_SIMILARITY";
    private static final String VIDEOID_ARGS_INTENT_VIDEO_MODE = "eu.electronicid.sdk.videoid.INTENT_VIDEO_MODE";
    private static final String VIDEOID_ARGS_INTENT_LANGUAGE = "eu.electronicid.sdk.videoid.INTENT_LANGUAGE";

    private String baseUrl;
    private String registrationAuthority;
    private String bearerId;
    private String tenantId;
    private String videoMode;
    private String minSimilarity;
    private String authLanguage;
    private int documentType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            baseUrl = extras.getString(ARGS_ELECTRONIC_ID_BASE_URL);
            registrationAuthority = extras.getString(ARGS_ELECTRONIC_ID_REGISTRATION_AUTHORITY);
            bearerId = extras.getString(ARGS_ELECTRONIC_ID_BEARER_ID);
            tenantId = extras.getString(ARGS_ELECTRONIC_ID_TENANT_ID);
            videoMode = extras.getString(ARGS_ELECTRONIC_ID_VIDEO_MODE, DEFAULT_VIDEO_MODE);
            minSimilarity = extras.getString(ARGS_ELECTRONIC_ID_MIN_SIMILARITY, DEFAULT_MIN_SIMILARITY);
            authLanguage = extras.getString(ARGS_ELECTRONIC_ID_AUTH_LANGUAGE, DEFAULT_AUTH_LANGUAGE);
            documentType = extras.getInt(ARGS_ELECTRONIC_ID_DOCUMENT_TYPE, DEFAULT_AUTH_DOCUMENT_TYPE);
        }

        launchAuthorizationCall();
    }

    private String getBearerHeader() {
        return String.format(HEADER_BEARER, bearerId);
    }

    private void launchAuthorizationCall() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_AUTHORIZATION, getBearerHeader());

        ElectronicIdAuthorizationRequest request = new ElectronicIdAuthorizationRequest();
        request.setTenantId(tenantId);
        request.setProcess(videoMode);
        request.setRauthorityId(registrationAuthority);

        new Api.ApiCallPost(headers, new Api.ApiCallListener() {
            @Override
            public void success(String response) {
                ElectronicIdAuthorizationResponse eidResponse = new Gson().fromJson(response, ElectronicIdAuthorizationResponse.class);
                String electronicIdAuthorization = eidResponse.getAuthorization();
                startVideoId(
                        electronicIdAuthorization,
                        authLanguage,
                        documentType,
                        REQUEST_CODE_ELECTRONIC_ID_ACTIVITY
                );
            }
        }).execute(getAuthorizationUrl(), new Gson().toJson(request));
    }

    private String getAuthorizationUrl() {
        return baseUrl + ELECTRONIC_ID_AUTHORIZATION_URL;
    }

    private void launchDataCall(String videoId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_AUTHORIZATION, getBearerHeader());

        ElectronicIdVideoDataRequest request = new ElectronicIdVideoDataRequest();
        request.setVideoId(videoId);

        new Api.ApiCallGet(headers, new Api.ApiCallListener() {
            @Override
            public void success(String response) {
                closeWithSuccess(response);
            }
        }).execute(getVideoDataUrl(), new Gson().toJson(request));
    }

    private String getVideoDataUrl() {
        return baseUrl + ELECTRONIC_ID_VIDEO_DATA_URL;
    }

    private void closeWithSuccess(String message) {
        Intent data = new Intent();
        data.putExtra(RESULT_MESSAGE, message);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void closeWithFailure(String message) {
        Intent data = new Intent();
        data.putExtra(RESULT_MESSAGE, message);
        setResult(Activity.RESULT_CANCELED, data);
        finish();
    }

    private void startVideoId(String electronicIdAuthorization,
                              String language,
                              Integer documentType,
                              Integer requestCode) {
        try {
            Environment environment = new Environment(
                    new URL(baseUrl),
                    electronicIdAuthorization);

            Intent intent = new Intent(this, VideoIDActivity.class);
            intent.putExtra(
                    VIDEOID_ARGS_ENVIRONMENT,
                    environment);
            intent.putExtra(
                    VIDEOID_ARGS_INTENT_DOCUMENT_TYPE,
                    documentType != null ? documentType :DEFAULT_AUTH_DOCUMENT_TYPE
            );
            intent.putExtra(
                    VIDEOID_ARGS_INTENT_MIN_SIMILARITY,
                    minSimilarity
            );
            intent.putExtra(
                    VIDEOID_ARGS_INTENT_VIDEO_MODE,
                    videoMode
            );
            intent.putExtra(
                    VIDEOID_ARGS_INTENT_LANGUAGE,
                    language != null ? language :DEFAULT_AUTH_LANGUAGE
            );

            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ELECTRONIC_ID_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String videoId = data.getStringExtra(VideoIDActivity.INTENT_RESULT_OK);
                closeWithSuccess(videoId);
                // launchDataCall(videoId);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                closeWithFailure("Error scanning document, please try again");
            }
        }
    }
}
