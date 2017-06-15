package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 6/30/2016.
 */
public class OTPActivity extends Activity implements View.OnClickListener {
    private EditText otpEdt;
    private Button send;
    public String otp, password, id;
    public Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpEdt = (EditText) findViewById(R.id.activity_otp_otp);
        send = (Button) findViewById(R.id.activity_otp_submit);
        utils = new Utils(OTPActivity.this);
        init();
    }

    private void init() {

        if (getIntent().getExtras() != null) {

            id = getIntent().getExtras().getString("id");
            password = getIntent().getExtras().getString("password");
        }

        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_otp_submit:
                otp = otpEdt.getText().toString();
                if (password.equalsIgnoreCase(otp)) {

                    Intent in = new Intent(OTPActivity.this, ResetPassword.class);
                    in.putExtra("id", id);
                    finish();
                    startActivity(in);

                } else {
                    Toast.makeText(OTPActivity.this, "OTP MISS-MATCH.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


}
