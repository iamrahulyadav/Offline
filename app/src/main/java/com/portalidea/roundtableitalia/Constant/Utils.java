package com.portalidea.roundtableitalia.Constant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.portalidea.roundtableitalia.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by archirayan on 4/29/2016.
 */
public class Utils {

    Context context;
    SharedPreferences sp;
    Location getLocation;

    public Utils(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(Constant.Prefrence, Context.MODE_PRIVATE);
    }


    public void setSharedPrefrence(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public String getSharedPrefrence(String key) {
        String defValue = "";
        String resposne = sp.getString(key, defValue);
        return resposne;
    }

    public void clearSharedPreferenceData() {
        sp.edit().clear().commit();

    }

    public String getResponseofPost(String URL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getResponseofGet(String URL) {
        URL url;
        String response = "";
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            url = new URL(URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            Log.d("URL - ResponseCode", URL + " - " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public String MakeServiceCall(String URLSTR) {
        StringBuilder response = null;


        try {
            response = new StringBuilder();
            URL url = new URL(URLSTR);
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
            if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    response.append(strLine);
                }
                input.close();
            }
        } catch (Exception e) {

        }
        return response.toString();

    }


    private boolean appInstalledOrNot(String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            Log.d("Network",
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean connectiondetect() {

        if (isConnectingToInternet()) {
            return true;
        } else {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_connectionfailed);
            Button ok = (Button) dialog.findViewById(R.id.dialog_ok);
            Button cancel = (Button) dialog.findViewById(R.id.dialog_cancel);
            dialog.show();
            dialog.setCancelable(false);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    connectiondetect();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));

                }
            });
            return false;
        }

    }

    public static void WriteSharePrefrence(Context context, String key,
                                           String value) {
        @SuppressWarnings("static-access")
//        SharedPreferences write_Data = context.getSharedPreferences(
//                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
//        Editor editor = write_Data.edit();
//        editor.putString(key, values);
//        editor.apply();

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String ReadSharePrefrence(Context context, String key) {
//        SharedPreferences read_data = context.getSharedPreferences(
//                Constant.SHRED_PR.SHARE_PREF, context.MODE_PRIVATE);
//
//        return read_data.getString(key, "");

        String data;
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        data = preferences.getString(key, "");
        editor.commit();
        return data;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    public static String getFinalTavolaString(String nation, String inc_zona, String inc_tavola1, String inc_tavola2) {
        String fullString = "";
        if (nation.length() > 0) {
            fullString = fullString + nation;
        }
        if (inc_zona.length() > 0) {
            if (fullString.length() > 0) {
                fullString = fullString + " - " + inc_zona + " di Zona ";
            } else {
                fullString = fullString + inc_zona + " di Zona ";
            }
        }
        if (inc_tavola1.length() > 0) {
            if (fullString.length() > 0) {
                fullString = fullString + " - " + inc_tavola1 + " di Tavola ";
            } else {
                fullString = fullString + inc_tavola1 + " di Tavola ";
            }
        }
        if (inc_tavola2.length() > 0) {
            if (fullString.length() > 0) {
                fullString = fullString + " - " + inc_tavola2 + " di Tavola ";
            } else {
                fullString = fullString + inc_tavola2 + " di Tavola ";
            }
        }
        return fullString;
    }

    public static String getTavolaString(String nation, String inc_zona, String inc_tavola1, String inc_tavola2) {
        String fullString = "";
        if (nation.length() > 0) {
            fullString = " - " + nation;
            return fullString;
        } else if (inc_zona.length() > 0 && fullString.length() == 0) {
            fullString = " - " + inc_zona + " di Zona ";
            return fullString;
        } else if (inc_tavola1.length() > 0 && fullString.length() == 0) {
            fullString = " - " + inc_tavola1 + " di Tavola ";
            return fullString;
        } else if (inc_tavola2.length() > 0 && fullString.length() == 0) {
            fullString = " - " + inc_tavola2 + " di Tavola ";
            return fullString;
        } else {
            return "";
        }
    }

    public static String getTavolaStringFullScreen(String nation, String inc_zona, String inc_tavola1, String inc_tavola2) {
        String fullString = "";
        if (nation.length() > 0) {
            fullString = nation;
            return fullString;
        } else if (inc_zona.length() > 0 && fullString.length() == 0) {
            fullString = " - " + inc_zona + " di Zona ";
            return fullString;
        } else if (inc_tavola1.length() > 0 && fullString.length() == 0) {
            fullString = " - " + inc_tavola1 + " di Tavola ";
            return fullString;
        } else if (inc_tavola2.length() > 0 && fullString.length() == 0) {
            fullString = " - " + inc_tavola2 + " di Tavola ";
            return fullString;
        } else {
            return "";
        }
    }
}

