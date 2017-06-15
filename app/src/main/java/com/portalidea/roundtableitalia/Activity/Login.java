package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.UserDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archirayan on 4/29/2016.
 */
public class Login extends Activity implements View.OnClickListener {

    public EditText userNameEdt, passwordEdt;
    public String userName, password;
    public Button submit, registerBtn, forgetBtn;
    public Utils utils;
    public ConnectionDetector cd;
    public SharedPreferences sp;
    public DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(Constant.Prefrence, Context.MODE_PRIVATE);
        userNameEdt = (EditText) findViewById(R.id.activity_login_email);
        passwordEdt = (EditText) findViewById(R.id.activity_login_password);
        registerBtn = (Button) findViewById(R.id.activity_login_register);
        forgetBtn = (Button) findViewById(R.id.activity_login_forget);
        cd = new ConnectionDetector(Login.this);
        submit = (Button) findViewById(R.id.activity_login_submit);
        utils = new Utils(Login.this);
        submit.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        forgetBtn.setOnClickListener(this);
        dbhelper = new DatabaseHelper(Login.this);
        ((ImageView) findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_register:
                Intent in = new Intent(Login.this, Register.class);
                startActivity(in);
                break;
            case R.id.activity_login_forget:
                Intent in1 = new Intent(Login.this, ForgetPassword.class);
                startActivity(in1);
                break;
            case R.id.activity_login_submit:
                if (userNameEdt.length() > 5) {

                    userName = userNameEdt.getText().toString();
                    password = passwordEdt.getText().toString();

                    if (cd.isConnectingToInternet()) {
                        new getLogin().execute();
                    } else {
                        ArrayList<UserDetails> array = dbhelper.getLogin(userName, password);
                        if (array.size() == 1) {
                            utils.setSharedPrefrence(Constant.UserId, array.get(0).getId());
                            Intent loginIntent = new Intent(Login.this, MainActivity.class);
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(loginIntent);
                            finish();

                        } else {
                            Toast.makeText(Login.this, R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private class getLogin extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Login.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.getResponseofGet(Constant.BASE_URL + "member_login.php?useremail=" + userName + "&password=" + password + "&android_reg_id=" + Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_GCM_REGID) + "&iphone_reg_id=");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    Toast.makeText(Login.this, R.string.Loginsucessfully, Toast.LENGTH_LONG).show();
                    utils.setSharedPrefrence(Constant.UserId, object.getJSONArray("data").getJSONObject(0).getString("id"));
                    Intent in = new Intent(Login.this, MainActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(Login.this, object.getString("msg")/*R.string.somethingwentwrong*/, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Login.this, R.string.somethingwentwrong, Toast.LENGTH_LONG).show();
            }
        }
    }
}
