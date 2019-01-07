package com.example.demo.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.CheckBox;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.example.demo.R;

public class OverlayDemo extends BaseActivity implements SensorEventListener {

    private TextureMapView mMapView;
    private MapView mMapView1;
    private BaiduMap mBaiduMap;

    // 定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;//精准度
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData; //定位数据




    //传感器
    private SensorManager mSensorManager;
    //方向角度
    private int mCurrentDirection = 0;


    private MyLocationConfiguration.LocationMode mCurrentMode;//当前位置图标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay_demo);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务


        initView();
        initLocalClient();
    }


    /**
     * 定位相关
     */
    private void initLocalClient() {

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();


    }

    private void initView() {
        mMapView = (TextureMapView) findViewById(R.id.overlay_texturemapview);
        mMapView1 = (MapView) findViewById(R.id.overlay_mapview);

        mBaiduMap = mMapView.getMap();
      //  mBaiduMap = mMapView1.getMap();


        mBaiduMap.setMyLocationEnabled(true);

        //地图设置
        UiSettings mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setAllGesturesEnabled(true);//开启手势
        mUiSettings.setScrollGesturesEnabled(true);//平移
        mUiSettings.setZoomGesturesEnabled(true);//放缩
        mUiSettings.setRotateGesturesEnabled(true);//旋转
        mUiSettings.setOverlookingGesturesEnabled(true);//俯视
        mUiSettings.setCompassEnabled(true);//是否启用指南针图层


        mBaiduMap.showMapPoi(true);//是否显示底图默认标注


        MapStatus ms = new MapStatus.Builder().build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
        mBaiduMap.animateMapStatus(u, 1000);

        mCurrentMode =MyLocationConfiguration.LocationMode.NORMAL;
        //设置 当前位置图标
//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                        mCurrentMode, true, null));



        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, null));

        // 修改为自定义marker
//       int accuracyCircleFillColor = 0xAAFFFF88;
//       int accuracyCircleStrokeColor = 0xAA00FF00;
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_geo);
//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker,
//                accuracyCircleFillColor, accuracyCircleStrokeColor));

    }


    private Double lastX = 0.0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
//            mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                        mCurrentMode, true, null));

            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(15.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    protected void onDestroy() {

        // 退出时销毁定位
        mLocClient.stop();
        mMapView.onDestroy();

        super.onDestroy();
    }
}
