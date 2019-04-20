package test.engineering.com.gourmetsearch.GourmetSearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.engineering.com.gourmetsearch.GenreSelect.GenreSelectActivity;
import test.engineering.com.gourmetsearch.Model.API.APIInterface;
import test.engineering.com.gourmetsearch.Model.API.APIService;
import test.engineering.com.gourmetsearch.Model.Response.HotPepperObject;
import test.engineering.com.gourmetsearch.Model.Response.Shop;
import test.engineering.com.gourmetsearch.Model.Response.StoreResponse;
import test.engineering.com.gourmetsearch.R;
import test.engineering.com.gourmetsearch.Util.BitmapUtil;
import test.engineering.com.gourmetsearch.Util.NetworkStateReceiver;

public class GourmetSearchActivity extends FragmentActivity implements OnMapReadyCallback, NetworkStateReceiver.NetworkStateReceiverListener, GoogleMap.OnMarkerClickListener {
    public static final int LOCATION_REQUEST = 111;

    private NetworkStateReceiver networkStateReceiver;
    private GoogleMap mMap;
    private Marker myLocation;
    private List<StoreResponse> storeList = new ArrayList<>();
    private List<Marker> storeMarkerList = new ArrayList<>();
    private Circle circle;

    private LocationManager mLocationManager;

    private ConstraintLayout genreSelectBar;
    private TextView genreNameTextView;
    private ConstraintLayout searchConstraintLayout;
    private ImageView searchImageView;

    private ConstraintLayout moveConstraintLayout;

    private ConstraintLayout currentPositionConstraintLayout;

    private ConstraintLayout storeDetailConstraintLayout;
    private TextView alphabetTextView;
    private TextView storeNameTextView;
    private TextView openTextView;
    private TextView minToGoTextView;

    private Point point;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gourmet_search);

        setupView();

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(this),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,}, LOCATION_REQUEST);
            return;
        }
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Display display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);

        setupGenreSelectBar();
        setupStoreDetailConstraintLayout();
        setupCurrentPositionConstraintLayout();
    }

    private void setupView() {
        genreSelectBar = findViewById(R.id.genreSelectBar);
        genreNameTextView = findViewById(R.id.genreNameTextView);
        searchConstraintLayout = findViewById(R.id.searchConstraintLayout);
        searchImageView = findViewById(R.id.searchImageView);


        moveConstraintLayout = findViewById(R.id.moveConstraintLayout);

        currentPositionConstraintLayout = findViewById(R.id.currentPositionConstraintLayout);

        storeDetailConstraintLayout = findViewById(R.id.storeDetailConstraintLayout);
        alphabetTextView = findViewById(R.id.alphabetTextView);
        storeNameTextView = findViewById(R.id.storeNameTextView);
        openTextView = findViewById(R.id.openTextView);
        minToGoTextView = findViewById(R.id.minToGoTextView);
    }

    private void setupGenreSelectBar() {
        genreSelectBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GourmetSearchActivity.this, GenreSelectActivity.class);
                startActivity(intent);
            }
        });

        searchConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStoreInfo();
            }
        });
    }

    private void setupCurrentPositionConstraintLayout() {
        currentPositionConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLocation == null) {
                    return;
                }
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(myLocation.getPosition());
                mMap.moveCamera(cameraUpdate);
            }
        });
    }


    private void setupStoreDetailConstraintLayout() {
        storeDetailConstraintLayout.setVisibility(View.GONE);
        storeNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 飲食店詳細画面へ遷移
            }
        });
    }

    private void setup() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10, // 通知のための最小時間間隔（ミリ秒）
                10, // 通知のための最小距離間隔（メートル）
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        String msg = "Lat=" + location.getLatitude()
                                + "\nLng=" + location.getLongitude();
                        Log.d("GPS", msg);
                        moveMap2Location(location, mMap.getCameraPosition().zoom);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                }
        );

    }

    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        for (String provider : providers) {

            Location l = mLocationManager.getLastKnownLocation
                    (provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        Log.d("last", "getLastKnownLocation " + bestLocation);
        moveMap2Location(bestLocation, 15.0f);
    }

    private void moveMap2Location(Location location, float zoom) {
        drawCircle(location);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (myLocation != null) {
            myLocation.remove();
        }

        Bitmap current_pin = BitmapUtil.resizeMapIcons(getApplicationContext(), "current_pin", 100, 100);
        myLocation = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(current_pin))
                .anchor(0.5f, 0.5f)
        );

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(cameraUpdate);
    }

    private void drawCircle(Location location) {
        if (circle != null) {
            circle.remove();
        }
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(500)
                .strokeWidth(2)
                .strokeColor(getColor(R.color.colorPrimary))
                .fillColor(getColor(R.color.colorPrimaryTransparent)));
    }

    private void getStoreInfo() {
        if (myLocation == null) {
            return;
        }
        LatLng position = myLocation.getPosition();
        APIInterface apiInterface = APIService.createService(APIInterface.class);
        Call<HotPepperObject> call = apiInterface.getOptionsHotPepperObjectNew(
                getString(R.string.hotpepperApikey),
                "",
                "json",
                position.latitude,
                position.longitude,
                26
        );
        call.enqueue(new Callback<HotPepperObject>() {
            @Override
            public void onResponse(Call<HotPepperObject> call, Response<HotPepperObject> response) {
                if (response.isSuccessful()) {
                    storeList = response.body().getResults().getShop();
                    receivedStoreDataList();
                }
            }

            @Override
            public void onFailure(Call<HotPepperObject> call, Throwable t) {

            }
        });
    }

    private void receivedStoreDataList() {
        if (storeList.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.emptyStoreList, Toast.LENGTH_LONG);
            return;
        }
        resetStoreMarkerList();
        for (int position = 0; position < storeList.size(); position++) {
            StoreResponse store = storeList.get(position);
            LatLng location = new LatLng(store.getLat(), store.getLng());
            storeMarkerList.add(mMap.addMarker(new MarkerOptions().position(location).title(String.format("%c", 'A' + position)).visible(true)));
        }
    }

    private void resetStoreMarkerList() {
        if (storeMarkerList.isEmpty()) {
            return;
        }
        for (Marker storeMarker : storeMarkerList) {
            storeMarker.remove();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_REQUEST) {
            setup();
        }
    }

    @Override
    public void networkAvailable() {
        Log.d("GourmetSearchActivity", "Connected");
        searchConstraintLayout.setEnabled(true);
    }

    @Override
    public void networkUnavailable() {
        Log.d("GourmetSearchActivity", "Disonnected");
        searchConstraintLayout.setEnabled(false);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle() == null) {
            return false;
        }
        position = marker.getTitle().toCharArray()[0] - 'A';

        StoreResponse store = storeList.get(position);

        storeNameTextView.setText(store.getName());
        alphabetTextView.setText(marker.getTitle());
        if(store.getOpen() == null || store.getOpen().isEmpty()) {
            openTextView.setText(getResources().getString(R.string.unknownOpen));
        } else {
            openTextView.setText(store.getOpen());
        }
        float[] results = new float[3];
        Location.distanceBetween(myLocation.getPosition().latitude, myLocation.getPosition().longitude, marker.getPosition().latitude, marker.getPosition().longitude, results);
        float distance = results[0];
        minToGoTextView.setText(getResources().getString(R.string.to_minutes, Math.round(distance / 80.0f)));
        storeDetailConstraintLayout.setVisibility(View.VISIBLE);
        return true;
    }
}
