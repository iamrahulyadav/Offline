package com.portalidea.roundtableitalia.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.portalidea.roundtableitalia.Activity.RTIMain;
import com.portalidea.roundtableitalia.Fragment.ItalianDetailsFrgament;
import com.portalidea.roundtableitalia.Model.ItalianDetails;
import com.portalidea.roundtableitalia.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by archirayan on 5/2/2016.
 */
public class RoundTableItalianAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ItalianDetails> array;
    Context context;
    FragmentManager fm;

    public RoundTableItalianAdapter(Context context, ArrayList<ItalianDetails> array, FragmentManager fm) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.array = array;
        this.fm = fm;
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
        ViewHolder holder;
        final ItalianDetails details = array.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.adapter_round_table_italian, parent, false);
            holder.nameTv = (TextView) convertView.findViewById(R.id.adapter_round_table_italian_name);
            holder.textTv = (TextView) convertView.findViewById(R.id.adapter_round_table_italian_text);
            holder.userImage = (ImageView) convertView.findViewById(R.id.adapter_round_table_italian_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(Html.fromHtml("<b> RT" + details.getNumero() + " " + details.getNome() + "</b>"));
        holder.textTv.setText("Zona " + details.getZona());
        String imageURL = ("http://www.roundtable.it/uploads/tx_annuario/" + array.get(position).getFoto()).replace(" ", "%20");

        Log.d("substring", imageURL.substring(imageURL.length() - 3, imageURL.length()));

        if (imageURL.substring(imageURL.length() - 3, imageURL.length()).equalsIgnoreCase("gif")) {
            Log.d("GIFIMAGE", imageURL);
            Glide.with(context)
                    .load(imageURL).asGif()
                    .placeholder(R.drawable.ic_placeholder).crossFade()
                    .into(holder.userImage);
        } else {
            Log.d("REGULARIMAGE", imageURL);
            Glide.with(context)
                    .load(imageURL)
                    .placeholder(R.drawable.ic_placeholder).crossFade()
                    .into(holder.userImage);
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItalianDetailsFrgament fragment = new ItalianDetailsFrgament();
                RTIMain.screenCountArray.add("3.2.1");
                Bundle bundle = new Bundle();
                bundle.putString("numero", details.getNumero());
                bundle.putString("nome", details.getNome());
                bundle.putString("zona", details.getZona());
                bundle.putString("tavola_sciolta", details.getTavola_sciolta());
                bundle.putString("madrina", details.getMadrina());
                bundle.putString("charter_meeting", details.getCharter_meeting());
                bundle.putString("gemellate", details.getGemellate());
                bundle.putString("riunioni", details.getRiunioni());
                bundle.putString("lat", details.getLat());
                bundle.putString("lng", details.getLng());
                bundle.putString("ritrovo", details.getRitrovo());
                bundle.putString("email", details.getEmail());
                bundle.putString("web", details.getWeb());
                bundle.putString("facebook", details.getFacebook());
                bundle.putString("twitter", details.getTwitter());
                bundle.putString("cap", details.getCap());
                bundle.putString("fax", details.getFax());
                bundle.putString("foto", details.getFoto());
                bundle.putString("editor", details.getEditor());
                bundle.putString("note", details.getNote());
                bundle.putString("President", details.getPresident());

                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.framelayout, fragment).commit();
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView textTv, nameTv;
        ImageView userImage;
    }

}
