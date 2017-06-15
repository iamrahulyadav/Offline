package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 6/11/2016.
 */
public class TavoleCommit extends Activity implements View.OnClickListener {

    RelativeLayout firstZonaRelative, secondZonaRelative, thirdZonaRelative, fourthZonaRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tavole_commit);


        ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Italia");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        firstZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_first);
        secondZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_second);
        thirdZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_third);
        fourthZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_fourth);

        firstZonaRelative.setOnClickListener(this);
        secondZonaRelative.setOnClickListener(this);
        thirdZonaRelative.setOnClickListener(this);
        fourthZonaRelative.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent in = new Intent(TavoleCommit.this, ZoneDetails.class);
        switch (v.getId()) {

            case R.id.activity_tavole_commit_fourth:
                in.putExtra("Zone", "4");
                startActivity(in);
                break;
            case R.id.activity_tavole_commit_third:
                in.putExtra("Zone", "3");
                startActivity(in);
                break;
            case R.id.activity_tavole_commit_second:
                in.putExtra("Zone", "2");
                startActivity(in);
                break;

            case R.id.activity_tavole_commit_first:
                in.putExtra("Zone", "1");
                startActivity(in);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
