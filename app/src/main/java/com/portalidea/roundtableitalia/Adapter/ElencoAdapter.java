package com.portalidea.roundtableitalia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Activity.RoundTableItaliaActivity;
import com.portalidea.roundtableitalia.Model.ItalianDetails;
import com.portalidea.roundtableitalia.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * Created by Rujul on 27-Dec-15.
 */
public class ElencoAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<ItalianDetails> array;

    public ElencoAdapter(Context context, ArrayList<ItalianDetails> array) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.adapter_zone_elenco, parent, false);
        final ItalianDetails details = array.get(position);
        TextView nameTv = (TextView) v.findViewById(R.id.adapter_zone_elenco_name);
        TextView textTv = (TextView) v.findViewById(R.id.adapter_zone_elenco_text);
        ImageView image = (ImageView) v.findViewById(R.id.adapter_zone_elenco_image);

        nameTv.setText("RT" + details.getNumero() + " " + details.getNome());
        textTv.setText("ZONA " + details.getZona());
        String imgURL = details.getFoto();
        if (imgURL.length() > 3) {
            if (imgURL.substring(imgURL.length() - 3, imgURL.length()).toString().equalsIgnoreCase("gif")) {
                Glide.with(context)
                        .load(Uri.parse(imgURL))
                        .asGif().placeholder(R.drawable.ic_placeholder).crossFade()
                        .into(image);
            } else {
                Glide.with(context)
                        .load(imgURL)
                        .placeholder(R.drawable.ic_placeholder).crossFade()
                        .into(image);
            }
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RoundTableItaliaActivity.class);
                intent.putExtra("numero", details.getNumero());
                intent.putExtra("nome", details.getNome());
                intent.putExtra("zona", details.getZona());
                intent.putExtra("tavola_sciolta", details.getTavola_sciolta());
                intent.putExtra("madrina", details.getMadrina());
                intent.putExtra("charter_meeting", details.getCharter_meeting());
                intent.putExtra("gemellate", details.getGemellate());
                intent.putExtra("riunioni", details.getRiunioni());
                intent.putExtra("ritrovo", details.getRitrovo());
                intent.putExtra("email", details.getEmail());
                intent.putExtra("web", details.getWeb());
                intent.putExtra("facebook", details.getFacebook());
                intent.putExtra("twitter", details.getTwitter());
                intent.putExtra("cap", details.getCap());
                intent.putExtra("fax", details.getFax());
                intent.putExtra("foto", details.getFoto());
                intent.putExtra("editor", details.getEditor());
                intent.putExtra("note", details.getNote());
                intent.putExtra("lat", details.getLat());
                intent.putExtra("lng", details.getLng());
                intent.putExtra("President", details.getPresident());

                context.startActivity(intent);

            }
        });
        return v;
    }
}
