package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by archirayan on 6/30/2016.
 */
public class ForgetPassword extends Activity implements View.OnClickListener {
    private EditText emailEdt;
    private Button send;
    public String email;
    public Utils utils;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        (findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        emailEdt = (EditText) findViewById(R.id.activity_forget_email);
        send = (Button) findViewById(R.id.activity_forget_submit);
        utils = new Utils(ForgetPassword.this);
        init();
    }

    private void init() {

        send.setOnClickListener(this);

    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_forget_submit:
                email = emailEdt.getText().toString();
                if (utils.isConnectingToInternet()) {
                    if (checkEmail(email)) {
                        new forgetPassword().execute();
                    } else {
                        Toast.makeText(ForgetPassword.this, R.string.warningallfieldrequires, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    utils.connectiondetect();
                }
                break;
        }
    }


    private class forgetPassword extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ForgetPassword.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.getResponseofGet(Constant.BASE_URL + "member_forgot_pwd.php?email=" + email);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {

                    String message = object.getString("msg");
                    String id = object.getString("id");
                    String password = object.getString("password");
                    Toast.makeText(ForgetPassword.this, message, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(ForgetPassword.this, OTPActivity.class);
                    in.putExtra("id", id);
                    in.putExtra("password", password);
                    finish();
                    startActivity(in);

                } else {
                    Toast.makeText(ForgetPassword.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
