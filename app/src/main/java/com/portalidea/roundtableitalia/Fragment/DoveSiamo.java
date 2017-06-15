package com.portalidea.roundtableitalia.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.portalidea.roundtableitalia.Activity.RTIMain;
import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 5/23/2016.
 */
public class DoveSiamo extends Fragment {
    RelativeLayout international, italian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dove_siamo_list, container, false);


        international = (RelativeLayout) view.findViewById(R.id.fragment_dove_siamo_international);
        italian = (RelativeLayout) view.findViewById(R.id.fragment_dove_siamo_italian);

        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RTIMain.screenCountArray.add("3.1");
                RoundTableInternational myf = new RoundTableInternational();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout, myf);
                transaction.commit();
            }
        });

        italian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RTIMain.screenCountArray.add("3.2");
                RoundTableItalian myf = new RoundTableItalian();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout, myf);
                transaction.commit();
            }
        });

        return view;
    }
}
