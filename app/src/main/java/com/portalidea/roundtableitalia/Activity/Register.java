package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by archirayan on 6/27/2016.
 */
public class Register extends Activity implements View.OnClickListener {
    public EditText usernameEdt, passwordEdt, conPasswordEdt;
    public Button submitBtn;
    public String username, password, conPassword;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEdt = (EditText) findViewById(R.id.activity_register_email);
        passwordEdt = (EditText) findViewById(R.id.activity_register_password);
        conPasswordEdt = (EditText) findViewById(R.id.activity_register_confpassword);
        submitBtn = (Button) findViewById(R.id.activity_register_submit);
        utils = new Utils(Register.this);
        init();

    }

    private void init() {
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.actionBarTitle)).setText(R.string.register);
        submitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_submit:

                username = usernameEdt.getText().toString();

                if (checkEmail(username)) {
                    password = passwordEdt.getText().toString();
                    conPassword = conPasswordEdt.getText().toString();
                    if (password.length() > 2 && password.equalsIgnoreCase(conPassword)) {

                        new register().execute();

                    } else {
                        Toast.makeText(Register.this, "La password non è corrispondono.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Inserire un'email valida", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    private class register extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Register.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.getResponseofGet(Constant.BASE_URL + "member_register.php?email=" + username + "&password=" + password + "&android_reg_id=" + utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_GCM_REGID) + "&iphone_reg_id=");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("status").equalsIgnoreCase("true")) {
                    Toast.makeText(Register.this, "Controlla la Tua email e clicca sul link per confermare la registrazione.", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(Register.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

