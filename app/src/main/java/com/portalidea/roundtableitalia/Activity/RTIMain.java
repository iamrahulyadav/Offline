package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Fragment.AddContact;
import com.portalidea.roundtableitalia.Fragment.AimsObjects;
import com.portalidea.roundtableitalia.Fragment.Chi_siamo;
import com.portalidea.roundtableitalia.Fragment.CosaFragment;
import com.portalidea.roundtableitalia.Fragment.DoveSiamo;
import com.portalidea.roundtableitalia.Fragment.RoundTableInternational;
import com.portalidea.roundtableitalia.Fragment.RoundTableItalian;
import com.portalidea.roundtableitalia.Fragment.ScopiRound;
import com.portalidea.roundtableitalia.R;

import java.util.ArrayList;

/**
 * Created by archirayan on 5/23/2016.
 */
public class RTIMain extends Activity {
    public static Button chiSiamoBtn, cosaFacciamoBtn, doveSiamoBtn, contattiBtn;
    public static ArrayList<String> screenCountArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_table);
//
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Italia");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                if (screenCountArray.size() == 1) {
                    Intent in = new Intent(RTIMain.this,MainActivity.class);
                    finish();
                    startActivity(in);
                } else {
                    int lastCount = screenCountArray.size() - 1;
                    screenCountArray.remove(lastCount);
                    int previousCount = screenCountArray.size() - 1;
                    String screenCount = screenCountArray.get(previousCount);
                    switch (screenCount) {
                        case "1":
                            doveSiamo();
                            break;
                        case "2":
                            chiSiamo();
                            break;
                        case "3":
                            cosaFacciamo();
                            break;
                        case "4":
                            contatti();
                            break;
                        case "3.1":
                            RoundTableInterNational();
                            break;
                        case "3.2":
                            RoundTableItlia();
                            break;
                        case "1.1":
                            chiSiamoScopi();
                            break;
                        case "1.2":
                            chiSiamoAimsObject();
                            break;
                        case "3.2.1":
                            RoundTableItlia();
                            break;
                    }
                }
            }
        });

        chiSiamoBtn = (Button) findViewById(R.id.activity_round_table_chi_siamo);
        cosaFacciamoBtn = (Button) findViewById(R.id.activity_round_table_cosa);
        doveSiamoBtn = (Button) findViewById(R.id.activity_round_table_dove);
        contattiBtn = (Button) findViewById(R.id.activity_round_table_contatti);
        setTextAndBackground(1);
        Fragment myf;
        screenCountArray = new ArrayList<>();
        screenCountArray.add("2");
        setTextAndBackground(1);
        Chi_siamo myf1 = new Chi_siamo();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf1);
        transaction.commit();

        doveSiamoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenCountArray.clear();
                screenCountArray.add("1");
                doveSiamo();
            }
        });
        chiSiamoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenCountArray.clear();
                screenCountArray.add("2");
                chiSiamo();

            }
        });

        cosaFacciamoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenCountArray.clear();
                screenCountArray.add("3");
                cosaFacciamo();

            }
        });

        contattiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenCountArray.clear();
                screenCountArray.add("4");
                contatti();
            }
        });

    }

    private void RoundTableInterNational() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Dove siamo");
        setTextAndBackground(3);
        RoundTableInternational myf = new RoundTableInternational();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf);
        transaction.commit();
    }

    private void RoundTableItlia() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Dove siamo");
        setTextAndBackground(3);
        RoundTableItalian myf = new RoundTableItalian();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf);
        transaction.commit();
    }

    private void chiSiamoScopi() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Chi siamo");
        setTextAndBackground(1);
        ScopiRound scopiRound = new ScopiRound();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (getFragmentManager().findFragmentById(R.id.framelayout) != null) {
            transaction.replace(R.id.framelayout, scopiRound);
        } else {
            transaction.add(R.id.framelayout, scopiRound);
        }
        transaction.commit();
    }

    private void chiSiamoAimsObject() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Chi siamo");
        setTextAndBackground(1);
        AimsObjects fragmentAimsObjects = new AimsObjects();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (getFragmentManager().findFragmentById(R.id.framelayout) != null) {
            transaction.replace(R.id.framelayout, fragmentAimsObjects);
        } else {
            transaction.add(R.id.framelayout, fragmentAimsObjects);
        }
        transaction.commit();
    }


    private void contatti() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Contatti");
        setTextAndBackground(4);
        AddContact myf = new AddContact();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf);
        transaction.commit();
    }

    private void cosaFacciamo() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Cosa facciamo");
        setTextAndBackground(2);
        CosaFragment myf = new CosaFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf);
        transaction.commit();
    }

    private void chiSiamo() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Chi siamo");
        setTextAndBackground(1);
        Chi_siamo myf = new Chi_siamo();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf);
        transaction.commit();
    }

    private void doveSiamo() {
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Dove siamo");
        setTextAndBackground(3);
        DoveSiamo myf = new DoveSiamo();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, myf);
        transaction.commit();
    }

    public void setTextAndBackground(int i) {

        if (i == 1) {

            doveSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            chiSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.white));
            cosaFacciamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            contattiBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));

            doveSiamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            chiSiamoBtn.setBackgroundResource(R.drawable.corner_yellow);
            cosaFacciamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            contattiBtn.setBackgroundResource(R.drawable.darkgray_background);

        } else if (i == 2) {

            doveSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            chiSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            cosaFacciamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.white));
            contattiBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));

            doveSiamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            chiSiamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            cosaFacciamoBtn.setBackgroundResource(R.drawable.corner_yellow);
            contattiBtn.setBackgroundResource(R.drawable.darkgray_background);


        } else if (i == 3) {

            doveSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.white));
            chiSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            cosaFacciamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            contattiBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));

            doveSiamoBtn.setBackgroundResource(R.drawable.corner_yellow);
            chiSiamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            contattiBtn.setBackgroundResource(R.drawable.darkgray_background);
            cosaFacciamoBtn.setBackgroundResource(R.drawable.darkgray_background);

        } else if (i == 4) {

            doveSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            chiSiamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            cosaFacciamoBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.bluetext));
            contattiBtn.setTextColor(ContextCompat.getColor(RTIMain.this, R.color.white));

            doveSiamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            chiSiamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            cosaFacciamoBtn.setBackgroundResource(R.drawable.darkgray_background);
            contattiBtn.setBackgroundResource(R.drawable.corner_yellow);

        }

    }

    @Override
    public void onBackPressed() {

    }
}
