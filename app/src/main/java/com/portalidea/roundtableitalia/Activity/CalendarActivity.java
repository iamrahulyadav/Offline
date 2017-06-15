package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.portalidea.roundtableitalia.Adapter.CalendarAdapter;
import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.CalendarDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by archirayan on 5/28/2016.
 */
public class CalendarActivity extends Activity implements View.OnClickListener {
    Utils utils;
    CompactCalendarView compactCalendarView;
    ImageView previousMonth, nextMonth;
    TextView monthNameTv;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    ListView listView;
    ArrayList<CalendarDetails> calendarDetailsModelsArray;
    private DatabaseHelper databaseHelper;
    private ConnectionDetector cd;
    String curDate;
    private Calendar currentCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ((TextView) findViewById(R.id.actionBarTitle)).setText("Calendario");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        cd = new ConnectionDetector(CalendarActivity.this);
        databaseHelper = new DatabaseHelper(CalendarActivity.this);
        listView = (ListView) findViewById(R.id.activity_calendar_list);
        monthNameTv = (TextView) findViewById(R.id.activity_calendar_monthname);
        previousMonth = (ImageView) findViewById(R.id.activity_calendar_previousmonth);
        nextMonth = (ImageView) findViewById(R.id.activity_calendar_nextmonth);
        utils = new Utils(CalendarActivity.this);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        calendarDetailsModelsArray = new ArrayList<>();
        curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        init();
    }


    private void init() {

        if (cd.isConnectingToInternet()) {

            new getData().execute();

        } else {

            calendarDetailsModelsArray = databaseHelper.getAllCalendarDetails();
            listView.setAdapter(new CalendarAdapter(CalendarActivity.this, calendarDetailsModelsArray, curDate, 1));

        }

        monthNameTv.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                String s1 = dateFormatForDate.format(dateClicked);
                listView.setAdapter(new CalendarAdapter(CalendarActivity.this, calendarDetailsModelsArray, s1, 0));

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Date date = compactCalendarView.getFirstDayOfCurrentMonth();
                String s1 = dateFormatForDate.format(date);
                listView.setAdapter(new CalendarAdapter(CalendarActivity.this, calendarDetailsModelsArray, s1, 1));
                monthNameTv.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
            }
        });
        nextMonth.setOnClickListener(this);
        previousMonth.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_calendar_nextmonth:
                compactCalendarView.showNextMonth();
                break;
            case R.id.activity_calendar_previousmonth:
                compactCalendarView.showPreviousMonth();
                break;
        }
    }

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(CalendarActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.MakeServiceCall(Constant.BASE_URL + "get_rss.php");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


                if (object.has("item")) {

                    JSONObject dataObject = object.optJSONObject("item");

                    if (dataObject != null) {

                        CalendarDetails calendarDetails = new CalendarDetails();

                        calendarDetails.setCalTitle(dataObject.getString("title"));
                        calendarDetails.setCalStartDate(dataObject.getJSONObject("content").getString("startdate"));
                        calendarDetails.setCalEndDate(dataObject.getJSONObject("content").getString("enddate"));
                        calendarDetails.setCalOrganization(dataObject.getJSONObject("content").getString("organizer"));
                        calendarDetails.setCalImage(dataObject.getString("img"));
                        try {
                            Date date1 = sdf.parse(dataObject.getJSONObject("content").getString("startdate"));
                            for (int k = 1; k < 100; k++) {
                                Calendar c = Calendar.getInstance();
                                c.setTime(date1);
                                if (k != 1) {
                                    c.add(Calendar.DATE, k);
                                }
                                if (sdf.parse(sdf.format(c.getTime())).equals(sdf.parse(parseDateToDATE(dataObject.getJSONObject("content").getString("enddate")))) || sdf.parse(sdf.format(c.getTime())).equals(sdf.parse(parseDateToDATE(dataObject.getJSONObject("content").getString("startdate"))))) {
                                    Event ev1 = new Event(Color.GREEN, c.getTimeInMillis());
                                    compactCalendarView.addEvent(ev1, true);
                                }

                                if (sdf.parse(sdf.format(c.getTime())).before(sdf.parse(parseDateToDATE(dataObject.getJSONObject("content").getString("enddate")))) && sdf.parse(sdf.format(c.getTime())).after(sdf.parse(parseDateToDATE(dataObject.getJSONObject("content").getString("startdate"))))) {
                                    Event ev1 = new Event(Color.GREEN, c.getTimeInMillis());
                                    compactCalendarView.addEvent(ev1, true);
                                }
                            }
//                            Event ev1 = new Event(Color.GREEN, date1.getTime());
//                            compactCalendarView.addEvent(ev1, true);
                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }


                        calendarDetailsModelsArray.add(calendarDetails);
                        //Do things with object.
                        databaseHelper.insertUserCalendarDetails(calendarDetailsModelsArray);
                    } else {

                        JSONArray array = object.optJSONArray("item");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject itemObject = array.getJSONObject(i);
                            CalendarDetails calendarDetails = new CalendarDetails();

                            calendarDetails.setCalTitle(itemObject.getString("title"));
                            calendarDetails.setCalStartDate(itemObject.getJSONObject("content").getString("startdate"));
                            calendarDetails.setCalEndDate(itemObject.getJSONObject("content").getString("enddate"));
                            calendarDetails.setCalOrganization(itemObject.getJSONObject("content").getString("organizer"));
                            calendarDetails.setCalImage(itemObject.getString("img"));
                            calendarDetails.setCalSito(itemObject.getString("link"));
                            calendarDetails.setCalLocation(itemObject.getJSONObject("content").getString("location"));
                            try {
                                Date date1 = sdf.parse(itemObject.getJSONObject("content").getString("startdate"));

                                for (int k = 1; k < 100; k++) {
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(date1);
                                    if (k != 1) {
                                        c.add(Calendar.DATE, k);
                                    }
                                    if (sdf.parse(sdf.format(c.getTime())).equals(sdf.parse(parseDateToDATE(itemObject.getJSONObject("content").getString("enddate")))) || sdf.parse(sdf.format(c.getTime())).equals(sdf.parse(parseDateToDATE(itemObject.getJSONObject("content").getString("startdate"))))) {
                                        Event ev1 = new Event(Color.GREEN, c.getTimeInMillis());
                                        compactCalendarView.addEvent(ev1, true);
                                    }

                                    if (sdf.parse(sdf.format(c.getTime())).before(sdf.parse(parseDateToDATE(itemObject.getJSONObject("content").getString("enddate")))) && sdf.parse(sdf.format(c.getTime())).after(sdf.parse(parseDateToDATE(itemObject.getJSONObject("content").getString("startdate"))))) {
                                        Event ev1 = new Event(Color.GREEN, c.getTimeInMillis());
                                        compactCalendarView.addEvent(ev1, true);
                                    }
                                }
//                            Event ev1 = new Event(Color.GREEN, date1.getTime());
//                            compactCalendarView.addEvent(ev1, true);
                            } catch (Exception e) {
                                Log.d("Exception", e.toString());
                            }
                            calendarDetailsModelsArray.add(calendarDetails);
                        }


                        databaseHelper.insertUserCalendarDetails(calendarDetailsModelsArray);

                        //Do things with array
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(CalendarActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            }

//            compactCalendarView.date
            listView.setAdapter(new CalendarAdapter(CalendarActivity.this, calendarDetailsModelsArray, curDate, 1));

        }
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

    public String parseDateToDATE(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "yyyy-MM-dd";
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
}
