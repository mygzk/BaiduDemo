package com.example.demo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.demo.R;

public class MapControlActivity extends BaseActivity implements View.OnClickListener {

    private MapView mMapview;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;

    private TextView tvZoom;
    private TextView tvRatate;
    private TextView tvOverlook;
    private EditText etZoom;
    private EditText etRatate;
    private EditText etOverlook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_control);
        mMapview = (MapView) findViewById(R.id.map_control_control_map);
        mBaiduMap = mMapview.getMap();
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MapStatus mapStatus = mBaiduMap.getMapStatus();
                updateMap(mapStatus.zoom, mapStatus.rotate, mapStatus.overlook,latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);
        initView();
    }

    private void initView() {
        tvZoom = (TextView) findViewById(R.id.tv_zoom);
        tvRatate = (TextView) findViewById(R.id.tv_rotate);
        tvOverlook = (TextView) findViewById(R.id.tv_overlook);
        etZoom = (EditText) findViewById(R.id.tv_zoom_value);
        etRatate = (EditText) findViewById(R.id.tv_rotate_value);
        etOverlook = (EditText) findViewById(R.id.tv_overlook_value);

        tvZoom.setOnClickListener(this);
        tvRatate.setOnClickListener(this);
        tvOverlook.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapview.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zoom:
                zoomMap();
                break;
            case R.id.tv_rotate:
                rotateMap();
                break;
            case R.id.tv_overlook:
                overlookMap();
                break;
        }
    }

    private void zoomMap() {
        //放大级别3~21
        float zoomValue = 0.0f;
        if (!TextUtils.isEmpty(etZoom.getText())) {
            zoomValue = Float.valueOf(etZoom.getText().toString());
        }
        MapStatus mapStatus = mBaiduMap.getMapStatus();
        updateMap(zoomValue, mapStatus.rotate, mapStatus.overlook,mapStatus.target);

    }

    private void overlookMap() {
        // -45~0
        float overlookValue = 0.0f;
        if (!TextUtils.isEmpty(etOverlook.getText())) {
            overlookValue = Float.valueOf("-"+etOverlook.getText().toString());
        }

        MapStatus mapStatus = mBaiduMap.getMapStatus();
        updateMap(mapStatus.zoom, mapStatus.rotate, overlookValue,mapStatus.target);
    }

    private void rotateMap() {
        float rotateValue = 0.0f;
        if (!TextUtils.isEmpty(etRatate.getText())) {
            rotateValue = Float.valueOf(etRatate.getText().toString());
        }

        MapStatus mapStatus = mBaiduMap.getMapStatus();
        updateMap(mapStatus.zoom, rotateValue, mapStatus.overlook,mapStatus.target);

    }


    private void updateMap(float zoomValue, float rotatevalue, float overlookValue,LatLng targ) {
        MapStatus mapStatus = new MapStatus
                .Builder(mBaiduMap.getMapStatus())
                .rotate(rotatevalue)
                .zoom(zoomValue)
                .overlook(overlookValue)
                .target(targ)
                .build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
    }
}
