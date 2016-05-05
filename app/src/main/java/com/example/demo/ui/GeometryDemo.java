package com.example.demo.ui;

import android.os.Bundle;

import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class GeometryDemo extends BaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mPolyline;
    private Polyline mColorfulPolyline;
    private Polyline mTexturePolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry_demo);

        mMapView = (MapView)findViewById(R.id.geometry_map);
        mBaiduMap = mMapView.getMap();
        addCustomElementsDemo();

    }

    public void addCustomElementsDemo() {
        // 添加普通折线绘制
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        mPolyline.setDottedLine(true);

        // 添加多颜色分段的折线绘制
        LatLng p11 = new LatLng(39.965, 116.444);
        LatLng p21 = new LatLng(39.925, 116.494);
        LatLng p31 = new LatLng(39.955, 116.534);
        LatLng p41 = new LatLng(39.905, 116.594);
        LatLng p51 = new LatLng(39.965, 116.644);
        List<LatLng> points1 = new ArrayList<LatLng>();
        points1.add(p11);
        points1.add(p21);
        points1.add(p31);
        points1.add(p41);
        points1.add(p51);
        List<Integer> colorValue = new ArrayList<Integer>();
        colorValue.add(0xAAFF0000);
        colorValue.add(0xAA00FF00);
        colorValue.add(0xAA0000FF);
        OverlayOptions ooPolyline1 = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(points1).colorsValues(colorValue);
        mColorfulPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline1);
        mColorfulPolyline.setWidth(30);


        // 添加多纹理分段的折线绘制
        BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromAsset("icon_road_red_arrow.png");
        BitmapDescriptor mBlueTexture = BitmapDescriptorFactory.fromAsset("icon_road_blue_arrow.png");
        BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("icon_road_green_arrow.png");
        LatLng p111 = new LatLng(39.865, 116.444);
        LatLng p211 = new LatLng(39.825, 116.494);
        LatLng p311 = new LatLng(39.855, 116.534);
        LatLng p411 = new LatLng(39.805, 116.594);
        List<LatLng> points11 = new ArrayList<LatLng>();
        points11.add(p111);
        points11.add(p211);
        points11.add(p311);
        points11.add(p411);
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        textureList.add(mRedTexture);
        textureList.add(mBlueTexture);
        textureList.add(mGreenTexture);
        List<Integer> textureIndexs = new ArrayList<Integer>();
        textureIndexs.add(0);
        textureIndexs.add(1);
        textureIndexs.add(2);
        OverlayOptions ooPolyline11 = new PolylineOptions().width(20)
                .points(points11).dottedLine(true).customTextureList(textureList).textureIndex(textureIndexs);
        mTexturePolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline11);



        // 添加弧线
        OverlayOptions ooArc = new ArcOptions().color(0xAA00FF00).width(4)
                .points(p1, p2, p3);
        mBaiduMap.addOverlay(ooArc);
        // 添加圆
        LatLng llCircle = new LatLng(39.90923, 116.447428);
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x000000FF)
                .center(llCircle).stroke(new Stroke(5, 0xAA000000))
                .radius(1400);
        mBaiduMap.addOverlay(ooCircle);

        LatLng llDot = new LatLng(39.98923, 116.397428);
        OverlayOptions ooDot = new DotOptions().center(llDot).radius(6)
                .color(0xFF0000FF);
        mBaiduMap.addOverlay(ooDot);
        // 添加多边形
        LatLng pt1 = new LatLng(39.93923, 116.357428);
        LatLng pt2 = new LatLng(39.91923, 116.327428);
        LatLng pt3 = new LatLng(39.89923, 116.347428);
        LatLng pt4 = new LatLng(39.89923, 116.367428);
        LatLng pt5 = new LatLng(39.91923, 116.387428);
        List<LatLng> pts = new ArrayList<LatLng>();
        pts.add(pt1);
        pts.add(pt2);
        pts.add(pt3);
        pts.add(pt4);
        pts.add(pt5);
        OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                .stroke(new Stroke(5, 0xAA00FF00)).fillColor(0xAAFFFF00);
        mBaiduMap.addOverlay(ooPolygon);

    }
}
