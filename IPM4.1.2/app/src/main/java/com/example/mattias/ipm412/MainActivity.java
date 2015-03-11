package com.example.mattias.ipm412;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLink1 = (Button) findViewById(R.id.button1);
        Button buttonLink2 = (Button) findViewById(R.id.button2);
        final WebView browser = (WebView) findViewById(R.id.webView);

        buttonLink1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //laddar angiven Url i WebViewn
                browser.loadUrl("https://www.google.se/?gws_rd=ssl");
            }
        });

        buttonLink2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                browser.loadUrl("http://www.aftonbladet.se/");

            }
        });
    }

}
