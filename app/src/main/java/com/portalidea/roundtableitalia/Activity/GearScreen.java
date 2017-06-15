package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 6/15/2016.
 */
public class GearScreen extends Activity {
    ImageView ivProtalIdea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gear);

        ivProtalIdea = (ImageView) findViewById(R.id.ivPortalIdea);
        ivProtalIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GearScreen.this, Web.class);
                intent.putExtra("URL", "http://www.portalidea.it");
                startActivity(intent);

            }
        });

//        ActionBar actinBar = getSupportActionBar();
//        actinBar.setDisplayHomeAsUpEnabled(true);
//        actinBar.setHomeAsUpIndicator(R.drawable.white_back_arrow);
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Credits");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        actinBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(GearScreen.this, R.color.actionbarblue)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
