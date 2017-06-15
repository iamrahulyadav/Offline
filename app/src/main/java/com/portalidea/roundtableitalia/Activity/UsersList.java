package com.portalidea.roundtableitalia.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.portalidea.roundtableitalia.Adapter.MyAdapter;
import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.UserDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by archirayan on 5/2/2016.
 */
public class UsersList extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int PERMISSION_FINE_LOCATION = 10;
    public Double latitude, longitude;
    public LinearLayout llTab2;
    public RelativeLayout llTab1;
    public SeekBar sbDistance;
    public TextView tvProgress, tvSelectedName;
    public Utils utils;
    public ArrayList<String> name;
    public StickyListHeadersListView stickyList;
    public Map<String, Integer> mapIndex;
    private MapView myOpenMapView;
    private MapController myMapController;
    public LocationManager locationManager;
    public ArrayList<OverlayItem> overlayItemArray;
    public DefaultResourceProxyImpl defaultResourceProxyImpl;
    public DatabaseHelper dbhelper;
    public ConnectionDetector cd;
    public Button main_cerca_tabler, main_tablers_vicini_a_te;
    //    public //    LinearLayout edittextView;
    public EditText surnameEdt, tavolaEdt, cityEdt, provinciaEdt, professionEdt;
    public ArrayList<UserDetails> offlineDetails;
    public ArrayList<UserDetails> offlineDetailsSearch;
    private ArrayList<OverlayItem> OverlayItemArray;
    public Location curlocation;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    public LinearLayout linearOfBackground;
    public LinearLayout linearLayoutOfPinMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_userlist);

        ((TextView) findViewById(R.id.actionBarTitle)).setText("Risultati ricerca");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        linearLayoutOfPinMember = (LinearLayout) findViewById(R.id.activity_serach_linear_pinname);
        main_cerca_tabler = (Button) findViewById(R.id.main_cerca_tabler);
        main_tablers_vicini_a_te = (Button) findViewById(R.id.main_tablers_vicini_a_te);
        stickyList = (StickyListHeadersListView) findViewById(R.id.list);
        surnameEdt = (EditText) findViewById(R.id.header_list_surname_edt);
        myOpenMapView = (MapView) findViewById(R.id.openmapview);
        tavolaEdt = (EditText) findViewById(R.id.header_list_tovelo_edt);
        cityEdt = (EditText) findViewById(R.id.header_list_city_edt);
        provinciaEdt = (EditText) findViewById(R.id.header_list_provincia_edt);
        professionEdt = (EditText) findViewById(R.id.header_list_profession_edt);
        llTab1 = (RelativeLayout) findViewById(R.id.llTab1_HeaderList);
        llTab2 = (LinearLayout) findViewById(R.id.llTab2_HeaderList);
        sbDistance = (SeekBar) findViewById(R.id.sbDistance_HEADER_LIST);
        sbDistance.setProgressDrawable(ContextCompat.getDrawable(UsersList.this, R.drawable.ic_seekbar_background));
        sbDistance.setMax(50);
        tvSelectedName = (TextView) findViewById(R.id.tvSelectedMarker);
        tvProgress = (TextView) findViewById(R.id.tvProgress_HeaderList);
        linearOfBackground = (LinearLayout) findViewById(R.id.activity_search_linear_top);

// MEHUL
        offlineDetails = new ArrayList<UserDetails>();

        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Toast.makeText(UsersList.this, ""+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                OverlayItemArray = new ArrayList<OverlayItem>();
                tvProgress.setText("" + seekBar.getProgress() + " KM");
                for (int i = 0; i < offlineDetails.size(); i++) {

                    UserDetails object = offlineDetails.get(i);

                    if (Double.parseDouble(object.getLat()) != 0.0) {

                        Location location = new Location("List");
                        location.setLatitude(Double.parseDouble(object.getLat()));
                        location.setLongitude(Double.parseDouble(object.getLog()));
                        float distance = location.distanceTo(curlocation);

                        if ((distance / 10000) < seekBar.getProgress()) {
                            OverlayItem object1 = new OverlayItem(
                                    "Here", object.getName(), new GeoPoint(Double.parseDouble(object.getLat()), Double.parseDouble(object.getLog())));
                            Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
                            object1.setMarker(newMarker);
                            OverlayItemArray.add(object1);
                        }
                        putAllPin(OverlayItemArray, offlineDetails);
                    }
                }
            }
        });

        utils = new Utils(UsersList.this);
        dbhelper = new DatabaseHelper(UsersList.this);
        cd = new ConnectionDetector(UsersList.this);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.setClickable(true);

        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(17);
        overlayItemArray = new ArrayList<OverlayItem>();
        offlineDetailsSearch = new ArrayList<UserDetails>();

        try {

            curlocation = new Location("A");
            curlocation.setLatitude(latitude);
            curlocation.setLongitude(longitude);
            GeoPoint geoPoint = new GeoPoint(latitude, longitude);
            myMapController.setCenter(geoPoint);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }

        if (cd.isConnectingToInternet()) {


        } else {
            OverlayItemArray = new ArrayList<>();
            setUpMap();
            offlineDetails = dbhelper.getAllUser();
            name = new ArrayList<String>();
            for (int i = 0; i < offlineDetails.size(); i++) {
                name.add(offlineDetails.get(i).getSurname());
                OverlayItem object1 = new OverlayItem(
                        "", "", new GeoPoint(Double.parseDouble(offlineDetails.get(i).getLat()), Double.parseDouble(offlineDetails.get(i).getLog())));
                Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
                object1.setMarker(newMarker);
                OverlayItemArray.add(object1);
            }
            setUpList(name, offlineDetails);
            putAllPin(OverlayItemArray, offlineDetails);
        }


        main_cerca_tabler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTab2.setVisibility(View.VISIBLE);
                llTab1.setVisibility(View.GONE);
                main_cerca_tabler.setBackground(ContextCompat.getDrawable(UsersList.this, R.drawable.corner_yellow));
                linearOfBackground.setBackgroundColor(ContextCompat.getColor(UsersList.this, R.color.white));
                main_tablers_vicini_a_te.setBackgroundResource(0);
//                myOpenMapView.setVisibility(View.GONE);
//                edittextView.setVisibility(View.VISIBLE);
                Drawable img = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_white_user);
                main_cerca_tabler.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_blue_location);
                main_tablers_vicini_a_te.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);
                main_cerca_tabler.setTextColor(ContextCompat.getColor(UsersList.this, R.color.white));
                main_tablers_vicini_a_te.setTextColor(ContextCompat.getColor(UsersList.this, R.color.blue));

            }
        });

        main_tablers_vicini_a_te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTab2.setVisibility(View.GONE);
                llTab1.setVisibility(View.VISIBLE);
                main_cerca_tabler.setBackgroundResource(0);
                main_tablers_vicini_a_te.setBackground(ContextCompat.getDrawable(UsersList.this, R.drawable.corner_yellow));
                linearOfBackground.setBackgroundColor(ContextCompat.getColor(UsersList.this, R.color.white));
                Drawable img = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_white_location);
                main_tablers_vicini_a_te.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_blue_user);
                main_cerca_tabler.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);
//                edittextView.setVisibility(View.GONE);
//                myOpenMapView.setVisibility(View.VISIBLE);
                main_cerca_tabler.setTextColor(ContextCompat.getColor(UsersList.this, R.color.blue));
                main_tablers_vicini_a_te.setTextColor(ContextCompat.getColor(UsersList.this, R.color.white));
            }
        });


        surnameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchSurname(s.toString());

            }
        });

        tavolaEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchTavola(s.toString());

            }
        });

        cityEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchCity(s.toString());

            }
        });

        professionEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchProfession(s.toString());

            }
        });
        provinciaEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchProvincia(s.toString());

            }
        });

    }

    private void searchProvincia(String s) {
        String tavola = tavolaEdt.getText().toString();
        String profession = professionEdt.getText().toString();
        String city = cityEdt.getText().toString();
        String surname = surnameEdt.getText().toString();

        ArrayList<String> name = new ArrayList<>();
        offlineDetailsSearch = new ArrayList<>();
        for (int i = 0; i < offlineDetails.size(); i++) {
            UserDetails details = offlineDetails.get(i);
            if (details.getSurname().toLowerCase().contains(surname.toLowerCase()) && details.getProvince().toLowerCase().contains(s.toLowerCase()) && details.getTavola().toLowerCase().contains(tavola.toLowerCase()) && details.getProfession().toLowerCase().contains(profession.toLowerCase()) && details.getClub_city_name().toLowerCase().contains(city.toLowerCase())) {

                offlineDetailsSearch.add(offlineDetails.get(i));
                name.add(offlineDetails.get(i).getSurname());

            }
        }
        setUpList(name, offlineDetailsSearch);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    private void searchProfession(String s) {

        String surname = surnameEdt.getText().toString();
        String tavola = tavolaEdt.getText().toString();
        String provincia = provinciaEdt.getText().toString();
        String city = cityEdt.getText().toString();

        ArrayList<String> name = new ArrayList<>();
        offlineDetailsSearch = new ArrayList<>();
        for (int i = 0; i < offlineDetails.size(); i++) {
            UserDetails details = offlineDetails.get(i);
            if (details.getTavola().toLowerCase().contains(tavola.toLowerCase()) && details.getSurname().toLowerCase().contains(surname.toLowerCase()) && details.getClub_city_name().toLowerCase().contains(city.toLowerCase()) && details.getProvince().toLowerCase().contains(provincia.toLowerCase()) && details.getProfession().toLowerCase().contains(s.toLowerCase())) {

                offlineDetailsSearch.add(offlineDetails.get(i));
                name.add(offlineDetails.get(i).getSurname());

            }
        }
        setUpList(name, offlineDetailsSearch);
    }

    private void searchCity(String s) {

        String surname = surnameEdt.getText().toString();
        String tavola = tavolaEdt.getText().toString();
        String profession = professionEdt.getText().toString();
//        String city = cityEdt.getText().toString();
        String provincia = provinciaEdt.getText().toString();

        ArrayList<String> name = new ArrayList<>();
        offlineDetailsSearch = new ArrayList<>();
        for (int i = 0; i < offlineDetails.size(); i++) {
            UserDetails details = offlineDetails.get(i);
            if (details.getTavola().toLowerCase().contains(tavola.toLowerCase()) && details.getProvince().toLowerCase().contains(provincia.toLowerCase()) && details.getSurname().toLowerCase().contains(surname.toLowerCase()) && details.getClub_city_name().toLowerCase().contains(s.toLowerCase()) && details.getProfession().toLowerCase().contains(profession.toLowerCase())) {

                offlineDetailsSearch.add(offlineDetails.get(i));
                name.add(offlineDetails.get(i).getSurname());

            }
        }
        setUpList(name, offlineDetailsSearch);
    }

    private void searchTavola(String s) {

        String surname = surnameEdt.getText().toString();
//        String tavola = tavolaEdt.getText().toString();
        String profession = professionEdt.getText().toString();
        String city = cityEdt.getText().toString();
        String provincia = provinciaEdt.getText().toString();

        ArrayList<String> name = new ArrayList<>();
        offlineDetailsSearch = new ArrayList<>();
        for (int i = 0; i < offlineDetails.size(); i++) {
            UserDetails details = offlineDetails.get(i);
            if (s.length() > 0) {


                if (s.toLowerCase().equalsIgnoreCase(details.getTavola().toLowerCase()) && details.getProvince().toLowerCase().contains(provincia.toLowerCase()) && details.getSurname().toLowerCase().contains(surname.toLowerCase()) && details.getClub_city_name().toLowerCase().contains(city.toLowerCase()) && details.getProfession().toLowerCase().contains(profession.toLowerCase())) {

                    offlineDetailsSearch.add(offlineDetails.get(i));
                    name.add(offlineDetails.get(i).getSurname());

                }
            } else {

                if (details.getSurname().toLowerCase().contains(surname.toLowerCase()) && details.getCity().toLowerCase().contains(city.toLowerCase()) && details.getProfession().toLowerCase().contains(profession.toLowerCase())) {

                    offlineDetailsSearch.add(offlineDetails.get(i));
                    name.add(offlineDetails.get(i).getSurname());

                }

            }
        }
        setUpList(name, offlineDetailsSearch);
    }


    private void searchSurname(String s) {
//        String surname = surnameEdt.getText().toString();
        String tavola = tavolaEdt.getText().toString();
        String profession = professionEdt.getText().toString();
        String city = cityEdt.getText().toString();
        String provincia = provinciaEdt.getText().toString();

        ArrayList<String> name = new ArrayList<>();
        offlineDetailsSearch = new ArrayList<>();
        for (int i = 0; i < offlineDetails.size(); i++) {
            UserDetails details = offlineDetails.get(i);


//
//            String lastCharcater = s.substring(s.length() - 1, s.length());
//            String cogname = "";
//            if (lastCharcater == " ") {
//
//                cogname = s.substring(0, s.length() - 1).toLowerCase();
//
//            } else {
//
//                cogname = s.toLowerCase();
//
//            }

            if (details.getSurname().toLowerCase().trim().contains(s.toLowerCase().trim()) && details.getTavola().toLowerCase().contains(tavola.toLowerCase()) && details.getProvince().toLowerCase().contains(provincia.toLowerCase()) && details.getProfession().toLowerCase().contains(profession.toLowerCase()) && details.getClub_city_name().toLowerCase().contains(city.toLowerCase())) {

                offlineDetailsSearch.add(offlineDetails.get(i));
                name.add(offlineDetails.get(i).getSurname());

            }
        }
        setUpList(name, offlineDetailsSearch);
    }

    private void setUpMap() {

        defaultResourceProxyImpl = new DefaultResourceProxyImpl(this);
//        MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(overlayItemArray, null, defaultResourceProxyImpl);
//        myOpenMapView.getOverlays().add(myItemizedIconOverlay);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //for demo, getLastKnownLocation from GPS only, not from NETWORK
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        Location lastLocation = new Location("");
//        lastLocation.setLatitude(20.30);
//        lastLocation.setLongitude(52.30);
//        if (lastLocation != null) {
//            updateLoc(lastLocation);
//        }
        //Add Scale Bar
//        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
//        myOpenMapView.getOverlays().add(myScaleBarOverlay);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                        PERMISSION_FINE_LOCATION);
//            } else {
//                if (mLastLocation != null) {
//                    latitude = mLastLocation.getLatitude();
//                    longitude = mLastLocation.getLongitude();
//                    curlocation = new Location("A");
//                    curlocation.setLatitude(latitude);
//                    curlocation.setLongitude(longitude);
//                    GeoPoint geoPoint = new GeoPoint(latitude, longitude);
//                    myMapController.setCenter(geoPoint);
//                }
//                if (cd.isConnectingToInternet()) {
//                    if (offlineDetails.size() == 0) {
//                        new getData().execute();
//                    } else {
//                        setUpList(name, offlineDetails);
//                        putAllPin(OverlayItemArray, offlineDetails);
//                    }
//                } else {
//                    OverlayItemArray = new ArrayList<>();
//                    setUpMap();
//                    offlineDetails = dbhelper.getAllUser();
//                    name = new ArrayList<String>();
//                    for (int i = 0; i < offlineDetails.size(); i++) {
//                        name.add(offlineDetails.get(i).getSurname());
//                        OverlayItem object1 = new OverlayItem(
//                                "", "", new GeoPoint(Double.parseDouble(offlineDetails.get(i).getLat()), Double.parseDouble(offlineDetails.get(i).getLog())));
//                        Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
//                        object1.setMarker(newMarker);
//                        OverlayItemArray.add(object1);
//                    }
//                    setUpList(name, offlineDetails);
//                    putAllPin(OverlayItemArray, offlineDetails);
//                }
//
//            }
//        } else {
//
//            if (mLastLocation != null) {
//
//                latitude = mLastLocation.getLatitude();
//                longitude = mLastLocation.getLongitude();
//                curlocation = new Location("A");
//                curlocation.setLatitude(latitude);
//                curlocation.setLongitude(longitude);
//                GeoPoint geoPoint = new GeoPoint(latitude, longitude);
//                myMapController.setCenter(geoPoint);
//            }
//            if (cd.isConnectingToInternet()) {
//
//                if (offlineDetails.size() == 0) {
//                    new getData().execute();
//                } else {
//                    setUpList(name, offlineDetails);
//                    putAllPin(OverlayItemArray, offlineDetails);
//                }
//
//            } else {
//                OverlayItemArray = new ArrayList<>();
//                setUpMap();
//                offlineDetails = dbhelper.getAllUser();
//                name = new ArrayList<String>();
//                for (int i = 0; i < offlineDetails.size(); i++) {
//                    name.add(offlineDetails.get(i).getSurname());
//                    OverlayItem object1 = new OverlayItem(
//                            "", "", new GeoPoint(Double.parseDouble(offlineDetails.get(i).getLat()), Double.parseDouble(offlineDetails.get(i).getLog())));
//                    Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
//                    object1.setMarker(newMarker);
//                    OverlayItemArray.add(object1);
//                }
//                setUpList(name, offlineDetails);
//                putAllPin(OverlayItemArray, offlineDetails);
//            }
//        }
//
//    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void displayIndex() {
        LinearLayout oldindexLayout = (LinearLayout) findViewById(R.id.side_index);
        oldindexLayout.removeAllViews();
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            if (indexLayout != null) {
                indexLayout.addView(textView);
            }
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

    @Override
    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        stickyList.setSelection(mapIndex.get(selectedIndex.getText()));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_FINE_LOCATION);
            } else {
                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    curlocation = new Location("A");
                    curlocation.setLatitude(latitude);
                    curlocation.setLongitude(longitude);
                    GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                    myMapController.setCenter(geoPoint);
                }
                if (cd.isConnectingToInternet()) {
                    if (offlineDetails.size() == 0) {
                        new getData().execute();
                    } else {
                        setUpList(name, offlineDetails);
                        putAllPin(OverlayItemArray, offlineDetails);
                    }
                } else {
                    OverlayItemArray = new ArrayList<>();
                    setUpMap();
                    offlineDetails = dbhelper.getAllUser();
                    name = new ArrayList<String>();
                    for (int i = 0; i < offlineDetails.size(); i++) {
                        name.add(offlineDetails.get(i).getSurname());
                        OverlayItem object1 = new OverlayItem(
                                "", "", new GeoPoint(Double.parseDouble(offlineDetails.get(i).getLat()), Double.parseDouble(offlineDetails.get(i).getLog())));
                        Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
                        object1.setMarker(newMarker);
                        OverlayItemArray.add(object1);
                    }
                    setUpList(name, offlineDetails);
                    putAllPin(OverlayItemArray, offlineDetails);
                }

            }
        } else {

            if (mLastLocation != null) {

                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                curlocation = new Location("A");
                curlocation.setLatitude(latitude);
                curlocation.setLongitude(longitude);
                GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                myMapController.setCenter(geoPoint);
            }
            if (cd.isConnectingToInternet()) {

                if (offlineDetails.size() == 0) {
                    new getData().execute();
                } else {
                    setUpList(name, offlineDetails);
                    putAllPin(OverlayItemArray, offlineDetails);
                }

            } else {
                OverlayItemArray = new ArrayList<>();
                setUpMap();
                offlineDetails = dbhelper.getAllUser();
                name = new ArrayList<String>();
                for (int i = 0; i < offlineDetails.size(); i++) {
                    name.add(offlineDetails.get(i).getSurname());
                    OverlayItem object1 = new OverlayItem(
                            "", "", new GeoPoint(Double.parseDouble(offlineDetails.get(i).getLat()), Double.parseDouble(offlineDetails.get(i).getLog())));
                    Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
                    object1.setMarker(newMarker);
                    OverlayItemArray.add(object1);
                }
                setUpList(name, offlineDetails);
                putAllPin(OverlayItemArray, offlineDetails);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UsersList.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UsersList.this);
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

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name = new ArrayList<String>();
            OverlayItemArray = new ArrayList<>();
            pd = new ProgressDialog(UsersList.this);
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
            overlayItemArray.clear();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = utils.getResponseofGet(Constant.BASE_URL + "api_user.php");

            JSONObject issueObj = null;
            setUpMap();
//            offlineDetails = new ArrayList<UserDetails>();
            try {
                issueObj = new JSONObject(response);
                Iterator<String> iterator = issueObj.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    JSONArray mainArray = issueObj.getJSONArray(key);
                    Log.i("Count", "" + mainArray.length());
                    for (int i = 0; i < mainArray.length(); i++) {
                        JSONObject object = mainArray.getJSONObject(i);
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

                        if (latitude != 0.0) {
                            Location location = new Location("List");
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);

                            if (curlocation != null) {

                                float distance = location.distanceTo(curlocation);

                            }

                            OverlayItem object1 = new OverlayItem(
                                    Name + " " + surname, /*object.getString()*/Name, new GeoPoint(latitude, longitude));
                            Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
                            object1.setMarker(newMarker);
                            OverlayItemArray.add(object1);
                        }
                    }
                    Log.i("ArrayCount", "" + offlineDetails.size());
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
            putAllPin(OverlayItemArray, offlineDetails);
            setUpList(name, offlineDetails);
//            JSONObject issueObj = null;
//            setUpMap();
//            offlineDetails = new ArrayList<UserDetails>();
//            try {
//                issueObj = new JSONObject(s);
//                Iterator<String> iterator = issueObj.keys();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//                    JSONArray mainArray = issueObj.getJSONArray(key);
//                    for (int i = 0; i < mainArray.length(); i++) {
//                        JSONObject object = mainArray.getJSONObject(i);
//                        String id, social_class, areaid, zoneid,
//                                clubid, nationalID, surname,
//                                dob, tavola, city, profession, province,
//                                tel_phone, name_wife, photo, inc_area,
//                                Postal_Code, homephone, mobilephone, workphone, fax,
//                                address_home, email, password, occupation, fb, lat, log,
//                                twitter, linkedin, googleplus,
//                                deviceid, status, club_city_name;
//                        String Name = object.getString("name").substring(0, 1).toUpperCase() + object.getString("name").substring(1);
////                        name.add(Name);
////                        Double latitude = Double.parseDouble(object.getString("lat"));
////                        Double longitude = Double.parseDouble(object.getString("log"));
////
////                        UserDetails details = new UserDetails();
////                        details.setName(Name);
////                        details.setId(object.getString("id"));
////                        details.setSurname(object.getString("surname"));
////                        details.setTavola(object.getString("tavola"));
////                        details.setCity(object.getString("city"));
////                        details.setProfession(object.getString("profession"));
//                        photo = "http://www.roundtable.it/uploads/tx_annuario/" + object.getString("photo");
//                        social_class = object.getString("social_class");
//                        areaid = object.getString("areaid");
//                        zoneid = object.getString("zoneid");
//                        clubid = object.getString("clubid");
//                        nationalID = object.getString("nationalID");
//                        surname = object.getString("surname");
//                        dob = object.getString("dob");
//                        tavola = object.getString("tavola");
//                        city = object.getString("city");
//                        profession = object.getString("profession");
//                        province = object.getString("province");
//                        tel_phone = object.getString("tel_phone");
//                        name_wife = object.getString("name_wife");
//                        inc_area = object.getString("inc_area");
//                        Postal_Code = object.getString("Postal_Code");
//                        homephone = object.getString("tel_phone");
//                        mobilephone = object.getString("mobilephone");
//                        workphone = object.getString("workphone");
//                        fax = object.getString("fax");
//                        address_home = object.getString("address_home");
//                        email = object.getString("email");
//                        password = object.getString("password");
//                        occupation = object.getString("occupation");
//                        fb = object.getString("fb");
//                        twitter = object.getString("twitter");
//                        linkedin = object.getString("linkedin");
//                        googleplus = object.getString("googleplus");
//                        deviceid = object.getString("deviceid");
//                        status = object.getString("status");
//                        lat = object.getString("lat");
//                        log = object.getString("log");
//                        club_city_name = object.getString("club_city_name");
//                        Double latitude = Double.parseDouble(object.getString("lat"));
//                        Double longitude = Double.parseDouble(object.getString("log"));
//
//                        surname = object.getString("surname").substring(0, 1).toUpperCase() + object.getString("surname").substring(1);
//                        name.add(surname);
//                        UserDetails details = new UserDetails();
//
//                        details.setName(Name);
//                        details.setId(object.getString("id"));
//                        details.setSurname(surname);
//                        details.setSocial_class(social_class);
//                        details.setAreaid(areaid);
//                        details.setZoneid(zoneid);
//                        details.setClubid(clubid);
//                        details.setNationalID(nationalID);
//                        details.setDob(dob);
//                        details.setTavola(tavola);
//                        details.setCity(city);
//                        details.setProfession(profession);
//                        details.setProvince(province);
//                        details.setTel_phone(tel_phone);
//                        details.setName_wife(name_wife);
//                        details.setPhoto(photo);
//                        details.setInc_area(inc_area);
//                        details.setPostal_Code(Postal_Code);
//                        details.setHomephone(homephone);
//                        details.setMobilephone(mobilephone);
//                        details.setWorkphone(workphone);
//                        details.setFax(fax);
//                        details.setAddress_home(address_home);
//                        details.setEmail(email);
//                        details.setPassword(password);
//                        details.setOccupation(occupation);
//                        details.setFb(fb);
//                        details.setLat(lat);
//                        details.setLog(log);
//                        details.setTwitter(twitter);
//                        details.setLinkedin(linkedin);
//                        details.setGoogleplus(googleplus);
//                        details.setDeviceid(deviceid);
//                        details.setStatus(status);
//                        details.setClub_city_name(club_city_name);
//
//                        offlineDetails.add(details);
//
//                        if (latitude != 0.0) {
//                            Location location = new Location("List");
//                            location.setLatitude(latitude);
//                            location.setLongitude(longitude);
//
//                            if (curlocation != null) {
//
//                                float distance = location.distanceTo(curlocation);
//                                Log.d("Distance", "" + distance);
//
//                            }
////                            setOverlayLoc(location);
////                            GeoPoint startPoint = new GeoPoint(latitude, longitude);
////                            myMapController.setCenter(startPoint);
//                            OverlayItem object1 = new OverlayItem(
//                                    "", "", new GeoPoint(latitude, longitude));
//                            Drawable newMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
//                            object1.setMarker(newMarker);
//                            OverlayItemArray.add(object1);
//                        }
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            putAllPin(OverlayItemArray);
//            dbhelper.insertUserList(offlineDetails);
//            setUpList(name, offlineDetails);
        }
    }

    public void putAllPin(final ArrayList<OverlayItem> array, final ArrayList<UserDetails> arrayDetails) {
        myOpenMapView.getOverlays().clear();
        tvSelectedName.setText("");
        linearLayoutOfPinMember.setVisibility(View.GONE);
//        for (int index = 0; index < arrayDetails.size(); index++) {
//            if (Double.parseDouble(arrayDetails.get(index).getLat()) == 0.00000000) {
//                arrayDetails.remove(index);
//            }
//        }

        ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(
                UsersList.this, array, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int i, OverlayItem overlayItem) {
                linearLayoutOfPinMember.setVisibility(View.VISIBLE);
                for (int k = 0; k < array.size(); k++) {

                    OverlayItem objectMarker = array.get(k);
                    Drawable myCurrentLocationMarker1 = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker);
                    objectMarker.setMarker(myCurrentLocationMarker1);

                }
                Drawable myCurrentLocationMarker = ContextCompat.getDrawable(UsersList.this, R.drawable.ic_marker_selected);
                overlayItem.setMarker(myCurrentLocationMarker);
                tvSelectedName.setText(getString(R.string.personselected) + " " + overlayItem.getTitle());
                return false;
            }

            @Override
            public boolean onItemLongPress(int i, OverlayItem overlayItem) {
                return false;
            }
        });
        myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);


//        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(UsersList.this);
//        myOpenMapView.getOverlays().add(myScaleBarOverlay);
    }

    public class SortBasedOnName implements Comparator {
        public int compare(Object o1, Object o2) {
            String dd1 = (String) o1;// where FBFriends_Obj is your object class
            String dd2 = (String) o2;
            return dd1.compareToIgnoreCase(dd2);//where uname is field name
        }
    }

    public void setUpList(ArrayList<String> name1, ArrayList<UserDetails> arrayUserDetails) {
        if (name1.size() == 0) {

            findViewById(R.id.emptyViewUserList).setVisibility(View.VISIBLE);
            findViewById(R.id.list).setVisibility(View.GONE);
            findViewById(R.id.side_index).setVisibility(View.GONE);

        } else {
            findViewById(R.id.emptyViewUserList).setVisibility(View.GONE);
            findViewById(R.id.list).setVisibility(View.VISIBLE);
            findViewById(R.id.side_index).setVisibility(View.VISIBLE);
            Collections.sort(name1, new SortBasedOnName());
            Collections.sort(arrayUserDetails, new Comparator<UserDetails>() {
                @Override
                public int compare(UserDetails lhs, UserDetails rhs) {
                    return lhs.getSurname().compareToIgnoreCase(rhs.getSurname());
                }
            });
            MyAdapter adapter = new MyAdapter(UsersList.this, arrayUserDetails);
            stickyList.setAdapter(adapter);
            stickyList.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

//        setListViewHeightBasedOnChildren(stickyList);
            getIndexList(name1);
            displayIndex();
        }
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
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            mGoogleApiClient);
                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                    }
                } else {
                    // permission denied
                    Toast.makeText(UsersList.this, "Request not granted", Toast.LENGTH_SHORT).show();
                }
                break;


            }
        }
    }
}
