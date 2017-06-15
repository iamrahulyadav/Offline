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

/**
 * Created by archirayan on 6/30/2016.
 */
public class ResetPassword extends Activity implements View.OnClickListener {
    private EditText passwordEdt, conPasswordEdt;
    private Button send;
    public String password, conpassword, id;
    public Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repassword);
        passwordEdt = (EditText) findViewById(R.id.activity_repassword_password);
        conPasswordEdt = (EditText) findViewById(R.id.activity_repassword_repassword);
        send = (Button) findViewById(R.id.activity_repassword_submit);
        utils = new Utils(ResetPassword.this);
        init();
    }

    private void init() {

        if (getIntent().getExtras() != null) {

            id = getIntent().getExtras().getString("id");

        }

        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_repassword_submit:
                password = passwordEdt.getText().toString();
                conpassword = conPasswordEdt.getText().toString();

                if (password.equals(conpassword)) {

                    new setPassword().execute();

                } else {
                    Toast.makeText(ResetPassword.this, "PASSWORD NOT MATCH.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class setPassword extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(ResetPassword.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return utils.getResponseofGet(Constant.BASE_URL + "member_change_pwd.php?id=" + id + "&password=" + password);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {

                    String message = object.getString("msg");
                    Toast.makeText(ResetPassword.this, message, Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(ResetPassword.this, Login.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);

                } else {

                    String message = object.getString("msg");
                    Toast.makeText(ResetPassword.this, message, Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
