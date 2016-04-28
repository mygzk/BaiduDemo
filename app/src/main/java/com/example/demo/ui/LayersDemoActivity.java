package com.example.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.demo.R;

public class LayersDemoActivity extends BaseActivity {
    /**
     * MapView 是地图主控件
     */
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layers_demo);
        mMapView = (MapView)findViewById(R.id.layer_bmapView);
        mBaiduMap = mMapView.getMap();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map_moudle, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_map_normal:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.menu_map_weixing:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.menu_map_jiaotong:
                Log.e(TAG,"mBaiduMap.isTrafficEnabled():"+mBaiduMap.isTrafficEnabled());
                if(mBaiduMap.isTrafficEnabled()){
                    mBaiduMap.setTrafficEnabled(false);
                }else{
                    mBaiduMap.setTrafficEnabled(true);
                }
                break;
            case R.id.menu_map_reli:
                Log.e(TAG,"mBaiduMap.isBaiduHeatMapEnabled():"+mBaiduMap.isBaiduHeatMapEnabled());
                if(mBaiduMap.isBaiduHeatMapEnabled()){
                    mBaiduMap.setBaiduHeatMapEnabled(false);
                }else{
                    mBaiduMap.setBaiduHeatMapEnabled(true);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
