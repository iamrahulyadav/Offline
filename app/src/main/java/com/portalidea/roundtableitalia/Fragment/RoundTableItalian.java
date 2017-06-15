package com.portalidea.roundtableitalia.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Adapter.RoundTableItalianAdapter;
import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.ItalianDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archirayan on 5/25/2016.
 */
public class RoundTableItalian extends Fragment {
    ListView listView;
    Utils utils;
    ArrayList<ItalianDetails> arrayDetails;
    ConnectionDetector connectionDetector;
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_round_table_italian, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        utils = new Utils(getActivity());
        listView = (ListView) view.findViewById(R.id.fragment_roundf_table_italian_list);
        connectionDetector = new ConnectionDetector(getActivity());
        if (connectionDetector.isConnectingToInternet()) {

            new getData().execute();

        } else {

            arrayDetails = databaseHelper.getAllItalianUser();
            listView.setAdapter(new RoundTableItalianAdapter(getActivity(), arrayDetails, getFragmentManager()));

        }

        return view;
    }

    private class getData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrayDetails = new ArrayList<ItalianDetails>();

            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return utils.getResponseofGet(Constant.BASE_URL + "get_xml.php");

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONArray array = new JSONArray(s.toString());
                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);
                    if (object.getString("tavola_sciolta").equalsIgnoreCase("no")) {
                        ItalianDetails details = new ItalianDetails();
                        details.setLat(object.getString("lat"));
                        details.setLng(object.getString("lng"));
                        details.setNumero(object.getString("numero"));
                        details.setNome(object.getString("nome"));
                        details.setZona("" + object.getString("zona"));
                        details.setTavola_sciolta(object.getString("tavola_sciolta"));
                        details.setMadrina(object.getString("madrina"));
                        details.setCharter_meeting(object.getString("charter_meeting"));
                        details.setGemellate(object.getString("gemellate"));
                        details.setRiunioni(object.getString("riunioni"));
                        details.setRitrovo(object.getString("ritrovo"));
                        details.setEmail(object.getString("email"));
                        details.setWeb(object.getString("web"));
                        details.setFacebook(object.getString("facebook"));
                        details.setTwitter(object.getString("twitter"));
                        details.setCap(object.getString("cap"));
                        details.setFax(object.getString("fax"));
                        details.setFoto(object.getString("foto"));
                        details.setEditor(object.getString("editor"));
                        details.setNote(object.getString("note"));
                        if (object.has("Presidente")) {
                            details.setPresident(object.getString("Presidente"));
                        } else {
                            details.setPresident("");
                        }

                        arrayDetails.add(details);
//                    name.add(object.getString("name"));
//                    text.add(object.getString("text"));
//                    image.add(object.getString("img"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
            databaseHelper.insertItalianUserList(arrayDetails);
            listView.setAdapter(new RoundTableItalianAdapter(getActivity(), arrayDetails, getFragmentManager()));

        }
    }
}
