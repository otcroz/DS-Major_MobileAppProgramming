package com.example.myapplication19

import android.Manifest // 직접 import 해준다.
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener{
    // 구글 맵에 대한 변수 선언
    var googleMap: GoogleMap? = null
    // 사용자 위치정보에 대한 클라이언트 변수
    lateinit var apiClient : GoogleApiClient
    lateinit var providerClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("mobileApp", "onCreate")
        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment)!!.getMapAsync(this)

        // 사용자 퍼미션 얻기
        providerClient = LocationServices.getFusedLocationProviderClient(this)
        apiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if(it.all{permission -> permission.value == true}){
                apiClient.connect() // 정보 가져옴, onConnected와 연결
            } else{
                Toast.makeText(this,"권한 거부..", Toast.LENGTH_SHORT).show()
            }
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) !== PackageManager.PERMISSION_GRANTED)
                { Log.d("mobileApp", "checkSelfPermission")
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE)
                )
        }
        else{
            apiClient.connect() // 정보 가져옴
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0 // 구글맵을 변수에 넣는다.
        Log.d("mobileApp", "onMapReady")
        // 지도의 타입을 바꾸어줌: satellite
        //googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        //특정 위치를 보여준다.(위도, 경도)
        /*val latLng = LatLng(37.568256, 126.897240)
        val position: CameraPosition = CameraPosition.Builder() // 카메라를 움직여서 설정해놓은 위치로 지도를 이동하도록 한다.
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        // 마커 추가하기
        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("월드컵경기장")
        googleMap?.addMarker(markerOp)
        */
    }
    // 카메라를 이동시키는 함수
    private fun moveMap(latitude: Double, longitude: Double){
        val latLng = LatLng(latitude, longitude)
        val position: CameraPosition = CameraPosition.Builder() // 카메라를 움직여서 설정해놓은 위치로 지도를 이동하도록 한다.
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        // 마커 추가하기
        val markerOp = MarkerOptions()
        markerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        markerOp.position(latLng)
        markerOp.title("MyLocation")
        googleMap?.addMarker(markerOp)
    }

    // 클라이언트에 대한 설정 함수
    override fun onConnected(p0: Bundle?) {
        // 퍼미션 다시 확인
        Log.d("mobileApp", "onConnected")
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED){
            providerClient.lastLocation.addOnSuccessListener(
                this@MainActivity,
                object: OnSuccessListener<Location>{
                    override fun onSuccess(p0: Location?) { // 사용자 위치정보에 대한 값
                        p0?.let{
                            val latitude = p0.latitude
                            val longitude = p0.longitude
                            Log.d("mobileApp", "lat: $latitude, lng: $longitude")
                            // 사용자 위치를 카메라로
                            moveMap(latitude, longitude)
                        }
                    }
                }
            )
            apiClient.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    // 메뉴에 대한 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, 1, 0, "위성지도")
        menu?.add(0, 2, 0, "일반지도")
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            }
            2 -> {
                googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }
        return false
    }
}