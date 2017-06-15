package com.portalidea.roundtableitalia.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.portalidea.roundtableitalia.R;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archirayan on 5/5/2016.
 */
public class Map extends AppCompatActivity {

    private MapView myOpenMapView;
    private MapController myMapController;
    LocationManager locationManager;
    ArrayList<OverlayItem> overlayItemArray;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        myOpenMapView = (MapView) findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.setClickable(true);
        //mapView.setUseDataConnection(false);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(17);
        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setCenter(startPoint);
        myMapController.setZoom(14);
        overlayItemArray = new ArrayList<OverlayItem>();

        DefaultResourceProxyImpl defaultResourceProxyImpl = new DefaultResourceProxyImpl(this);
        MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(overlayItemArray, null, defaultResourceProxyImpl);
        myOpenMapView.getOverlays().add(myItemizedIconOverlay);

//        ArrayList<GeoPoint> circlePoints = new ArrayList<GeoPoint>();
//        iSteps = (100 * 40000)^2;
//        fStepSize = M_2_PI/iSteps;
//        for (double f = 0; f < M_2_PI; f += fStepSize){
//            circlePoints.add(new GeoPoint(centerLat + radius*sin(f), centerLon + radius*cos(f)));
//        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //for demo, getLastKnownLocation from GPS only, not from NETWORK
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location lastLocation = new Location("");
        lastLocation.setLatitude(20.30);
        lastLocation.setLongitude(52.30);
        if (lastLocation != null) {
            updateLoc(lastLocation);
        }

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        myOpenMapView.getOverlays().add(myScaleBarOverlay);
    }

    private void updateLoc(Location loc) {
        GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
        myMapController.setCenter(locGeoPoint);
        myMapController.animateTo(locGeoPoint);

        setOverlayLoc(loc);

        myOpenMapView.invalidate();
    }

    private void setOverlayLoc(Location overlayloc) {
        GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);
        //---
        overlayItemArray.clear();

        OverlayItem newMyLocationItem = new OverlayItem("My Location", "My Location", overlocGeoPoint);
        overlayItemArray.add(newMyLocationItem);
        //---
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager.removeUpdates(myLocationListener);
    }

    private class MyItemizedIconOverlay extends ItemizedIconOverlay<OverlayItem> {

        public MyItemizedIconOverlay(List<OverlayItem> pList, org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> pOnItemGestureListener,
                                     ResourceProxy pResourceProxy) {
            super(pList, pOnItemGestureListener, pResourceProxy);
        }

        @Override
        public void draw(Canvas canvas, MapView mapview, boolean arg2) {
            super.draw(canvas, mapview, arg2);

//            if (!overlayItemArray.isEmpty()) {
//                //overlayItemArray have only ONE element only, so I hard code to get(0)
//                GeoPoint in = overlayItemArray.get(0).getPoint();
//
//                Point out = new Point();
//                mapview.getProjection().toPixels(in, out);
//
//                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker);
//                canvas.drawBitmap(bm, out.x - bm.getWidth() / 2, out.y - bm.getHeight() / 2, null);
//            }

        }

        @Override
        public boolean onSingleTapUp(MotionEvent event, MapView mapView) {
            //return super.onSingleTapUp(event, mapView);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event, MapView mapView) {
            return true;
        }
    }

//    private LocationListener myLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            updateLoc(location);
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//    };

//    class MapOverlay extends Overlay {
//        private GeoPoint pointToDraw;
//
//        public MapOverlay(Context ctx) {
//            super(ctx);
//        }
//
//        public void setPointToDraw(GeoPoint point) {
//            pointToDraw = point;
//        }
//
//        public GeoPoint getPointToDraw() {
//            return pointToDraw;
//        }
//
//        @Override
//        protected void draw(Canvas canvas, MapView mapView, boolean shadow) {
//            super.draw(canvas, mapView, shadow);
//            // convert point to pixels
//            Point screenPts = new Point();
//            mapView.getProjection().toPixels(pointToDraw, screenPts);
//
//            // add marker
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pen);
//            canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 128, null);
//
//            mPaintBorder = new Paint();
//            mPaintBorder.setStyle(Paint.Style.STROKE);
//            mPaintBorder.setAntiAlias(true);
//            mPaintBorder.setColor(0xee4D2EFF);
//            mPaintFill = new Paint();
//            mPaintFill.setStyle(Paint.Style.FILL);
//            mPaintFill.setColor(0x154D2EFF);
//
//            double lat = pointToDraw.getLatitudeE6();
//            int radius = (int) mapView.getProjection().metersToEquatorPixels(theGpsAccuracy);
//            /** Draw the boundary of the circle */
//            canvas.drawCircle(ScreenPts.x, ScreenPts.y, radius, mPaintBorder);
//            /** Fill the circle with semitransparent color */
//            canvas.drawCircle(ScreenPts.x, ScreenPts.y, radius, mPaintFill);
//        }
//    }
}
