package com.portalidea.roundtableitalia.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.Activity.MapActivity;
import com.portalidea.roundtableitalia.R;
import com.bumptech.glide.Glide;

/**
 * Created by archirayan on 5/27/2016.
 */
public class ItalianDetailsFrgament extends Fragment {
    TextView nameTv, zonaTv, tavolaTv, chartermeetingTv, tavolamadrinaTv, gemellateTv, riunioniTv, ritrovoTv, webTv, emailTv, facebookTv, presidentTv;
    ImageView image;
String lat,lng;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_italian_detail, container, false);

        image = (ImageView) view.findViewById(R.id.fragment_italian_details_image);
        String numero = getArguments().getString("numero");
        String nome = getArguments().getString("nome");
        String zona = getArguments().getString("zona");
        String tavola_sciolta = getArguments().getString("tavola_sciolta");
        String madrina = getArguments().getString("madrina");
        String charter_meeting = getArguments().getString("charter_meeting");
        String gemellate = getArguments().getString("gemellate");
        String riunioni = getArguments().getString("riunioni");
        String ritrovo = getArguments().getString("ritrovo");
        String email = getArguments().getString("email");
        lat = getArguments().getString("lat");
        lng = getArguments().getString("lng");
        String web = getArguments().getString("web");
        String facebook = getArguments().getString("facebook");
        String twitter = getArguments().getString("twitter");
        String cap = getArguments().getString("cap");
        String fax = getArguments().getString("fax");
        String foto = getArguments().getString("foto");
        String editor = getArguments().getString("editor");
        String note = getArguments().getString("note");
        String President = getArguments().getString("President");

        nameTv = (TextView) view.findViewById(R.id.fragment_italian_details_name);
        zonaTv = (TextView) view.findViewById(R.id.fragment_italian_details_zona);
        tavolaTv = (TextView) view.findViewById(R.id.fragment_italian_details_tavola);
        chartermeetingTv = (TextView) view.findViewById(R.id.fragment_italian_details_charter);
        gemellateTv = (TextView) view.findViewById(R.id.fragment_italian_details_gemellate);
        riunioniTv = (TextView) view.findViewById(R.id.fragment_italian_details_riunioni);
        ritrovoTv = (TextView) view.findViewById(R.id.fragment_italian_details_ritrova);
        webTv = (TextView) view.findViewById(R.id.fragment_italian_details_web);
        emailTv = (TextView) view.findViewById(R.id.fragment_italian_details_email);
        facebookTv = (TextView) view.findViewById(R.id.fragment_italian_details_facebook);
        presidentTv = (TextView) view.findViewById(R.id.fragment_italian_details_president);
        tavolamadrinaTv = (TextView) view.findViewById(R.id.fragment_italian_details_tavola_madrina);

        nameTv.setText("Round Table " + numero + " " + nome);
        zonaTv.setText(Html.fromHtml("<b> Zona" + "</b>" + " |" + zona));
        tavolaTv.setText(Html.fromHtml("<b> Tavola" + "</b>" + " |" + numero));
        chartermeetingTv.setText(Html.fromHtml("<b> Charter Meeting " + "</b>" + " |" + charter_meeting));
        tavolamadrinaTv.setText(Html.fromHtml("<b> Tavola Madrina " + "</b>" + " |" + madrina));
        gemellateTv.setText(Html.fromHtml("<b> Gemellate " + "</b>" + " |" + gemellate));
        riunioniTv.setText(Html.fromHtml("<b> Riunioni " + "</b>" + " |" + riunioni));
        ritrovoTv.setText(Html.fromHtml("<b> Ritrovo " + "</b>" + " |<font color=\"#3A77E6\"><a>" + ritrovo + "</a></font>"));
        webTv.setMovementMethod(LinkMovementMethod.getInstance());
        webTv.setText(Html.fromHtml("<b> Web " + "</b>" + " |<font color=\"#3A77E6\"><a> " + web + "</a></font>"), TextView.BufferType.SPANNABLE);
        emailTv.setMovementMethod(LinkMovementMethod.getInstance());
        emailTv.setText(Html.fromHtml("<b> Email " + "</b>" + " |<font color=\"#3A77E6\"> <a href=\"mailto:" + email + "\">" + email + "</a></font>"), TextView.BufferType.SPANNABLE);

        facebookTv.setText(Html.fromHtml("<b> Facebook " + "</b>" + " |" + facebook));
        presidentTv.setText(Html.fromHtml("<b> President " + "</b>" + " |" + President));

        String imageURL = ("http://www.roundtable.it/uploads/tx_annuario/" + foto).replace(" ", "%20");
        if (imageURL.substring(imageURL.length() - 3, imageURL.length()).equalsIgnoreCase("gif")) {
            Glide.with(getActivity())
                    .load(imageURL).asGif()
                    .placeholder(R.drawable.ic_placeholder).crossFade()
                    .into(image);
        } else {
            Glide.with(getActivity())
                    .load(imageURL)
                    .placeholder(R.drawable.ic_placeholder).crossFade()
                    .into(image);
        }

        ritrovoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "LAT LNG"+lat+" "+lng, Toast.LENGTH_SHORT).show();
                if (!lat.equalsIgnoreCase("")) {

                    Intent in = new Intent(getActivity(), MapActivity.class);
                    in.putExtra("lat",lat);
                    in.putExtra("lng",lng);
                    startActivity(in);

                } else {

                    Toast.makeText(getActivity(), "Invalid Address", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }
}
