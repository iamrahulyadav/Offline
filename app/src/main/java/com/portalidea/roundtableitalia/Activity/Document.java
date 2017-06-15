package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 5/27/2016.
 */
public class Document extends Activity {
    LinearLayout FondazioneRoundTableLinear, statusRoundLinear,InvestituradiunnuovoTablerLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        ((TextView) findViewById(R.id.actionBarTitle)).setText("Documenti");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        statusRoundLinear = (LinearLayout) findViewById(R.id.StatusRoundLinear);
        FondazioneRoundTableLinear = (LinearLayout) findViewById(R.id.FondazioneRoundTableLinear);
        InvestituradiunnuovoTablerLinear = (LinearLayout)findViewById(R.id.InvestituradiunnuovoTablerLinear);
        FondazioneRoundTableLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Document.this, DocumentLoad.class);
//                in.putExtra("URL", "file:///android_asset/abc.pdf");
                in.putExtra("URL", Constant.BASE_URL+"pdf/abc.pdf");
                startActivity(in);
            }
        });

        statusRoundLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Document.this, DocumentLoad.class);
                in.putExtra("URL", Constant.BASE_URL+"pdf/StatutoFondazioneRoundTableItaliaOnlus.pdf");
                startActivity(in);
            }
        });

        InvestituradiunnuovoTablerLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Document.this, DocumentLoad.class);
                in.putExtra("URL", Constant.BASE_URL+"pdf/INVESTITURA_DI_UN_NUOVO_TABLER.pdf");
                startActivity(in);
            }
        });


    }
}
