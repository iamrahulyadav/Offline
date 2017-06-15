package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Adapter.ElencoAdapter;
import com.portalidea.roundtableitalia.Adapter.TavoleDetailsAdapter;
import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.DatabaseHelper.DatabaseHelper;
import com.portalidea.roundtableitalia.Model.ItalianDetails;
import com.portalidea.roundtableitalia.Model.ZoneUserDetails;
import com.portalidea.roundtableitalia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by archirayan on 6/11/2016.
 */
public class ZoneDetails extends Activity implements View.OnClickListener {
    Button ElencoBtn, comitatoBtn;
    String Zone;
    private ImageView image;
    private Utils utils;
    private ConnectionDetector cd;
    private ListView listView;
    public ArrayList<ZoneUserDetails> zoneDetailsArray;
    public ArrayList<ItalianDetails> zoneElencoArray;
    public String ZoneNumber;
    public DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_details);
        comitatoBtn = (Button) findViewById(R.id.activity_zone_details_comitato);
        ElencoBtn = (Button) findViewById(R.id.activity_zone_details_elenco);
        image = (ImageView) findViewById(R.id.activity_zone_details_image);
        listView = (ListView) findViewById(R.id.activity_zone_details_list);
        dbhelper = new DatabaseHelper(ZoneDetails.this);
        cd = new ConnectionDetector(ZoneDetails.this);
        utils = new Utils(ZoneDetails.this);

        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (getIntent().getExtras() != null) {
            Zone = getIntent().getExtras().getString("Zone");
            setupMainImage();
        }

        comitatoBtn.setOnClickListener(this);
        ElencoBtn.setOnClickListener(this);

        init();

    }

    private void init() {

        comitatoBtn.setBackgroundColor(ContextCompat.getColor(ZoneDetails.this, R.color.yellow));
        ElencoBtn.setBackgroundColor(ContextCompat.getColor(ZoneDetails.this, R.color.gray));

        if (cd.isConnectingToInternet()) {

            new getData().execute();

        } else {
            if (Zone.equalsIgnoreCase("1")) {
                ZoneNumber = "I";
            } else if (Zone.equalsIgnoreCase("2")) {
                ZoneNumber = "II";
            } else if (Zone.equalsIgnoreCase("3")) {
                ZoneNumber = "III";
            } else {
                ZoneNumber = "V";
            }

            zoneDetailsArray = dbhelper.getAllUserByZone(ZoneNumber);
            listView.setAdapter(new TavoleDetailsAdapter(ZoneDetails.this, zoneDetailsArray));

           /* cd.connectiondetect();*/

        }
    }

    private void setupMainImage() {
        if (Zone.equalsIgnoreCase("1")) {
            ZoneNumber = "I";
            image.setImageResource(R.drawable.ic_primary_zona);
            ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Prima Zona");
        } else if (Zone.equalsIgnoreCase("2")) {
            ZoneNumber = "II";
            image.setImageResource(R.drawable.ic_secondary_zona);
            ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Seconda Zona");
        } else if (Zone.equalsIgnoreCase("3")) {
            ZoneNumber = "III";
            image.setImageResource(R.drawable.ic_terza_zona);
            ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Terza Zona");
        } else {
            ZoneNumber = "V";
            image.setImageResource(R.drawable.ic_quarter_zona);
            ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Quinta Zona");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_zone_details_elenco:
                ElencoBtn.setBackgroundColor(ContextCompat.getColor(ZoneDetails.this, R.color.yellow));
                comitatoBtn.setBackgroundColor(ContextCompat.getColor(ZoneDetails.this, R.color.gray));
                if (cd.isConnectingToInternet()) {
                    new getDataElenco().execute();
                } else {
                    zoneElencoArray = dbhelper.getAllItalianUserByZone(ZoneNumber);
                    listView.setAdapter(new ElencoAdapter(ZoneDetails.this, zoneElencoArray));
                }

                break;
            case R.id.activity_zone_details_comitato:
                comitatoBtn.setBackgroundColor(ContextCompat.getColor(ZoneDetails.this, R.color.yellow));
                ElencoBtn.setBackgroundColor(ContextCompat.getColor(ZoneDetails.this, R.color.gray));
                if (cd.isConnectingToInternet()) {
                    new getData().execute();
                } else {
                    zoneDetailsArray = dbhelper.getAllUserByZone(ZoneNumber);
                    listView.setAdapter(new TavoleDetailsAdapter(ZoneDetails.this, zoneDetailsArray));
                }

                break;
        }

    }

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            zoneDetailsArray = new ArrayList<>();
            pd = new ProgressDialog(ZoneDetails.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (Zone.equalsIgnoreCase("1")) {

                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tablers.php?zone=I");
            } else if (Zone.equalsIgnoreCase("2")) {

                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tablers.php?zone=II");
            } else if (Zone.equalsIgnoreCase("3")) {

                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tablers.php?zone=III");
            } else if (Zone.equalsIgnoreCase("4")) {

                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tablers.php?zone=V");
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONArray arry = new JSONArray(s);
                for (int i = 0; i < arry.length(); i++) {
                    JSONObject obj = arry.getJSONObject(i);


                    String inc_zona = obj.getString("inc_zona");
                    String tavole = obj.getString("tavola");
                    String citta = obj.getString("citta");
                    String image = "http://www.roundtable.it/uploads/tx_annuario/" + obj.getString("foto");

                    if (obj.getString("status").equalsIgnoreCase("Tabler attivo") && !obj.getString("inc_zona").equalsIgnoreCase("")) {

                        ZoneUserDetails details = new ZoneUserDetails();
                        details.setZona(obj.getString("zona"));
                        details.setFoto(image);
                        details.setTavola(tavole);
                        details.setCognome(obj.getString("cognome"));
                        details.setNome(obj.getString("nome"));
                        details.setData_nascita(obj.getString("data_nascita"));
                        details.setInc_zona(inc_zona);
                        details.setInc_nazionale(obj.getString("inc_nazionale"));
                        details.setCitta(citta);
                        details.setTel_cellulare(obj.getString("tel_cellulare"));
                        details.setTel_ufficio(obj.getString("tel_ufficio"));
                        details.setNewcitta(obj.getString("newname"));
                        details.setStatus(obj.getString("status"));
                        details.setCap(obj.getString("cap"));
                        details.setIndirizzo(obj.getString("indirizzo"));
                        details.setProfessione(obj.getString("professione"));
                        details.setEmail(obj.getString("email"));
                        details.setInc_tavola_01(obj.getString("inc_tavola_01"));
                        details.setInc_tavola_02(obj.getString("inc_tavola_02"));
                        details.setNome_moglie(obj.getString("nome_moglie"));
                        zoneDetailsArray.add(details);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listView.setAdapter(new TavoleDetailsAdapter(ZoneDetails.this, zoneDetailsArray));
        }
    }

    private class getDataElenco extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            zoneElencoArray = new ArrayList<>();
            pd = new ProgressDialog(ZoneDetails.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (Zone.equalsIgnoreCase("1")) {
                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tavole.php?zone=I");
            } else if (Zone.equalsIgnoreCase("2")) {
                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tavole.php?zone=II");
            } else if (Zone.equalsIgnoreCase("3")) {
                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tavole.php?zone=III");
            } else if (Zone.equalsIgnoreCase("4")) {
                return utils.getResponseofGet(Constant.BASE_URL + "get_zone_tavole.php?zone=V");
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONArray arry = new JSONArray(s.toString());
                for (int i = 0; i < arry.length(); i++) {
                    JSONObject object = arry.getJSONObject(i);
                    ItalianDetails details = new ItalianDetails();
                    details.setNumero(object.getString("numero"));
                    details.setNome(object.getString("nome"));
                    details.setZona(ZoneNumber);
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
                    details.setLat(object.getString("lat"));
                    details.setLng(object.getString("lng"));
                    String image = "http://www.roundtable.it/uploads/tx_annuario/" + object.getString("foto");
                    details.setFoto(image);
                    details.setEditor(object.getString("editor"));
                    details.setNote(object.getString("note"));
                    if (object.has("Presidente")) {
                        details.setPresident(object.getString("Presidente"));
                    } else {
                        details.setPresident("");
                    }

//                    Elencodetails detailsEle = new Elencodetails();
//                    detailsEle.setNumero(obj.getString("numero"));
//                    detailsEle.setNome(obj.getString("nome"));
//                    detailsEle.setZona(Zone);
//                    String image = "http://www.roundtable.it/uploads/tx_annuario/" + obj.getString("foto");
//                    detailsEle.setFoto(image);
                    zoneElencoArray.add(details);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Exception", e.toString());
            }
            listView.setAdapter(new ElencoAdapter(ZoneDetails.this, zoneElencoArray));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

