package com.portalidea.roundtableitalia.Activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.portalidea.roundtableitalia.R;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * Created by archirayan on 16-Sep-16.
 */
public class MapActivity extends Activity {


    private MapView myOpenMapView;
    private MapController myMapController;
    String lat, lng;
    public ArrayList<OverlayItem> overlayItemArray;
    private ItemizedIconOverlay<OverlayItem> currentLocationOverlay;
    DefaultResourceProxyImpl resourceProxy;
    GeoPoint geoPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.actionBarTitle)).setText("Round Table Italia");

//        Intent intent = getIntent();
//        lat = intent.getStringExtra("lat");
//        lng = intent.getStringExtra("lng");

        overlayItemArray = new ArrayList<>();
        lat = "41.871900";
        lng = "12.567400";
        resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        myOpenMapView = (MapView) findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setMultiTouchControls(true);
        myOpenMapView.setClickable(true);
        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setZoom(17);
        geoPoint = new GeoPoint(new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lng)));
        myMapController.setCenter(geoPoint);
        OverlayItem myLocationOverlayItem = new OverlayItem("Here", "Current Position", new GeoPoint(Double.parseDouble(lat), Double.parseDouble(lng)));
        Drawable myCurrentLocationMarker = ContextCompat.getDrawable(this, R.drawable.ic_marker);
        TextDrawable textDra = new TextDrawable("Helloasdadsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd");
        myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(myLocationOverlayItem);

        currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Drawable myCurrentLocationMarker = ContextCompat.getDrawable(MapActivity.this, R.drawable.ic_blue_location);
                        item.setMarker(myCurrentLocationMarker);
                        Toast.makeText(MapActivity.this, "index" + index, Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, resourceProxy);
        this.myOpenMapView.getOverlays().add(this.currentLocationOverlay);
    }

    public class TextDrawable extends Drawable {

        private final String text;
        private final Paint paint;

        public TextDrawable(String text) {
            this.text = text;
            this.paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(20f);
            paint.setAntiAlias(true);
            paint.setFakeBoldText(true);
            paint.setShadowLayer(6f, 0, 0, Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.LEFT);
        }

//        @Override
//        public void draw(final Canvas c, final MapView osmv, final boolean shadow) {
//            if (shadow) {
//                return;
//            }
//            c.drawText("some text", tx, ty + 5, this.paint);
//        }

        @Override
        public void draw(Canvas c) {
            final MapView.Projection pj = myOpenMapView.getProjection();
            c.drawText("some text", 0, 0, this.paint);
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
