package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.R;
import com.bumptech.glide.Glide;

/**
 * Created by archirayan on 6/27/2016.
 */
public class RoundTableItaliaActivity extends Activity implements View.OnClickListener {
    TextView nameTv, zonaTv, tavolaTv, chartermeetingTv, tavolamadrinaTv, gemellateTv, riunioniTv, ritrovoTv, webTv, emailTv, facebookTv, presidentTv;
    ImageView image;
    public String ritrovo, lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_table_italia);
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Italia");
        image = (ImageView) findViewById(R.id.activity_round_table_italia_image);
        nameTv = (TextView) findViewById(R.id.activity_round_table_italia_name);
        zonaTv = (TextView) findViewById(R.id.activity_round_table_italia_zona);
        tavolaTv = (TextView) findViewById(R.id.activity_round_table_italia_tavola);
        chartermeetingTv = (TextView) findViewById(R.id.activity_round_table_italia_charter);
        gemellateTv = (TextView) findViewById(R.id.activity_round_table_italia_gemellate);
        riunioniTv = (TextView) findViewById(R.id.activity_round_table_italia_riunioni);
        ritrovoTv = (TextView) findViewById(R.id.activity_round_table_italia_ritrova);
        webTv = (TextView) findViewById(R.id.activity_round_table_italia_web);
        emailTv = (TextView) findViewById(R.id.activity_round_table_italia_email);
        facebookTv = (TextView) findViewById(R.id.activity_round_table_italia_facebook);
        presidentTv = (TextView) findViewById(R.id.activity_round_table_italia_president);
        tavolamadrinaTv = (TextView) findViewById(R.id.activity_round_table_italia_tavola_madrina);

        init();
    }

    private void init() {


        if (getIntent().getExtras() != null) {
            lat = getIntent().getExtras().getString("lat");
            lng = getIntent().getExtras().getString("lng");
            String numero = getIntent().getExtras().getString("numero");
            String nome = getIntent().getExtras().getString("nome");
            String zona = getIntent().getExtras().getString("zona");
            String tavola_sciolta = getIntent().getExtras().getString("tavola_sciolta");
            String madrina = getIntent().getExtras().getString("madrina");
            String charter_meeting = getIntent().getExtras().getString("charter_meeting");
            String gemellate = getIntent().getExtras().getString("gemellate");
            String riunioni = getIntent().getExtras().getString("riunioni");
            ritrovo = getIntent().getExtras().getString("ritrovo");
            String email = getIntent().getExtras().getString("email");
            String web = getIntent().getExtras().getString("web");
            String facebook = getIntent().getExtras().getString("facebook");
            String twitter = getIntent().getExtras().getString("twitter");
            String cap = getIntent().getExtras().getString("cap");
            String fax = getIntent().getExtras().getString("fax");
            String foto = getIntent().getExtras().getString("foto");
            String editor = getIntent().getExtras().getString("editor");
            String note = getIntent().getExtras().getString("note");
            String President = getIntent().getExtras().getString("President");
            nameTv.setText("Round Table " + numero + " " + nome);
            zonaTv.setText(Html.fromHtml("<b> Zona" + "</b>" + " |" + zona));
            tavolaTv.setText(Html.fromHtml("<b> Tavola" + "</b>" + " |" + numero));
            chartermeetingTv.setText(Html.fromHtml("<b> Charter Meeting " + "</b>" + " |" + charter_meeting));
            tavolamadrinaTv.setText(Html.fromHtml("<b> Tavola Madrina " + "</b>" + " |" + madrina));
            gemellateTv.setText(Html.fromHtml("<b> Gemellate " + "</b>" + " |" + gemellate));
            riunioniTv.setText(Html.fromHtml("<b> Riunioni " + "</b>" + " |" + riunioni));
            ritrovoTv.setText(Html.fromHtml("<b> Ritrovo " + "</b>" + " |<font color=\"#3A77E6\"><a>" + ritrovo + "</a></font>"));

            webTv.setMovementMethod(LinkMovementMethod.getInstance());
            webTv.setText(Html.fromHtml("<b> Web " + "</b>" + " |<font color=\"#3A77E6\"><a> " + web + "</a></font>"), TextView.BufferType.SPANNABLE);

            emailTv.setMovementMethod(LinkMovementMethod.getInstance());
            emailTv.setText(Html.fromHtml("<b> Email " + "</b>" + " |<font color=\"#3A77E6\"><a href=\"mailto:" + email + "\"> " + email + "</a></font>"), TextView.BufferType.SPANNABLE);

            facebookTv.setText(Html.fromHtml("<b> Facebook " + "</b>" + " |" + facebook));
            presidentTv.setText(Html.fromHtml("<b> President " + "</b>" + " |" + President));

            String imageURL = (/*"http://www.roundtable.it/uploads/tx_annuario/" +*/ foto).replace(" ", "%20");
            if (imageURL.substring(imageURL.length() - 3, imageURL.length()).equalsIgnoreCase("gif")) {
                Glide.with(RoundTableItaliaActivity.this)
                        .load(imageURL).asGif()
                        .placeholder(R.drawable.ic_placeholder).crossFade()
                        .into(image);
            } else {
                Glide.with(RoundTableItaliaActivity.this)
                        .load(imageURL)
                        .placeholder(R.drawable.ic_placeholder).crossFade()
                        .into(image);
            }
        }
        ritrovoTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_round_table_italia_ritrova:
                if (!lat.equalsIgnoreCase("")) {

                    Intent in = new Intent(RoundTableItaliaActivity.this, MapActivity.class);
                    in.putExtra("lat",lat);
                    in.putExtra("lng",lng);
                    startActivity(in);

                } else {

                    Toast.makeText(RoundTableItaliaActivity.this, "Invalid Address", Toast.LENGTH_SHORT).show();

                }

//                Utils utils = new Utils(RoundTableItaliaActivity.this);
//                String data = ritrovo;
//
//                String dataa = data.substring(0, data.lastIndexOf("-"));
//                String[] data1 = dataa.split("-");
////                Log.d("DAATA", Data[0]);
//////                Log.d("Array", "" + data1);
//                String one = String.valueOf(data1[0]);
//                String two = String.valueOf(data1[1]);
//////                data = one + " " + two;
//                LatLng latLng = utils.getLocationFromAddress(RoundTableItaliaActivity.this, one + " " + two);
//                if (latLng != null) {
//                    Log.d("Address", "" + data + " " + latLng.latitude);
//                } else {
//                    Toast.makeText(RoundTableItaliaActivity.this, "null", Toast.LENGTH_SHORT).show();
//                }
//                location.setLatitude(latitude);
//                location.setLongitude(longitude);


                break;
        }
    }
}
