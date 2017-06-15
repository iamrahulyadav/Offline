package com.portalidea.roundtableitalia.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Constant.ConnectionDetector;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by archirayan on 6/13/2016.
 */
public class AddContact extends Fragment implements View.OnClickListener {
    private EditText nomeEdt, cognomeEdt, dataDiEdt, cittaEdt, teleFonoEdt, emailEdt, noteEdt;
    private Button addContactBtn;
    private ConnectionDetector cd;
    private Utils utils;
    public TextView privacyTv;
    public String nome, cognome, dataDi, citta, teleFono, email, note;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        nomeEdt = (EditText) view.findViewById(R.id.fragment_add_contact_nome);
        cognomeEdt = (EditText) view.findViewById(R.id.fragment_add_contact_congome);
        dataDiEdt = (EditText) view.findViewById(R.id.fragment_add_contact_data_di);
        cittaEdt = (EditText) view.findViewById(R.id.fragment_add_contact_citta);
        teleFonoEdt = (EditText) view.findViewById(R.id.fragment_add_contact_telefono);
        emailEdt = (EditText) view.findViewById(R.id.fragment_add_contact_email);
        noteEdt = (EditText) view.findViewById(R.id.fragment_add_contact_note);
        utils = new Utils(getActivity());
        privacyTv = (TextView) view.findViewById(R.id.fragment_add_contact_policy);
        privacyTv.setText(Html.fromHtml("Acconsento al trattamento dei miei dati personali e dichiaro di aver letto la " +
                "<a href='http://www.roundtable.it/contatti/'>privacy policy</a>"));
        privacyTv.setClickable(true);
        privacyTv.setMovementMethod(LinkMovementMethod.getInstance());
        cd = new ConnectionDetector(getActivity());
        addContactBtn = (Button) view.findViewById(R.id.fragment_add_contact_invia);
        addContactBtn.setOnClickListener(this);


//        http://181.224.157.105/~hirepeop/host1/OffilineModeApps/Api/mail.php?Nome=1&Cognome=2&Datad_di_Nascita=3&Citta=4&Telefono=5&Email=5&Note=6
        return view;
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_add_contact_invia:


                email = emailEdt.getText().toString();
                note = noteEdt.getText().toString();
                nome = nomeEdt.getText().toString();
                cognome = cognomeEdt.getText().toString();
                dataDi = dataDiEdt.getText().toString();
                citta = cittaEdt.getText().toString();
                teleFono = teleFonoEdt.getText().toString();
                if (checkEmail(email) && note.length() > 0 && nome.length() > 0 && cognome.length() > 0 && dataDi.length() > 0 && citta.length() > 0 && teleFono.length() > 0 && note.length() > 0) {
                    if (cd.isConnectingToInternet()) {
                        new sendData().execute();
                    } else {
                        cd.connectiondetect();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.app_name)
                            .setMessage(R.string.warningallfieldrequires)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }


                break;

        }
    }

    private class sendData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
//            http://181.224.157.105/~hirepeop/host1/OffilineModeApps/Api/mail.php?Nome=1&Cognome=2&Datad_di_Nascita=3&Citta=4&Telefono=5&Email=5&Note=6
            return utils.getResponseofGet(Constant.BASE_URL + "mail.php?Nome=" + nome + "&Cognome=" + cognome + "&Datad_di_Nascita=" + dataDi + "&Citta=" + citta + "&Telefono=" + teleFono + "&Email=" + email + "&Note=" + note);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response", s.toString());
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s.toString());
                if (object.getString("status").equalsIgnoreCase("true")) {
                    Toast.makeText(getActivity(), "Il messaggio è stato inviato correttamente", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Exception", e.toString());
            }
        }
    }
}
