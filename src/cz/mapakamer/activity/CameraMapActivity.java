package cz.mapakamer.activity;


import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import cz.mapakamer.R;
import cz.mapakamer.entity.Camera;
import cz.mapakamer.map.CameraMapOverlay;
import cz.mapakamer.map.CameraMapOverlayItem;
import cz.mapakamer.utils.GPSUtility;
import android.location.Location;
import android.os.Bundle;

public class CameraMapActivity extends MapActivity {

	protected List<Overlay> mapOverlays;
    private MyLocationOverlay myLocationOverlay;
	protected MapView mapView;	
	private ArrayList<Camera> allCameras;
    protected Location myLocation;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
   
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                gotoMyPosition();
            }
        });
        
        mapOverlays.add(myLocationOverlay);
        
        myLocation = GPSUtility.getLastGps(this);
        
        allCameras = new ArrayList<Camera>();
        initSomeCameras(); 
        
        if (myLocation != null) {
        	//TODO / only in some area or all?
    		showCameras(getAllCameras(),
            		new GeoPoint((int) (myLocation.getLatitude()*1E6),
                            (int) (myLocation.getLongitude()*1E6)));
        }              
    }
	
	protected void gotoMyPosition() {
        mapView.getController().animateTo(myLocationOverlay.getMyLocation());
    }
	    
	protected void showCameras(ArrayList<Camera> cameras, GeoPoint zoomPoint) {
        
        CameraMapOverlay itemizedOverlay = new CameraMapOverlay(R.drawable.red_camera_icon, CameraMapActivity.this, mapView);

        double latitude = zoomPoint.getLatitudeE6()/1E6;
        double longitude = zoomPoint.getLongitudeE6()/1E6;
        
        if (cameras.size()>0) {
            Camera nearest = cameras.get(0);
            
            nearest.setDistance(Camera.howFar(latitude, longitude,
                    nearest.getLatitude(), nearest.getLongitude()));
			            
            itemizedOverlay.addOverlay(new CameraMapOverlayItem(nearest));

            for (int i = 1; i < cameras.size(); i++) {
                Camera camera = cameras.get(i);
                camera.setDistance(Camera.howFar(latitude, longitude,
                		camera.getLatitude(), camera.getLongitude()));

                if (camera.getDistance() < nearest.getDistance()) {
                    nearest = camera;
                }
                itemizedOverlay.addOverlay(new CameraMapOverlayItem(camera));
            }

            byte zooming = getZoom(nearest);
            mapView.getController().setZoom(zooming);
        }        

        mapOverlays.add(itemizedOverlay);
        
    }

    protected byte getZoom(Camera nearestCamera) {
        byte zoom = getZoomLevel(nearestCamera.getDistance() * 2 / 1000);
        return zoom;
    }
    
    
    private void initSomeCameras() {        	
    	//TODO
    	Camera first = new Camera();
    	first.setLatitude(50.06237349443927);
    	first.setLongitude(14.444809198903386);
    	first.setDescription("popisek prvni kamery");
    	first.setDistance(Camera.howFar(first.getLatitude(), first.getLongitude(), myLocation.getLatitude(), myLocation.getLongitude()));
    	allCameras.add(first);  
    	
    	Camera second = new Camera();
    	second.setLatitude(50.07479677603857);
    	second.setLongitude(14.480924606323242);
    	second.setDescription("popisek druhe kamery");
    	second.setDistance(Camera.howFar(second.getLatitude(), second.getLongitude(), myLocation.getLatitude(), myLocation.getLongitude()));
    	allCameras.add(second); 
    	
    	Camera third = new Camera();
    	third.setLatitude(50.0857849787494);
    	third.setLongitude(14.405213358113542);
    	third.setDescription("popisek treti kamery");
    	third.setDistance(Camera.howFar(third.getLatitude(), third.getLongitude(), myLocation.getLatitude(), myLocation.getLongitude()));
    	allCameras.add(third); 
    }
    
    private ArrayList<Camera> getAllCameras() {
    	return allCameras;    	
    }
        
    @Override
    protected void onResume() {
        super.onResume();
        myLocationOverlay.enableMyLocation();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        myLocationOverlay.disableMyLocation();
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public static byte getZoomLevel(double distance) {
        byte zoom = 1;
        double E = 40075;
        zoom = (byte) Math.round(Math.log(E / distance) / Math.log(2) + 1);

        if (zoom > 21)
            zoom = 21;
        if (zoom < 1)
            zoom = 1;

        return zoom;
    }
				
}
