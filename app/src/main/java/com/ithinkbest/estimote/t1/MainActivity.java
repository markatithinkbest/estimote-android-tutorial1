package com.ithinkbest.estimote.t1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // TODO: replace "<major>:<minor>" strings to match your own beacons.
    private static final Map<String, List<String>> PLACES_BY_BEACONS;

    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("12345:1001", new ArrayList<String>() {{
            add("AAA");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            add("BBB");
            // "Green & Green Salads" is the next closest
            add("CCC");
            // "Mini Panini" is the furthest away
        }});
        placesByBeacons.put("12345:1002", new ArrayList<String>() {{
            add("BBB");
            add("AAA");
            add("CCC");
        }});
        placesByBeacons.put("12345:1003", new ArrayList<String>() {{
            add("CCC");
            add("BBB");
            add("AAA");
        }});
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }
        return Collections.emptyList();
    }

    private BeaconManager beaconManager;
    private Region region;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView=(WebView)findViewById(R.id.webView);

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                String htmlText ="<h1>有效範圍內目前沒有店家</h1><h1>請稍等幾秒鐘或走到能偵測的位置</h1>";
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    List<String> places = placesNearBeacon(nearestBeacon);
                    // TODO: update the UI here
                    Log.d("markchenAirport", "Nearest places: " + places);
                   // for (String p:places){
                    htmlText="<h1>最近的店家是</h1><h1>"+places.get(0)+"</h1><hr>";
                    htmlText+="<h2>其次是</h2><h2>"+places.get(1)+"</h2><hr>";;
                    htmlText+="<h3>最遠的是</h3><h3>"+places.get(2)+"</h3>";;

                    // }
                }else{

                }
                webView.loadData(htmlText , "text/html; charset=UTF-8", null);
            }
        });

//        region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 12345, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
       beaconManager.stopRanging(region);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
