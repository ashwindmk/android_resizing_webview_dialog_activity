package ashwin.examples.com.resizingwebviewdialogactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by ashwin on 02/07/18.
 */

public class WebViewDialogActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        final RelativeLayout rootLayout = new RelativeLayout(WebViewDialogActivity.this);
        RelativeLayout.LayoutParams rootLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootLayoutParams);
        webView = new WebView(WebViewDialogActivity.this);
        final LinearLayout.LayoutParams webviewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(webviewLayoutParams);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("debug-logging", "loading url: " + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:webdialog.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
            }
        });
        webView.addJavascriptInterface(this, "webdialog");

        rootLayout.addView(webView);

        setContentView(rootLayout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels - 100;
        int height = (int) ((65.0f/100.0f) * displayMetrics.heightPixels);
        getWindow().setLayout(width, height);

        // load url
        String url = getIntent().getDataString();
        webView.loadUrl(url);
    }

    @JavascriptInterface
    public void resize(final float height) {
        WebViewDialogActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int deviceHeight = displayMetrics.heightPixels;
                int newHeight = Math.min(deviceHeight - 200, (int) ((height * getResources().getDisplayMetrics().density) + 200));
                int width = getResources().getDisplayMetrics().widthPixels - 100;
                getWindow().setLayout(width, newHeight);
            }
        });
    }

    // remove this overridden method onBackPressed() to dismiss dialog on back pressed
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
