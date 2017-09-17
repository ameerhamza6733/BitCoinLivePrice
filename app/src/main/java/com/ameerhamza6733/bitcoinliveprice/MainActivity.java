package com.ameerhamza6733.bitcoinliveprice;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView priceLive,currencyType,status,timeStamp;
    private Handler handler;

    private Runnable updateTask;
    private ProgressBar prograssBar;
    private String CurrencyType ="USD";
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        priceLive= (TextView) findViewById(R.id.live_price_textView);
        currencyType= (TextView) findViewById(R.id.curncy_type_textView);
        status= (TextView) findViewById(R.id.status_textView);
        timeStamp= (TextView) findViewById(R.id.time_stamp_textView);
        prograssBar = (ProgressBar) findViewById(R.id.progressBar);
        intiVolley(CurrencyType);


        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        myHandler();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5168564707064012/1381700130");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void myHandler() {
        handler = new Handler();

        updateTask = new Runnable() {
            @Override
            public void run() {

                // liveScrFramgment.this.mSwipeRefrashLayout.setRefreshing(true);
              intiVolley( CurrencyType);
                MainActivity.this.handler.postDelayed(this, 10000);
            }
        };

        handler.postDelayed(updateTask, 10000);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.BTCUSD) {
            updateCurrencyType("USD");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
            // Handle the camera action
        } else if (id == R.id.BTCEUR) {
            updateCurrencyType("EUR");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();

        } else if (id == R.id.BTCINR) {
            updateCurrencyType("INR");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        } else if (id == R.id.BTCJPY) {
            updateCurrencyType("JPY");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        } else if (id == R.id.BTCGBP) {
            updateCurrencyType("GBP");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        } else if (id == R.id.BTCAUD) {
            updateCurrencyType("AUD");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        }else if(id == R.id.BTCCAD){
            updateCurrencyType("CAD");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        }else if(id == R.id.BTCCHF){
            updateCurrencyType("CHF");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        }
        else if(id == R.id.BTCCNY){
            updateCurrencyType("CNY");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        }
        else if(id == R.id.BTCRUB){
            updateCurrencyType("RUB");
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateCurrencyType(String type) {
        Toast.makeText(this,"Currency type = "+type,Toast.LENGTH_LONG).show();
        CurrencyType=type;
        intiVolley(CurrencyType);
        myHandler();
    }

    private void intiVolley(final String CurrencyType){
        prograssBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC&tsyms="+CurrencyType,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            prograssBar.setVisibility(View.INVISIBLE);
                            Log.d("onResponse","Volley"+response.getJSONObject("BTC").getDouble(CurrencyType));
                           priceLive.setText(String.valueOf(response.getJSONObject("BTC").getDouble(CurrencyType)));
                           currencyType.setText("BTC-"+CurrencyType);
//                            if(response.getString("c").startsWith("+"))
//                                status.setTextColor(Color.parseColor("#4caf50"));
//                            else
//                                status.setTextColor(Color.parseColor("#f44336"));
//                            status.setText(response.getString("c"));
//                            status.setText(response.getString("c"));
//                            timeStamp.setText(response.getString("lt"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Toast.makeText(MainActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );

      RequestQueue requestQueue=  Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivty","onDestroy removing callbacks...");
        handler.removeCallbacks(updateTask);
        super.onDestroy();

    }

    @Override
    protected void onRestart() {
        Log.d("MainActivty","onRestart");
        super.onRestart();

    }
}
