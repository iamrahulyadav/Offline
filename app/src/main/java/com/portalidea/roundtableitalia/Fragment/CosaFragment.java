package com.portalidea.roundtableitalia.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan1 on 5/24/2016.
 */
public class CosaFragment extends Fragment {
    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cosa, container, false);
        webview = (WebView) view.findViewById(R.id.wvCosa_COSAfragment);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/cosa_facciamo.html");

        return view;
    }


}
