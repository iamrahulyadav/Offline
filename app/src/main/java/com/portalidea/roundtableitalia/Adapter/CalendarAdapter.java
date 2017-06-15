package com.portalidea.roundtableitalia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Activity.CalendarDetail;
import com.portalidea.roundtableitalia.Model.CalendarDetails;
import com.portalidea.roundtableitalia.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Rujul on 27-Dec-15.
 */
public class CalendarAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<CalendarDetails> calendarDetailsModelsArray;
    ArrayList<CalendarDetails> calendarDetails;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");

    public CalendarAdapter(Context context, ArrayList<CalendarDetails> calendarDetailsModelsArray, String dateStr, int forMonth) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        calendarDetails = new ArrayList<>();
        if (forMonth == 0) {
            for (int i = 0; i < calendarDetailsModelsArray.size(); i++) {
                String DateString = parseDateToMM(dateStr);
                String DateStartDate = parseDateToMM(calendarDetailsModelsArray.get(i).getCalStartDate());
                String DateEndDate = parseDateToMM(calendarDetailsModelsArray.get(i).getCalEndDate());
                try {
                    Date date1 = sdf.parse(DateString);
                    Date dateStart = sdf.parse(DateStartDate);
                    Date dateEnd = sdf.parse(DateEndDate);
                    if (date1.equals(dateEnd) || date1.equals(dateStart)) {
                        calendarDetails.add(calendarDetailsModelsArray.get(i));
                    } else if (date1.before(dateEnd) && date1.after(dateStart)) {
                        calendarDetails.add(calendarDetailsModelsArray.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                for (int i = 0; i < calendarDetailsModelsArray.size(); i++) {
                    String DateString = parseDateToMM(dateStr);
                    String DateStartDate = parseDateToMM(calendarDetailsModelsArray.get(i).getCalStartDate());
                    String DateEndDate = parseDateToMM(calendarDetailsModelsArray.get(i).getCalEndDate());
                    Date date = sdf1.parse(DateString);
                    Date date1 = sdf1.parse(DateStartDate);
                    Date dateEnd = sdf1.parse(DateEndDate);
                    if (date1.equals(date) || dateEnd.equals(date)) {
                        calendarDetails.add(calendarDetailsModelsArray.get(i));
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    @Override
    public int getCount() {
        return calendarDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.adapter_calenda, parent, false);

        CalendarDetails calendarDetailss = calendarDetails.get(position);

        TextView dateTv = (TextView) v.findViewById(R.id.adapter_calendar_date);
        TextView titleTv = (TextView) v.findViewById(R.id.adapter_calendar_title);
        TextView organizerTv = (TextView) v.findViewById(R.id.adapter_calendar_organizer);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCalDetails = new Intent(context, CalendarDetail.class);

                intentCalDetails.putExtra("calTitleStr", calendarDetails.get(position).getCalTitle());
                intentCalDetails.putExtra("calImgStr", calendarDetails.get(position).getCalImage());
                intentCalDetails.putExtra("calorgStr", calendarDetails.get(position).getCalOrganization());
                String startDate = calendarDetails.get(position).getCalStartDate();
                String EndDate = calendarDetails.get(position).getCalEndDate();
                intentCalDetails.putExtra("calstartDateStr", startDate);
                intentCalDetails.putExtra("calEndDateStr", EndDate);
                intentCalDetails.putExtra("calSitoStr", calendarDetails.get(position).getCalSito());
                intentCalDetails.putExtra("calLocationStr", calendarDetails.get(position).getCalLocation());

                context.startActivity(intentCalDetails);

            }
        });

        titleTv.setTextColor(ContextCompat.getColor(context, R.color.blue));

        dateTv.setText(parseDateToddMMyyyy(calendarDetailss.getCalStartDate()) + " - " + parseDateToddMMyyyy(calendarDetailss.getCalEndDate()));
        titleTv.setText(calendarDetailss.getCalTitle());
        organizerTv.setText(calendarDetailss.getCalOrganization());

        return v;
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

    public String parseDateToMM(String time) {
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
