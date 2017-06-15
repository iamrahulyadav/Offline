package com.portalidea.roundtableitalia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.portalidea.roundtableitalia.Activity.ContactInfoZone;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.Model.ZoneUserDetails;
import com.portalidea.roundtableitalia.R;

import java.util.ArrayList;

/**
 * Created by archirayan on 6/11/2016.
 */

public class TavoleDetailsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    Context context;
    Utils utils;
    private ArrayList<ZoneUserDetails> array;

    public TavoleDetailsAdapter(Context context, ArrayList<ZoneUserDetails> array) {
        inflater = LayoutInflater.from(context);
        this.array = array;
        this.context = context;
        utils = new Utils(context);
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
        final ZoneUserDetails details = array.get(position);
//        final UserDetails details = name.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.adpater, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.adapter_username);
            holder.addressTv = (TextView) convertView.findViewById(R.id.adapter_details_address);
            holder.userImage = (ImageView) convertView.findViewById(R.id.adapter_username_img);
            holder.callIv = (ImageView) convertView.findViewById(R.id.call);
            holder.whatsappIv = (ImageView) convertView.findViewById(R.id.whatsapp);
            holder.emailIv = (ImageView) convertView.findViewById(R.id.email);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        http://www.roundtable.it/uploads/tx_annuario/RTSM%20-%20Logo%20trasparente_02.gif
        holder.text.setText(details.getNome() + " " + details.getCognome());
//        String imgURL = image.get(position);
        String imgURL = details.getFoto();
//        String imgURL =

        if (details.getTel_cellulare().length() > 0) {
            holder.whatsappIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_msg));
            holder.whatsappIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = "";
                    if (details.getTel_cellulare().length() < 1) {
                        number = details.getTel_ufficio();
                    } else {
                        number = details.getTel_cellulare();
                    }
                    Uri uri = Uri.parse("smsto:" + number);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "");
                    context.startActivity(it);
                }
            });
        } else {
            holder.whatsappIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dis_msg));
            holder.whatsappIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        if (imgURL.length() > 3) {
            if (imgURL.substring(imgURL.length() - 3, imgURL.length()).toString().equalsIgnoreCase("gif")) {
                Glide.with(context)
                        .load(Uri.parse(imgURL))
                        .asGif().placeholder(R.drawable.ic_placeholder).crossFade()
                        .into(holder.userImage);
            } else {
                Glide.with(context)
                        .load(imgURL)
                        .placeholder(R.drawable.ic_placeholder).crossFade()
                        .into(holder.userImage);
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ContactInfoZone.class);
                in.putExtra("status", details.getStatus());
                in.putExtra("tavola", details.getTavola());
                in.putExtra("newcitta", details.getNewcitta());
                in.putExtra("citta", details.getCitta());
                in.putExtra("cognome", details.getCognome());
                in.putExtra("nome", details.getNome());
                in.putExtra("cap", details.getCap());
                in.putExtra("data_nascita", details.getData_nascita());
                in.putExtra("indirizzo", details.getIndirizzo());
                in.putExtra("profession", details.getProfessione());
                in.putExtra("office", details.getTel_ufficio());
                in.putExtra("cellular", details.getTel_cellulare());
                in.putExtra("image", details.getFoto());
                in.putExtra("inc_zona", details.getInc_zona());
                in.putExtra("email", details.getEmail());
                in.putExtra("moglie", details.getNome_moglie());
                in.putExtra("provincia", details.getProvincia());
                in.putExtra("tavola1", details.getInc_tavola_01());
                in.putExtra("tavola2", details.getInc_tavola_02());
                in.putExtra("national", details.getInc_nazionale());
//                in.putExtra("Postal_Code", details.getPostal_Code());
//                in.putExtra("homephone", details.getHomephone());
//                in.putExtra("mobilephone", details.getMobilephone());
//                in.putExtra("workphone", details.getWorkphone());
//                in.putExtra("fax", details.getFax());
//                in.putExtra("address_home", details.getAddress_home());
//                in.putExtra("email", details.getEmail());
//                in.putExtra("password", details.getPassword());
//                in.putExtra("occupation", details.getOccupation());
//                in.putExtra("fb", details.getFb());
//                in.putExtra("lat", details.getLat());
//                in.putExtra("log", details.getLog());
//                in.putExtra("twitter", details.getTwitter());
//                in.putExtra("linkedin", details.getLinkedin());
//                in.putExtra("googleplus", details.getGoogleplus());
//                in.putExtra("deviceid", details.getDeviceid());
//                in.putExtra("status", details.getStatus());
                context.startActivity(in);
            }
        });


        holder.callIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + details.getTel_cellulare()));
                context.startActivity(intent);
            }
        });
        holder.emailIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailClient = new Intent(Intent.ACTION_SEND);
                mailClient.setPackage("com.google.android.gm");
                mailClient.setType("message/rfc822");
                String[] TO = {details.getEmail()};
                mailClient.putExtra(Intent.EXTRA_EMAIL, TO);
                if (mailClient != null) {
                    context.startActivity(mailClient);
                } else {
                    Toast.makeText(context, "Please install Gmail.", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        String tavolaString = Utils.getTavolaString(details.getInc_nazionale(), details.getInc_zona(), details.getInc_tavola_01(), details.getInc_tavola_02());
//        holder.addressTv.setText(incZona.get(position) + " - RT " + tavole.get(position) + citta.get(position));
        holder.addressTv.setText("Tabler attivo " + details.getInc_zona() + " di zona - RT " + details.getTavola() + " " + details.getNewcitta());
        return convertView;
    }


    class ViewHolder {
        TextView text, addressTv;
        ImageView callIv, whatsappIv, emailIv, userImage;
    }

}
