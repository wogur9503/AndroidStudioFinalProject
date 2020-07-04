package com.cookandroid.n2016316029final;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.textclassifier.TextClassifierEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    static final LatLng SEOUL = new LatLng(37.5665, 126.9780);
    static final LatLng KONKUKSTATION = new LatLng(37.5403978,127.0691641);
    static final LatLng GOODMORNINGDENTAL = new LatLng(37.5398803,127.0698732);
    static final LatLng ANGELINUS = new LatLng(37.5402419,127.0704849);
    static final LatLng IBK = new LatLng(37.5404517,127.0697205);
    Button btnBack, btnBack2, btnBack3;
    ImageView iv, iv2, iv3;
    private GoogleMap map;
    Marker seoul, konkukstation, goodmorningdental, angelinus, ibk;
    GroundOverlayOptions pointMark;
    LatLng myPosition;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.iv);
        iv2 = (ImageView)findViewById(R.id.iv2);
        iv3 = (ImageView)findViewById(R.id.iv3);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager locationManager
                = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria(); // 베스트 프로바이더 기준
        String provider = locationManager.getBestProvider(criteria, true);
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);


        if(location != null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            myPosition = new LatLng(latitude, longitude);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pointMark = new GroundOverlayOptions().image(BitmapDescriptorFactory
                .fromResource(R.drawable.marker)).position(latLng, 100f, 100f);
                map.addGroundOverlay(pointMark);
            }
        });
        seoul = map.addMarker(new MarkerOptions().position(SEOUL).title("서울"));
        goodmorningdental = map.addMarker(new MarkerOptions().position(GOODMORNINGDENTAL).title("굿모닝치과"));
        angelinus = map.addMarker(new MarkerOptions().position(ANGELINUS).title("앤젤리너스"));
        konkukstation = map.addMarker(new MarkerOptions().position(KONKUKSTATION).title("건대입구역"));
        ibk = map.addMarker(new MarkerOptions().position(IBK).title("기업은행"));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                if(marker.equals(angelinus)) {
                    Intent intent = new Intent(MainActivity.this, Information.class);
                    startActivity(intent);
                }
                if(marker.equals(goodmorningdental)) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
                if(marker.equals(ibk)) {
                    Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KONKUKSTATION, 100));
        map.animateCamera(CameraUpdateFactory.zoomTo(50), 2000, null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
