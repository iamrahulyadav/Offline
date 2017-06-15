package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan1 on 5/24/2016.
 */
public class DocumentLoad extends Activity {
    private WebView webview;
    String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cosa);
        (findViewById(R.id.fragment_cosa_linear)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Documenti");
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        URL = getIntent().getExtras().getString("URL");
        webview = (WebView) findViewById(R.id.wvCosa_COSAfragment);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + URL);
    }
}
