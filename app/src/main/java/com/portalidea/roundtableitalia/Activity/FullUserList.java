package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Adapter.MyListAdapter;
import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.UserDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by archirayan on 5/14/2016.
 */
public class FullUserList extends Activity implements View.OnClickListener {

    java.util.Map<String, Integer> mapIndex;
    public ListView listView;
    public DatabaseHelper dbhelper;
    public ConnectionDetector cd;
    public Utils utils;
    private ArrayList<UserDetails> offlineDetails;
    ArrayList<String> name;
    public Button zoneListBtn, comitatoBtn;
    public LinearLayout zoneLinear, comitatoLinear;
    public RelativeLayout firstZonaRelative, secondZonaRelative, thirdZonaRelative, fourthZonaRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_list);

        ((TextView) findViewById(R.id.actionBarTitle)).setText(getString(R.string.app_name));
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        ActionBar actinBar = getSupportActionBar();
//        actinBar.setDisplayHomeAsUpEnabled(true);
//        actinBar.setHomeAsUpIndicator(R.drawable.white_back_arrow);
//        actinBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(FullUserList.this, R.color.actionbarblue)));


        listView = (ListView) findViewById(R.id.activity_full_list_list);

        utils = new Utils(FullUserList.this);
        dbhelper = new DatabaseHelper(FullUserList.this);
        cd = new ConnectionDetector(FullUserList.this);
        zoneListBtn = (Button) findViewById(R.id.activity_full_list_zone_rt);
        comitatoBtn = (Button) findViewById(R.id.activity_full_list_comitato);
        zoneLinear = (LinearLayout) findViewById(R.id.activity_full_list_linear_zone_list_linear);
        comitatoLinear = (LinearLayout) findViewById(R.id.activity_full_list_linear);

        firstZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_first);
        secondZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_second);
        thirdZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_third);
        fourthZonaRelative = (RelativeLayout) findViewById(R.id.activity_tavole_commit_fourth);

        firstZonaRelative.setOnClickListener(this);
        secondZonaRelative.setOnClickListener(this);
        thirdZonaRelative.setOnClickListener(this);
        fourthZonaRelative.setOnClickListener(this);

        init();
    }

    private void init() {
        zoneListBtn.setOnClickListener(this);
        comitatoBtn.setOnClickListener(this);
        if (cd.isConnectingToInternet()) {

            new getData().execute();

        } else {
            name = new ArrayList<String>();
            offlineDetails = dbhelper.getAllUser();
            for (int i = 0; i < offlineDetails.size(); i++) {
                name.add(offlineDetails.get(i).getName());
            }
            setUpList(name, offlineDetails);
        }
    }

    public void setUpList(ArrayList<String> name, ArrayList<UserDetails> arrayUserDetails) {
//        Collections.sort(name, new SortBasedOnName());
//        Collections.sort(arrayUserDetails, new Comparator<UserDetails>() {
//            @Override
//            public int compare(UserDetails lhs, UserDetails rhs) {
//                return lhs.getSurname().compareToIgnoreCase(rhs.getSurname());
//            }
//        });
        MyListAdapter adapter = new MyListAdapter(FullUserList.this, arrayUserDetails);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

//      setListViewHeightBasedOnChildren(stickyList);
//        getIndexList(name);
//        displayIndex();
    }

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name = new ArrayList<String>();
            offlineDetails = new ArrayList<UserDetails>();
            pd = new ProgressDialog(FullUserList.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = utils.getResponseofGet(Constant.BASE_URL + "api_user_withoutalphabet.php");

            JSONArray issueObj = null;

            try {
                issueObj = new JSONArray(response);
                for (int i = 0; i < issueObj.length(); i++) {
                    JSONObject object = issueObj.getJSONObject(i);
                    String id, social_class, areaid, zoneid,
                            clubid, nationalID, surname,
                            dob, tavola, city, profession, province,
                            tel_phone, name_wife, photo, inc_area,
                            Postal_Code, homephone, mobilephone, workphone, fax,
                            address_home, email, password, occupation, fb, lat, log,
                            twitter, linkedin, googleplus,
                            deviceid, status, club_city_name, is_enable, tavola1, tavola2;
                    String Name = object.getString("name").substring(0, 1).toUpperCase() + object.getString("name").substring(1);
//                        name.add(Name);
//                        Double latitude = Double.parseDouble(object.getString("lat"));
//                        Double longitude = Double.parseDouble(object.getString("log"));
//
//                        UserDetails details = new UserDetails();
//                        details.setName(Name);
//                        details.setId(object.getString("id"));
//                        details.setSurname(object.getString("surname"));
//                        details.setTavola(object.getString("tavola"));
//                        details.setCity(object.getString("city"));
//                        details.setProfession(object.getString("profession"));
                    photo = "http://www.roundtable.it/uploads/tx_annuario/" + object.getString("photo");
                    social_class = object.getString("social_class");
                    areaid = object.getString("areaid");
                    zoneid = object.getString("zoneid");
                    clubid = object.getString("clubid");
                    nationalID = object.getString("nationalID");
                    surname = object.getString("surname");
                    dob = object.getString("dob");
                    tavola = object.getString("tavola");
                    city = object.getString("city");
                    profession = object.getString("profession");
                    province = object.getString("province");
                    tel_phone = object.getString("tel_phone");
                    name_wife = object.getString("name_wife");
                    inc_area = object.getString("inc_area");
                    Postal_Code = object.getString("Postal_Code");
                    homephone = object.getString("tel_phone");
                    is_enable = String.valueOf(tel_phone.length());
//                        if (tel_phone.length() > 0) {
//                            is_enable = "1";
//                        } else {
//                            is_enable = "0";
//                        }
                    mobilephone = object.getString("mobilephone");
                    workphone = object.getString("workphone");
                    fax = object.getString("fax");
                    address_home = object.getString("address_home");
                    email = object.getString("email");
                    password = object.getString("password");
                    occupation = object.getString("occupation");
                    fb = object.getString("fb");
                    twitter = object.getString("twitter");
                    linkedin = object.getString("linkedin");
                    googleplus = object.getString("googleplus");
                    deviceid = object.getString("deviceid");
                    status = object.getString("status");
                    lat = object.getString("lat");
                    log = object.getString("log");
                    tavola1 = object.getString("tavola_1");
                    tavola2 = object.getString("tavola_2");
                    club_city_name = object.getString("club_city_name");
                    Double latitude = Double.parseDouble(object.getString("lat"));
                    Double longitude = Double.parseDouble(object.getString("log"));

                    surname = object.getString("surname").substring(0, 1).toUpperCase() + object.getString("surname").substring(1);
                    name.add(surname);
                    UserDetails details = new UserDetails();

                    details.setName(Name);
                    details.setId(object.getString("id"));
                    details.setSurname(surname);
                    details.setSocial_class(social_class);
                    details.setAreaid(areaid);
                    details.setZoneid(zoneid);
                    details.setClubid(clubid);
                    details.setNationalID(nationalID);
                    details.setDob(dob);
                    details.setTavola(tavola);
                    details.setCity(city);
                    details.setProfession(profession);
                    details.setProvince(province);
                    details.setTel_phone(tel_phone);
                    details.setName_wife(name_wife);
                    details.setPhoto(photo);
                    details.setInc_area(inc_area);
                    details.setPostal_Code(Postal_Code);
                    details.setHomephone(homephone);
                    details.setMobilephone(mobilephone);
                    details.setWorkphone(workphone);
                    details.setFax(fax);
                    details.setAddress_home(address_home);
                    details.setEmail(email);
                    details.setPassword(password);
                    details.setOccupation(occupation);
                    details.setFb(fb);
                    details.setLat(lat);
                    details.setLog(log);
                    details.setTwitter(twitter);
                    details.setLinkedin(linkedin);
                    details.setGoogleplus(googleplus);
                    details.setDeviceid(deviceid);
                    details.setStatus(status);
                    details.setClub_city_name(club_city_name);
                    details.setIs_enable(is_enable);
                    details.setTavola1(tavola1);
                    details.setTavola2(tavola2);
                    details.setIsRegisted(object.getString("mem_status"));
                    offlineDetails.add(details);

//                        if (latitude != 0.0) {
//                            Location location = new Location("List");
//                            location.setLatitude(latitude);
//                            location.setLongitude(longitude);
//
////                            if (curlocation != null) {
////
////                                float distance = location.distanceTo(curlocation);
////
////                            }
//
////                            OverlayItem object1 = new OverlayItem(
////                                    Name + " " + surname, /*object.getString()*/Name, new GeoPoint(latitude, longitude));
////                            Drawable newMarker = ContextCompat.getDrawable(FullUserList.this, R.drawable.ic_marker);
////                            object1.setMarker(newMarker);
//                        }

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("vvvv", e.toString());
            }
            dbhelper.insertUserList(offlineDetails);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ArrayCount", "" + offlineDetails.size());
            setUpList(name, offlineDetails);
            pd.dismiss();
        }

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

//    private void displayIndex() {
//        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
//        TextView textView;
//        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
//        for (String index : indexList) {
//            textView = (TextView) getLayoutInflater().inflate(
//                    R.layout.side_index_item, null);
//            textView.setText(index);
//            textView.setOnClickListener(this);
//            if (indexLayout != null) {
//                indexLayout.addView(textView);
//            }
//        }
//    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(FullUserList.this, ZoneDetails.class);
        if (view.getId() == R.id.activity_full_list_zone_rt) {
            zoneLinear.setVisibility(View.VISIBLE);
            comitatoLinear.setVisibility(View.GONE);

            zoneListBtn.setBackgroundResource(R.drawable.corner_yellow);
            comitatoBtn.setBackgroundResource(R.drawable.darkgray_background);

            zoneListBtn.setTextColor(ContextCompat.getColor(FullUserList.this, R.color.white));
            comitatoBtn.setTextColor(ContextCompat.getColor(FullUserList.this, R.color.bluetext));

        } else if (view.getId() == R.id.activity_full_list_comitato) {
            zoneLinear.setVisibility(View.GONE);
            comitatoLinear.setVisibility(View.VISIBLE);

            comitatoBtn.setBackgroundResource(R.drawable.corner_yellow);
            zoneListBtn.setBackgroundResource(R.drawable.darkgray_background);

            zoneListBtn.setTextColor(ContextCompat.getColor(FullUserList.this, R.color.bluetext));
            comitatoBtn.setTextColor(ContextCompat.getColor(FullUserList.this, R.color.white));

        } else if (view.getId() == R.id.activity_tavole_commit_fourth) {
            in.putExtra("Zone", "4");
            startActivity(in);
        } else if (view.getId() == R.id.activity_tavole_commit_third) {
            in.putExtra("Zone", "3");
            startActivity(in);
        } else if (view.getId() == R.id.activity_tavole_commit_second) {
            in.putExtra("Zone", "2");
            startActivity(in);
        } else if (view.getId() == R.id.activity_tavole_commit_first) {
            in.putExtra("Zone", "1");
            startActivity(in);
        } else {
            TextView selectedIndex = (TextView) view;
            listView.setSelection(mapIndex.get(selectedIndex.getText()));
        }
    }


    private void getIndexList(ArrayList<String> fruits) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < fruits.size(); i++) {
            String fruit = fruits.get(i);
            String index = fruit.substring(0, 1);
            if (mapIndex.get(index) == null) {
                mapIndex.put(index, i);
            }
        }
    }

//    public class SortBasedOnName implements Comparator {
//        public int compare(Object o1, Object o2) {
//            String dd1 = (String) o1;// where FBFriends_Obj is your object class
//            String dd2 = (String) o2;
//            return dd1.compareToIgnoreCase(dd2);//where uname is field name
//        }
//    }


}
