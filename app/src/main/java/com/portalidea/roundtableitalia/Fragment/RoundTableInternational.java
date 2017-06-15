package com.portalidea.roundtableitalia.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.portalidea.roundtableitalia.Adapter.RoundTableInternationalAdapter;
import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.InternationalDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archirayan on 5/25/2016.
 */
public class RoundTableInternational extends Fragment {
    ListView listView;
    Utils utils;
    ArrayList<InternationalDetails> arrayDetails;
    ConnectionDetector cd;
    DatabaseHelper dbhelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_round_table_international, container, false);
        cd = new ConnectionDetector(getActivity());
        dbhelper = new DatabaseHelper(getActivity());
        utils = new Utils(getActivity());
        listView = (ListView) view.findViewById(R.id.fragment_roundf_table_international_list);
        if (cd.isConnectingToInternet()) {
            new getData().execute();
        } else {
            arrayDetails = dbhelper.getAllInternational();
            listView.setAdapter(new RoundTableInternationalAdapter(getActivity(), arrayDetails));
        }
        return view;
    }

    private class getData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrayDetails = new ArrayList<InternationalDetails>();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return utils.getResponseofGet(Constant.BASE_URL + "scraping.php");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);
                    InternationalDetails details = new InternationalDetails();
                    String imageURL = object.getString("img");
                    int index = imageURL.lastIndexOf("/");
                    details.setImage("http://www.rtinternational.org/wp-content/themes/RTI/rondels/big/" + imageURL.substring(index));
                    details.setText(object.getString("text"));
                    details.setName(object.getString("name"));
                    details.setUrl(object.getString("src"));
                    arrayDetails.add(details);
//                    name.add(object.getString("name"));
//                    text.add(object.getString("text"));
//                    image.add(object.getString("img"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            dbhelper.insertInternationalList(arrayDetails);
            listView.setAdapter(new RoundTableInternationalAdapter(getActivity(), arrayDetails));

        }
    }
}
