package com.portalidea.roundtableitalia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.portalidea.roundtableitalia.Activity.ContactInfo;
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.Model.UserDetails;
import com.portalidea.roundtableitalia.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by archirayan on 5/2/2016.
 */
public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private LayoutInflater inflater;
    private ArrayList<UserDetails> name;
    Context context;
    Utils utils;

    public MyAdapter(Context context, ArrayList<UserDetails> name) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.name = name;
        utils = new Utils(context);
        Log.d("Size-",""+name.size());
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final UserDetails details = name.get(position);
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
        String tavolaString = Utils.getTavolaString(details.getNationalID(), details.getInc_area(), details.getTavola1(), details.getTavola2());
//        http://www.roundtable.it/uploads/tx_annuario/RTSM%20-%20Logo%20trasparente_02.gif
        holder.addressTv.setText("" + details.getStatus() + " " + tavolaString + " RT" + details.getTavola() + " " + details.getClub_city_name());
        holder.text.setText(details.getName() + " " + details.getSurname());
        String imgURL = details.getPhoto();
        if (imgURL.length() > 3) {
            if (imgURL.substring(imgURL.length() - 3, imgURL.length()).equalsIgnoreCase("gif")) {
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

        if (details.getIs_enable().equalsIgnoreCase("0")) {
            holder.whatsappIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_dis_msg));
            holder.whatsappIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            holder.whatsappIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_msg));

            holder.whatsappIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = "";
                    if (details.getMobilephone().length() < 1) {
                        number = details.getHomephone();
                    } else {
                        number = details.getMobilephone();
                    }
                    Uri uri = Uri.parse("smsto:" + number);
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", "");
                    context.startActivity(it);
                }
            });
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ContactInfo.class);
                in.putExtra(Constant.UserName, details.getName());
                in.putExtra("id", details.getId());
                in.putExtra("social_class", details.getSocial_class());
                in.putExtra("areaid", details.getAreaid());
                in.putExtra("zoneid", details.getZoneid());
                in.putExtra("clubid", details.getClubid());
                in.putExtra("nationalID", details.getNationalID());
                in.putExtra("surname", details.getSurname());
                in.putExtra("dob", details.getDob());
                in.putExtra("tavola", details.getTavola());
                in.putExtra("city", details.getCity());
                in.putExtra("profession", details.getProfession());
                in.putExtra("province", details.getProvince());
                in.putExtra("tel_phone", details.getTel_phone());
                in.putExtra("name_wife", details.getName_wife());
                in.putExtra("photo", details.getPhoto());
                in.putExtra("inc_area", details.getInc_area());
                in.putExtra("Postal_Code", details.getPostal_Code());
                in.putExtra("homephone", details.getHomephone());
                in.putExtra("mobilephone", details.getMobilephone());
                in.putExtra("workphone", details.getWorkphone());
                in.putExtra("fax", details.getFax());
                in.putExtra("address_home", details.getAddress_home());
                in.putExtra("email", details.getEmail());
                in.putExtra("password", details.getPassword());
                in.putExtra("occupation", details.getOccupation());
                in.putExtra("fb", details.getFb());
                in.putExtra("lat", details.getLat());
                in.putExtra("log", details.getLog());
                in.putExtra("twitter", details.getTwitter());
                in.putExtra("linkedin", details.getLinkedin());
                in.putExtra("googleplus", details.getGoogleplus());
                in.putExtra("deviceid", details.getDeviceid());
                in.putExtra("status", details.getStatus());
                in.putExtra("club_city_name", details.getClub_city_name());
                in.putExtra("tavola1", details.getTavola1());
                in.putExtra("tavola2", details.getTavola2());
                context.startActivity(in);
            }
        });


        holder.callIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (details.getHomephone().length() > 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + details.getHomephone()));
                    context.startActivity(intent);
                } else if (details.getMobilephone().length() > 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + details.getMobilephone()));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Mobile no is not valid.", Toast.LENGTH_SHORT).show();
                }
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

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {

            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header_textview);
            convertView.setTag(holder);

        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + name.get(position).getSurname().subSequence(0, 1).charAt(0);
        holder.text.setText(headerText.toUpperCase());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return name.get(position).getSurname().subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text, addressTv;
        ImageView callIv, whatsappIv, emailIv, userImage;
    }

}
