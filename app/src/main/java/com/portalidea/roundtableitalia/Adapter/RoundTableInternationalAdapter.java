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

import com.portalidea.roundtableitalia.Activity.Web;
import com.portalidea.roundtableitalia.Model.InternationalDetails;
import com.portalidea.roundtableitalia.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by archirayan on 5/2/2016.
 */
public class RoundTableInternationalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<InternationalDetails> array;
    Context context;

    public RoundTableInternationalAdapter(Context context, ArrayList<InternationalDetails> array) {
        inflater = LayoutInflater.from(context);
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
        ViewHolder holder;
        InternationalDetails details;
        details = array.get(position);
        if (convertView == null) {
            holder = new ViewHolder();


            convertView = inflater.inflate(R.layout.adapter_round_table_international, parent, false);
            holder.nameTv = (TextView) convertView.findViewById(R.id.adapter_round_table_international_name);
            holder.textTv = (TextView) convertView.findViewById(R.id.adapter_round_table_international_text);
            holder.userImage = (ImageView) convertView.findViewById(R.id.adapter_round_table_international_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        http://www.roundtable.it/uploads/tx_annuario/RTSM%20-%20Logo%20trasparente_02.gif
        holder.textTv.setText(details.getText());
        holder.nameTv.setText(details.getName());
        Glide.with(context)
                .load(Uri.parse(array.get(position).getImage()))
                .asGif().placeholder(R.drawable.ic_placeholder).crossFade()
                .into(holder.userImage);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Web.class);
                in.putExtra("URL", array.get(position).getUrl());
                context.startActivity(in);
            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView textTv, nameTv;
        ImageView userImage;
    }

}
