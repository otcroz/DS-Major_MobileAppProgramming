package com.example.myapplication19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    // 구글 맵에 대한 변수 선언
    var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0 // 구글맵을 변수에 넣는다.

        //특정 위치를 보여준다.(위도, 경도)
        val latLng = LatLng(37.568256, 126.897240)
        val position: CameraPosition = CameraPosition.Builder() // 카메라를 움직여서 설정해놓은 위치로 지도를 이동하도록 한다.
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
    }
}