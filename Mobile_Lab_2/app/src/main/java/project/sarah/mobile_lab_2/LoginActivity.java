package project.sarah.mobile_lab_2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by sarah on 11/2/17.
 */

public class LoginActivity extends AppCompatActivity {
    TextView tv, tv_forgot;
    EditText edit_id, edit_pw;
    Button btn_login, btn_FB;
    CallbackManager callbackManager;
    String st_un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "project.sarah.mobile_lab_1",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        tv=(TextView)findViewById(R.id.appName);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/billabong.ttf"));

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckCredentials(v);
            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.btn_fblogin);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });

        tv_forgot=(TextView)findViewById(R.id.tv_forgot);
        tv_forgot.setClickable(true);
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableString content = new SpannableString("Forgot your password? Click this");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                tv_forgot.setText(content);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void CheckCredentials(View v){
        EditText usernameCtrl = (EditText)findViewById(R.id.edit_id);
        EditText passwordCtrl = (EditText)findViewById(R.id.edit_pw);
        TextView errorText = (TextView)findViewById(R.id.err_txt);
        String username = usernameCtrl.getText().toString();
        String password = passwordCtrl.getText().toString();

        boolean ValidationFlag = false;
        if(!username.isEmpty() && !password.isEmpty() ){
            if(username.equals("admin") && password.equals("admin")){
                ValidationFlag = true;
                st_un=username;
            }
        }

        if(!ValidationFlag){
            errorText.setVisibility(View.VISIBLE);
        }
        else{
            reDirectToHomePage(v);
        }
    }

    public void reDirectToHomePage(View v){
        Intent redirect = new Intent(LoginActivity.this, ChooseActivity.class);
        redirect.putExtra("uname", st_un);
        finish();
        startActivity(redirect);
    }
}
