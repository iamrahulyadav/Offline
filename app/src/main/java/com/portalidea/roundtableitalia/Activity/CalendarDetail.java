package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.portalidea.roundtableitalia.Model.CalendarDetails;
import com.portalidea.roundtableitalia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by archi_info on 6/1/2016.
 */
public class CalendarDetail extends Activity {

    ImageView calImg;
    TextView TitleTv, DateTv, OrganizzatoreTv, SitoTv, QuandoTv;
    ArrayList<CalendarDetails> calendarDetailsModelsArray;
    String calTitleStr, calImgStr, calStartDateStr, calEndDateStr, calOrganizzatoreStr, calSitoStr, calQuandoStr;
    private Bundle calendarDetailsBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_details);

        ((TextView) findViewById(R.id.actionBarTitle)).setText("Calendario");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        calendarDetailsBundle = getIntent().getExtras();
        if (calendarDetailsBundle != null) {
            calTitleStr = (String) calendarDetailsBundle.get("calTitleStr");
            calImgStr = (String) calendarDetailsBundle.get("calImgStr");
            calOrganizzatoreStr = (String) calendarDetailsBundle.get("calorgStr");
            calStartDateStr = (String) calendarDetailsBundle.get("calstartDateStr");
            calEndDateStr = (String) calendarDetailsBundle.get("calEndDateStr");
            calSitoStr = (String) calendarDetailsBundle.get("calSitoStr");
            calQuandoStr = (String) calendarDetailsBundle.get("calLocationStr");
        }


        calImg = (ImageView) findViewById(R.id.activity_calendar_details_image);
        TitleTv = (TextView) findViewById(R.id.activity_calendar_details_title);
        DateTv = (TextView) findViewById(R.id.activity_calendar_details_dove);
        OrganizzatoreTv = (TextView) findViewById(R.id.activity_calendar_details_organizer);
        SitoTv = (TextView) findViewById(R.id.activity_calendar_details_sito);
        QuandoTv = (TextView) findViewById(R.id.activity_calendar_details_quando);

//        SitoTv.setText(calSitoStr);
        SitoTv.setMovementMethod(LinkMovementMethod.getInstance());
        SitoTv.setText(Html.fromHtml("<font color=\"#3A77E6\"><a>" + calSitoStr + "</a></font>"), TextView.BufferType.SPANNABLE);
        Log.e("Sito","Sito >>"+calSitoStr);
        DateTv.setText(calQuandoStr);
        TitleTv.setText(calTitleStr);
        QuandoTv.setText(parseDateToddMMyyyy(calStartDateStr) + " - " + parseDateToddMMyy(calEndDateStr));
        OrganizzatoreTv.setText(calOrganizzatoreStr);

        Glide.with(CalendarDetail.this)
                .load(calImgStr)
                .placeholder(R.drawable.ic_placeholder)
                .crossFade()
                .into(calImg);

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String parseDateToddMMyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
