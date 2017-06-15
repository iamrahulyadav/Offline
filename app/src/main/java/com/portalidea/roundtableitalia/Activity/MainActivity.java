package com.portalidea.roundtableitalia.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationServices;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.UserDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LocationManager locationManager;
    String provider;

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int PERMISSION_FINE_LOCATION = 10;
    public TextView loginTv;
    public ImageView imageView;
    public String userId;
    LinearLayout searchLinear, taveloLinear, rtiLinear, calendarioLinear, documentLinear, loginLinear;
    Utils utils;
    ImageView searchImage, taveloImage, calendarioImage, documentImage, gearImage;
    Double latitude, longitude;
    private String gcmRegId;
    private GoogleCloudMessaging gcm;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ArrayList<UserDetails> offlineDetails;
    public DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getIntent().getExtras() != null) {
//            String urlString = getIntent().getExtras().getString("Data");
//            if (urlString != null) {
//                if (urlString.length() > 2) {
//                    if (urlString.startsWith("http")) {
//                        String url = urlString;
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        startActivity(i);
//                    } else {
//                        String url = "http://" + urlString;
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        startActivity(i);
//                    }
//                }
//            }
//        }

        setContentView(R.layout.content_main);


        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }
        dbhelper = new DatabaseHelper(MainActivity.this);
        utils = new Utils(MainActivity.this);
        imageView = (ImageView) findViewById(R.id.activity_main_imageView);
        searchLinear = (LinearLayout) findViewById(R.id.content_main_search);
        gearImage = (ImageView) findViewById(R.id.activity_main_gear);
        taveloLinear = (LinearLayout) findViewById(R.id.content_main_tavole);
        rtiLinear = (LinearLayout) findViewById(R.id.main_rti_linear);
        calendarioLinear = (LinearLayout) findViewById(R.id.content_main_calendario);
        documentLinear = (LinearLayout) findViewById(R.id.content_main_document);
        loginLinear = (LinearLayout) findViewById(R.id.content_main_login);
        loginTv = (TextView) findViewById(R.id.content_mian_login);
        calendarioImage = (ImageView) findViewById(R.id.content_main_calendario_image);
        taveloImage = (ImageView) findViewById(R.id.content_main_tavole_image);
        documentImage = (ImageView) findViewById(R.id.content_main_document_image);
        searchImage = (ImageView) findViewById(R.id.content_main_search_image);

        userId = utils.getSharedPrefrence(Constant.UserId);
        gearImage.setOnClickListener(this);
//        register();
        if (utils.isConnectingToInternet()) {
            new updateAllUsersList().execute();
        } else {

        }

        if (!userId.equalsIgnoreCase("")) {
            loginTv.setText(getString(R.string.logout));
            init();
        } else {
            loginTv.setText(getString(R.string.login));
            setDesignWithoutLogin();
        }
    }

//    private void register() {
//        if (utils.isConnectingToInternet()) {
//            gcmRegId = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_GCM_REGID);
//            if (gcmRegId.equals("")) {
//                // Registration is not present, register now with GCM
//                gcmRegId = registerGCM();
//            } else {
//                //Already Registr with GCM
//                gcmRegId = Utils.ReadSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_GCM_REGID);
//            }
//        }
//    }


    private void setDesignWithoutLogin() {

        calendarioImage.setImageResource(R.drawable.ic_calendario_disable);
        documentImage.setImageResource(R.drawable.ic_document_disable);
        taveloImage.setImageResource(R.drawable.ic_tavole_disable);
        searchImage.setImageResource(R.drawable.main_search_disable);
        loginLinear.setOnClickListener(this);
        rtiLinear.setOnClickListener(this);

    }


    private void init() {
        setDesignWithLogin();
    }

    private void setDesignWithLogin() {


        imageView.setOnClickListener(this);
        calendarioImage.setImageResource(R.drawable.ic_calendario);
        documentImage.setImageResource(R.drawable.ic_document);
        taveloImage.setImageResource(R.drawable.ic_tavole);
        searchImage.setImageResource(R.drawable.main_search_select);
        loginLinear.setOnClickListener(this);
        rtiLinear.setOnClickListener(this);
        taveloLinear.setOnClickListener(this);
        searchLinear.setOnClickListener(this);
        documentLinear.setOnClickListener(this);
        calendarioLinear.setOnClickListener(this);

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_main_login:
                if (!userId.equalsIgnoreCase("")) {
                    utils.clearSharedPreferenceData();
                    Intent inClear = new Intent(MainActivity.this, MainActivity.class);
                    this.finish();
                    inClear.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(inClear);
                } else {
                    Intent in = new Intent(MainActivity.this, Login.class);
                    startActivity(in);
                }
                break;
            case R.id.content_main_tavole:
//                Intent in1 = new Intent(MainActivity.this, TavoleCommit.class);
//                startActivity(in1);
                Intent in1 = new Intent(MainActivity.this, FullUserList.class);
                startActivity(in1);
                break;
            case R.id.content_main_search:
                Intent in2 = new Intent(MainActivity.this, UsersList.class);
                startActivity(in2);
                break;
            case R.id.main_rti_linear:
                Intent in3 = new Intent(MainActivity.this, RTIMain.class);
                startActivity(in3);
                break;
            case R.id.content_main_document:
                Intent in4 = new Intent(MainActivity.this, Document.class);
                startActivity(in4);
                break;
            case R.id.content_main_calendario:
                Intent in5 = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(in5);
                break;
            case R.id.activity_main_gear:
                Intent in6 = new Intent(MainActivity.this, GearScreen.class);
                startActivity(in6);
                break;
            case R.id.activity_main_imageView:
//                Intent in7 = new Intent(MainActivity.this, FullUserList.class);
//                startActivity(in7);
                break;
        }

    }

//    public String registerGCM() {
//        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
//        if (TextUtils.isEmpty(gcmRegId)) {
//            registerInBackground();
//        } else {
////            Toast.makeText(getApplicationContext(),
////                    "RegId already available. RegId: " + gcmRegId,
////                    Toast.LENGTH_LONG).show();
//        }
//        return gcmRegId;
//    }


//    private void registerInBackground() {
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... params) {
//                String msg = "";
//                try {
//                    if (gcm == null) {
//                        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
//                    }
//                    gcmRegId = gcm.register(Constant.SENDER_ID);
////                    gcmRegId = gcm.register()
//                    Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_GCM_REGID, gcmRegId);
//                    msg = "" + gcmRegId;
//
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//                    register();
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                Utils.WriteSharePrefrence(getApplicationContext(), Constant.SHRED_PR.KEY_GCM_REGID, msg);
//            }
//        }.execute(null, null, null);
//    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_FINE_LOCATION);
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    Log.e("LAT LNG", "LAT 0 " + latitude + " LNG 0 " + longitude);
                    if (utils.isConnectingToInternet() && latitude != null) {
                        if (!utils.getSharedPrefrence(Constant.UserId).equalsIgnoreCase("")) {
                            new UpdateLcoation().execute();
                        }
                    }
                }
            } else {
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    Log.e("LAT LNG", "LAT 1 " + latitude + " LNG 1 " + longitude);
                    if (utils.isConnectingToInternet() && latitude != null) {
                        if (!utils.getSharedPrefrence(Constant.UserId).equalsIgnoreCase("")) {
                            new UpdateLcoation().execute();
                        }
                    }
                }
            }
        } else {
            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                Log.e("LAT LNG", "LAT 2 " + latitude + " LNG 2 " + longitude);
                if (utils.isConnectingToInternet() && latitude != null) {
                    if (!utils.getSharedPrefrence(Constant.UserId).equalsIgnoreCase("")) {
                        new UpdateLcoation().execute();
                    }
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name)
                .setMessage(R.string.notgettinglocation)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.app_name)
                .setMessage(R.string.notgettinglocation)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                    }
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            mGoogleApiClient);
                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                        Log.e("LAT LNG", "LAT 3 " + latitude + " LNG 3 " + longitude);
                    }
                } else {
                    // permission denied
                    Toast.makeText(MainActivity.this, "Request not granted", Toast.LENGTH_SHORT).show();
                }
                break;


            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    private class UpdateLcoation extends AsyncTask<String, String, String> {
//        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = new ProgressDialog(MainActivity.this);
//            pd.setMessage("Loading...");
//            pd.setCancelable(false);
//            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("URL LOCATION ", ">>>>> " + Constant.BASE_URL + "update_user_lat_log.php?id=" + userId + "&lat=" + String.valueOf(latitude) + "&long=" + String.valueOf(longitude));
            return utils.getResponseofGet(Constant.BASE_URL + "update_user_lat_log.php?id=" + userId + "&lat=" + String.valueOf(latitude) + "&long=" + String.valueOf(longitude));

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
//                if (object.getString("status").equalsIgnoreCase("true")) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle(R.string.app_name)
//                            .setMessage(R.string.updatedlocation)
//                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle(R.string.app_name)
//                            .setMessage(R.string.locationfailedupdate)
//                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class updateAllUsersList extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = utils.getResponseofGet(Constant.BASE_URL + "api_user.php");

            JSONObject issueObj = null;
            offlineDetails = new ArrayList<UserDetails>();
            try {
                issueObj = new JSONObject(response);
                Iterator<String> iterator = issueObj.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    JSONArray mainArray = issueObj.getJSONArray(key);
                    for (int i = 0; i < mainArray.length(); i++) {
                        JSONObject object = mainArray.getJSONObject(i);
                        String id, social_class, areaid, zoneid,
                                clubid, nationalID, surname,
                                dob, tavola, city, profession, province,
                                tel_phone, name_wife, photo, inc_area,
                                Postal_Code, homephone, mobilephone, workphone, fax,
                                address_home, email, password, occupation, fb, lat, log,
                                twitter, linkedin, googleplus,
                                deviceid, status, club_city_name, is_enable;
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
                        club_city_name = object.getString("club_city_name");
                        Double latitude = Double.parseDouble(object.getString("lat"));
                        Double longitude = Double.parseDouble(object.getString("log"));
                        if (object.getString("surname").length() > 1) {
                            surname = object.getString("surname").substring(0, 1).toUpperCase() + object.getString("surname").substring(1);
                        }
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

                        offlineDetails.add(details);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dbhelper.insertUserList(offlineDetails);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

//            Toast.makeText(MainActivity.this, "" + dbhelper.getAllUser().size(), Toast.LENGTH_SHORT).show();


        }
    }
}
