package com.mota.tribal.protsahan.PDF;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mota.tribal.protsahan.Helper.ConnectivityReceiver;
import com.mota.tribal.protsahan.R;

public class ViewPDFActivity extends AppCompatActivity {

    WebView mWebView;
    private Toolbar mToolbar;
    private ProgressDialog lt;
    private String LinkToPDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        lt = new ProgressDialog(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
        mToolbar = findViewById(R.id.mtoolbar);
        mToolbar.setTitle(R.string.view_pdf);
        setSupportActionBar(mToolbar);
        LinkToPDF = getIntent().getExtras().getString("link_to_pdf");
        try {
            lt.setMessage("Loading Pdf...");
            lt.show();
            if (new ConnectivityReceiver().isConnected()) {
                mWebView = findViewById(R.id.webView);
                mWebView.getSettings().setJavaScriptEnabled(true);

                mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + LinkToPDF);
                mWebView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        lt.hide();
                    }
                });
            } else {
                lt.hide();
                Snackbar.make(findViewById(R.id.rel), getResources().getString(R.string.ConnectionError), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void download(View view) {
//        final Boolean[] isInternetPresent = {false};
//        final ConnectionDetector[] cd = new ConnectionDetector[1];
//        cd[0] = new ConnectionDetector(getApplicationContext());
//        isInternetPresent[0] = cd[0].isConnectingToInternet();
        if (new ConnectivityReceiver().isConnected()) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LinkToPDF));
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(R.id.rel), getResources().getString(R.string.ConnectionError), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}