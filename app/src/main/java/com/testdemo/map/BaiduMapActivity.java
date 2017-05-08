package com.testdemo.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import butterknife.BindView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 05 13:47
 * @DESC：百度地图（定位、地图）
 */

public class BaiduMapActivity extends BaseAppCompatActivity {
    @BindView(R.id.location_info_tv)
    TextView location_info_tv;

    @BindView(R.id.my_map_view)
    MapView my_map_view;

    private LocationClient locationClient;
    private BaiduMap baiduMap;
    private boolean isFirstNavigate = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState, R.layout.activity_baidu_map);
        initLocation();
    }

    private void initLocation(){
        location_info_tv.setText("定位信息：定位中..." );
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//需要返回地理位置详细信息，如果不设置，则不会返回国家省份等信息
        option.setScanSpan(5000);//每隔5秒自动定位
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new MyLocationListener());
        locationClient.start();
        baiduMap = my_map_view.getMap();
    }


    @Override
    protected void onResume() {
        super.onResume();
        my_map_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        my_map_view.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            locationClient.stop();
        }
        my_map_view.onDestroy();
    }

    private void navigateTo(BDLocation bdLocation) {
        if (!isFirstNavigate) return;
        MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(9);
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.newLatLng(new LatLng(bdLocation.getLatitude(),bdLocation.getLocationWhere()));
        baiduMap.animateMapStatus(update);
        isFirstNavigate = false;
    }

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.e(TAG,"-----------------------------onReceiveLocation：" + bdLocation.getDirection());
            final StringBuilder sb = new StringBuilder("定位信息：").append("\n");
            sb.append("定位类型：");
            if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("网络");
            }else if (bdLocation.getLocType() == BDLocation.GPS_ACCURACY_BAD) {
                sb.append("GPS");
            }
            sb.append("\n");
            sb.append("国家：").append(bdLocation.getCountry()).append("\n");
            sb.append("省份：").append(bdLocation.getProvince()).append("\n");
            sb.append("城市：").append(bdLocation.getCity()).append("\n");
            sb.append("区域：").append(bdLocation.getDistrict()).append("\n");
            sb.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            sb.append("经度：").append(bdLocation.getLongitude()).append("\n");
            sb.append("精准度：").append(bdLocation.getRadius()).append("\n");
            Log.e(TAG,"-----------------------------：" + sb.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    location_info_tv.setText(sb.toString());
                }
            });
            navigateTo(bdLocation);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            Log.e(TAG,"-----------------------------onConnectHotSpotMessage：" + s);
        }
    }
}
