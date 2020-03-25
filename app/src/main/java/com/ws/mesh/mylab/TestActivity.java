package com.ws.mesh.mylab;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends FragmentActivity {

    private LocationManager locationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @OnClick(R.id.btn_location)
    public void onLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation("network");
        SunriseSunset sunriseSunset = new SunriseSunset(
                TimeZone.getDefault(), location.getLatitude(), location.getLongitude());
        Log.i("LocationUtil", "LocationUtil: " + location.getLatitude());

        String sunRise = sunriseSunset.getOfficialSunrise(Calendar.getInstance());
        String sunSet = sunriseSunset.getOfficialSunset(Calendar.getInstance());

        Log.i("AABBCC", "onLocate: lat --> " + location.getLatitude() +
                " lng --> " + location.getLongitude() +
                " sunRise --> " + sunRise +
                " sunSet --> " + sunSet);
    }
}
