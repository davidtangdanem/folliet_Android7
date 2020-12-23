package com.maps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.menadinteractive.folliet2016.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    //LocationRequest mLocationRequest;
    String lat, lon;
    LatLng start;
    LatLng dest;
    ArrayList<PdvModel> items;
    PdvAdapter pdvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

       /* if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }*/

        String data = "";

        items = new ArrayList<>();

        items.add(new PdvModel("Magasin 1","48.85956808655448","2.3757424073192666","",""));
        items.add(new PdvModel("Magasin 2","48.85465281879693","2.3723410033272856","",""));
        items.add(new PdvModel("Magasin 3","48.86719250058799","2.3730177426589503","",""));
        items.add(new PdvModel("Magasin 4","48.86538530344708","2.3683542471207115","",""));
        ListView myDrawer = (ListView) findViewById(R.id.my_drawer);

        pdvAdapter = new PdvAdapter(this,
                R.layout.item_list_pdv, items);

        myDrawer.setAdapter(pdvAdapter);

        // Initializing
        MarkerPoints = new ArrayList<>();

        for (int i = 0; i < items.size();i++){

            MarkerPoints.add(new LatLng(Double.parseDouble(items.get(i).getLat()), Double.parseDouble(items.get(i).getLng())));

        }

        //MarkerPoints.add(new LatLng(48.85956808655448,2.3757424073192666));
        //MarkerPoints.add(new LatLng(48.85465281879693,2.3723410033272856));
        //MarkerPoints.add(new LatLng(48.86719250058799,2.3730177426589503));
        //48.86538530344708,2.3683542471207115

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.e("OnmapREady fired", "fired");

        //Initialize Google Play Services
      /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {  */
           buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
       // }


    }

    public void mapOperations() {

        Log.e("Map operations called", "Hope");

        LatLng point = start;

        // Already two locations
       /* if (MarkerPoints.size() > 1) {
            MarkerPoints.clear();
            mMap.clear();
        }*/

        // Adding new item to the ArrayList
        //MarkerPoints.add(start);

        // adding destination from database
        //LocationsDB db = new LocationsDB(MapsActivity.this);
        //String loc = db.getTermValues();
        //Log.d("location from db", loc);
        //String[] separated = loc.split(" ");


        //try {
        // Double lt = Double.parseDouble(separated[0]);
        //Double ln = Double.parseDouble(separated[1]);
        dest = new LatLng(48.86538530344708,2.3683542471207115);

        MarkerPoints.add(dest);
        //}
        //catch(Exception e) {
        //   Log.d("Wrong target location", e.toString());
        //}


        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        //options.position(start);
        //options.position(dest);

        /**
         * For the start location, the color of marker is GREEN and
         * for the end location, the color of marker is RED.
         */
       /* if (MarkerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (MarkerPoints.size() == 2) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        } else {

        }*/

        //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // Add new marker to the Google Map Android API V2
        //mMap.addMarker(options);

        LatLng origin = null;
        LatLng dest = null;

        // Checks, whether start and end locations are captured
        if (MarkerPoints.size() >= 2) {

            for (int i=0;i<(MarkerPoints.size() -1);i++)
            {

                origin = MarkerPoints.get(i);
                dest = MarkerPoints.get(i+1);

                // affichage btn départ
                options = new MarkerOptions();
                // Setting the position of the marker
                options.position(origin);
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                options.title(items.get(i).getName());
                mMap.addMarker(options);

                // affichage btn arrivée
                //options = new MarkerOptions();
                // Setting the position of the marker
                //options.position(dest);
                //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                //mMap.addMarker(options);

                // Getting URL to the Google Directions API
                String url = getUrl(origin, dest);
                Log.e("onMapClick", url.toString());
                FetchUrl FetchUrl = new FetchUrl(i);

                // Start downloading json data from Google Directions API
                FetchUrl.execute(url);
                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            }
        }


    }


    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.e("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        private ProgressDialog mDialog;
        private int idx ;

        @Override
        protected void onPreExecute (){
            mDialog = new ProgressDialog(MapsActivity.this);
            mDialog.setMessage("Calcul de l'itinéraire...");
            mDialog.show();
        }

        public FetchUrl(int i){
            this.idx = i;
        }

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.e("Background Task data", data.toString());
            } catch (Exception e) {
                Log.e("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask(idx);

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

            mDialog.dismiss();

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        private int idx ;


        public ParserTask(int i){
            this.idx = i;
        }

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
               /// DataParser parser = new DataParser();
                //Log.d("ParserTask", parser.toString());

                // Starts parsing data
               // routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }

            Log.e(" Parser Task routes",""+routes);
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            String duration = "";
            String distance = "";


            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                distance= path.get(0).get("distance");
                duration= path.get(1).get("duration");


                items.get(idx).setDistance(distance);
                items.get(idx).setDuration(duration);

                //Log.e("path.get",""+path.get(0).get("distance"));//.get("duration");
                //Log.e("path.get",""+path.get(1).get("duration"));//.get("duration");

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    Log.e("point",""+point);
                    double lat;
                    double lng;
                    LatLng position;

                    if (point.get("lat") !=null && point.get("lng") !=null )
                    {
                        lat = Double.parseDouble(point.get("lat"));
                        lng = Double.parseDouble(point.get("lng"));

                        //duration = point.get("duration");
                        //distance = point.get("distance");

                        position = new LatLng(lat, lng);

                        points.add(position);
                    }

                }


                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            pdvAdapter.notifyDataSetChanged();

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
               //.addApi(LocationServices.API)

        Log.e("buildGoogleApiClient",""+mGoogleApiClient);

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.e("OnConnected fired", "fired");

      //  mLocationRequest = new LocationRequest();
      //  mLocationRequest.setInterval(1000);
      //  mLocationRequest.setFastestInterval(1000);
      //  mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
          //      mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

            start = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());


            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

            mapOperations();
        } else {

            start = new LatLng(48.7633859,2.4739518);

            //Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();

            mapOperations();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


/*
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> listPdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Liste Point de Vente à visiter
        listPdv = new ArrayList<LatLng>();
        listPdv.add(new LatLng(48.86538530344708,2.3683542471207115));
        listPdv.add(new LatLng(48.85956808655448,2.3757424073192666));
        listPdv.add(new LatLng(48.85465281879693,2.3723410033272856));
        listPdv.add(new LatLng(48.86719250058799,2.3730177426589503));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0 ; i < listPdv.size() ; i++)
        {
            // Add a marker in Sydney and move the camera
            LatLng position = listPdv.get(i);
            mMap.addMarker(new MarkerOptions().position(position).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        }
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
*/