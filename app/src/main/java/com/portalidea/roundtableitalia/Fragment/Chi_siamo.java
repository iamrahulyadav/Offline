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
public class Chi_siamo extends Fragment {

    RelativeLayout scopi_round_relative, aims_object_relative;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chi_siamo, container, false);

        scopi_round_relative = (RelativeLayout) view.findViewById(R.id.fragment_chi_siamo_scopi_round);
        aims_object_relative = (RelativeLayout) view.findViewById(R.id.fragment_chi_siamo_aim_object);

        scopi_round_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RTIMain.screenCountArray.add("1.1");
                ScopiRound scopiRound = new ScopiRound();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (getFragmentManager().findFragmentById(R.id.framelayout) != null) {
                    transaction.replace(R.id.framelayout, scopiRound);
                } else {
                    transaction.add(R.id.framelayout, scopiRound);
                }
                transaction.commit();
            }
        });

        aims_object_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RTIMain.screenCountArray.add("1.2");
                AimsObjects fragmentAimsObjects = new AimsObjects();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (getFragmentManager().findFragmentById(R.id.framelayout) != null) {
                    transaction.replace(R.id.framelayout, fragmentAimsObjects);
                } else {
                    transaction.add(R.id.framelayout, fragmentAimsObjects);
                }
                transaction.commit();

            }
        });

        return view;
    }
}
