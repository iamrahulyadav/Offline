package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.portalidea.roundtableitalia.R;

/**
 * Created by archirayan on 5/26/2016.
 */
public class Web extends Activity {
    String URL;
    WebView webView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.actionBarTitle)).setText(R.string.app_name);

        webView = (WebView) findViewById(R.id.activity_web_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebViewClient());
        progress = (ProgressBar) findViewById(R.id.progressBar_MyAccountActivity);
        progress.setMax(100);

        if (getIntent().getExtras() != null) {

            URL = getIntent().getExtras().getString("URL");

        }
        webView.loadUrl(URL);
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }
}

