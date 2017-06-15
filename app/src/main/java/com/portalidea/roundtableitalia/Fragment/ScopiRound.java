package com.portalidea.roundtableitalia.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 5/24/2016.
 */
public class ScopiRound extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_scopi_round, container, false);

        return view;
    }
}
