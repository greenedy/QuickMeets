package com.mark.mroz.quickmeets;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mark.mroz.quickmeets.enums.SportEnum;
import com.mark.mroz.quickmeets.models.SportAdapter;
import com.mark.mroz.quickmeets.models.SportsEvent;
import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.v7.media.MediaControlIntent.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, View.OnClickListener, SearchView.OnQueryTextListener, GoogleMap.OnInfoWindowClickListener {

    private static GlobalSharedManager manager;

    GoogleMap mapView;
    MarkerOptions marker;
    LatLng updatedPosition;
    Boolean markerClicked = false;
    ListView listViewC;

    Button doneEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()) {
            setContentView(R.layout.activity_main_bottom);

            manager = new GlobalSharedManager(getApplicationContext());

            initMap();

            Intent intent = getIntent();
            if (intent.getStringExtra("SETUP_OPTION") != null && intent.getStringExtra("SETUP_OPTION").equals("EDIT")) {

                findViewById(R.id.searchBar).setVisibility(View.INVISIBLE);
                //findViewById(R.id.profileButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.doneEditButton).setVisibility(View.VISIBLE);
                doneEditButton = (Button) findViewById(R.id.doneEditButton);
                doneEditButton.setOnClickListener(this);
            } else {
                findViewById(R.id.doneEditButton).setVisibility(View.INVISIBLE);
                SearchView s = (SearchView) findViewById(R.id.searchBar);
                s.setOnQueryTextListener(this);
            }

            ArrayList<SportsEvent> arrayOfAllEvents = new ArrayList<SportsEvent>();
            // Create the adapter to convert the array to views
            SportAdapter adapterC = new SportAdapter(this, arrayOfAllEvents);
            // Attach the adapter to a ListView
            listViewC =  (ListView)findViewById(R.id.listViewAllEvents);  //id in xml
            listViewC.setAdapter(adapterC);

            // int heightC =0;
            for (SportsEvent event : manager.getAllSportsEvents()) { {
                //     heightC= heightC+500;
                adapterC.add(event);
            }}


            //When an event from the list is clicked open the event details and pass information in intent to it
            listViewC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                    SportsEvent selectedFromList =(SportsEvent) (listViewC.getItemAtPosition(myItemInt));
                    Intent destinationIntent = new Intent(getApplicationContext(), DetailActivity.class);
                    destinationIntent.putExtra("sport", selectedFromList.getSport().toString());
                    destinationIntent.putExtra("joinedPeople", Integer.toString(selectedFromList.getSubscribedUsers().size()));
                    destinationIntent.putExtra("intensity", Integer.toString(selectedFromList.getIntensity()));
                    destinationIntent.putExtra("players", Integer.toString(selectedFromList.getMaxPlayers()));
                    destinationIntent.putExtra("eventCreator",selectedFromList.getEventCreator().getName());
                    destinationIntent.putExtra("start_time",  DateFormat.getDateTimeInstance().format(selectedFromList.getStartTime()));
                    destinationIntent.putExtra("end_time", DateFormat.getDateTimeInstance().format(selectedFromList.getEndTime()));
                    destinationIntent.putExtra("equipment", selectedFromList.getEquipment().toString());
                    destinationIntent.putExtra("description", selectedFromList.getDescription());
                    destinationIntent.putExtra("Lat", selectedFromList.getLat());
                    destinationIntent.putExtra("Lng", selectedFromList.getLng());
                    startActivity(destinationIntent);

                }
            });

            //Bottom navigation bar code that displays what has been selected from the bottom menu
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_A);
            bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                        ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.action_map:
                                    vf.setDisplayedChild(0);
                                    break;
                                case R.id.action_list:
                                    vf.setDisplayedChild(1);
                                    break;
                                case R.id.action_profile:
                                    onClickProfile();
                                    break;
                            }
                            return false;
                        }
            });

        } else {
            // TODO - handle failed to link to google maps API
        }


    }



    // Init

    private void initMap() {

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    // Map delegate

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapView = googleMap;
        goToLocation(45.4172477, -75.6806411, 12);

        Intent intent = getIntent();
        if (intent.getStringExtra("SETUP_OPTION") != null) {
            if (intent.getStringExtra("SETUP_OPTION").equals("CANCEL")) {
                mapView.clear();
                for (SportsEvent e : manager.getAllSportsEvents()) {
                    setPinsForLocation(new LatLng(e.getLat(), e.getLng()));
                }
            } else if (intent.getStringExtra("SETUP_OPTION").equals("EDIT")) {

                double lat = intent.getDoubleExtra("Lat", 0L);
                double lng = intent.getDoubleExtra("Lng", 0L);

                this.marker = new MarkerOptions().position(new LatLng(lat,lng)).draggable(true);

                mapView.addMarker(this.marker);
            }
        } else {

            for (SportsEvent e : manager.getAllSportsEvents()) {
                setPinsForLocation(new LatLng(e.getLat(), e.getLng()));
            }
        }

        mapView.setOnMapClickListener(this);
        mapView.setOnMapLongClickListener(this);
        mapView.setOnMarkerDragListener(this);
        mapView.setOnMarkerClickListener(this);
        mapView.setOnInfoWindowClickListener(this);
        mapView.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                /*
                Here you will inflate the custom layout that you want to use for marker. I have inflate the custom view according to my requirements.
               */
                Intent currentIntent = getIntent();
                if (currentIntent.getStringExtra("SETUP_OPTION") == null) {

                }

                for (final SportsEvent event : manager.getAllSportsEvents()) {
                    if (event.getLat() == marker.getPosition().latitude && event.getLng() == marker.getPosition().longitude) {
                        View v = getLayoutInflater().inflate(R.layout.custom_info_contents, null);


                        TextView title = (TextView) v.findViewById(R.id.markerTitle);
                        TextView players = (TextView) v.findViewById(R.id.markerSnippet);
                        CheckBox equipmentBox = (CheckBox) v.findViewById(R.id.markerEquipmentCheckBox);
                        title.setText(SportEnum.asString(event.getSport()));
                        players.setText(event.getSubscribedUsers().size() + " / " + event.getMaxPlayers() + " Players");

                        if (event.getEquipment()) {
                            equipmentBox.setSelected(true);
                            equipmentBox.setChecked(true);
                            equipmentBox.toggle();
                        }

                        //Button joinButton = (Button) v.findViewById(R.id.joinButton);




                        ImageView cimg = (ImageView) v.findViewById(R.id.marker_image_icon);

                        Drawable drawableImage = null;

                        switch (event.getSport()) {
                            case SOCCER:
                                drawableImage = ContextCompat.getDrawable(getApplicationContext(), R.drawable.soccer);
                                break;
                            case TENNIS:
                                drawableImage = ContextCompat.getDrawable(getApplicationContext(), R.drawable.tennis);
                                break;
                            case FOOTBALL:
                                drawableImage = ContextCompat.getDrawable(getApplicationContext(), R.drawable.football);
                                break;
                            case DANCING:
                                drawableImage = ContextCompat.getDrawable(getApplicationContext(), R.drawable.dancing);
                                break;
                        }

                        if (drawableImage != null) {
                            cimg.setImageDrawable(drawableImage);
                        }

                        return v;
                    }
                }
                return getLayoutInflater().inflate(R.layout.custom_info_contents, null);
            }
        });

    }

    // Public - Map Helpers

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();

        int isAvailable = api.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cannot connect to Play Services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    // Private -  Map Helpers

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mapView.moveCamera(update);

    }

    private void goToLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mapView.moveCamera(update);

    }

    private void setPinsForLocation(LatLng ll) {
        mapView.addMarker(new MarkerOptions().position(ll)
                .title("Sports Event"));
        mapView.moveCamera(CameraUpdateFactory.newLatLng(ll));

    }


    @Override
    public void onMapClick(LatLng point) {

        mapView.animateCamera(CameraUpdateFactory.newLatLng(point));

        markerClicked = false;
    }

    @Override
    public void onMapLongClick(LatLng point) {

        Intent currentIntent = getIntent();
        if (currentIntent.getStringExtra("SETUP_OPTION") == null) {

            mapView.addMarker(new MarkerOptions()
                    .position(point)
                    .draggable(true));

            Intent intent = new Intent(this, AddSportsEventActivity.class);
            intent.putExtra("Lat", point.latitude);
            intent.putExtra("Lng", point.longitude);
            startActivity(intent);

            markerClicked = false;
        }

    }

    @Override
    public void onMarkerDragStart(Marker marker) {



    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        mapView.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        updatedPosition = marker.getPosition();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Intent intent = getIntent();

        if (intent.getStringExtra("SETUP_OPTION") != null && intent.getStringExtra("SETUP_OPTION").equals("EDIT")) {
            return true;
        } else if (marker.isInfoWindowShown()) {

            for (SportsEvent e : manager.getAllSportsEvents()) {
                if (e.getLat() == marker.getPosition().latitude && e.getLng() == marker.getPosition().longitude) {

                    Intent destinationIntent = new Intent(this, DetailActivity.class);
                    destinationIntent.putExtra("sport", e.getSport());
                    destinationIntent.putExtra("players", e.getMaxPlayers());
                    destinationIntent.putExtra("start_time", e.getStartTime());
                    destinationIntent.putExtra("end_time", e.getEndTime());
                    destinationIntent.putExtra("equipment", e.getEquipment());
                    destinationIntent.putExtra("description", e.getDescription());
                    destinationIntent.putExtra("Lat", e.getLat());
                    destinationIntent.putExtra("Lng", e.getLng());
                    startActivity(destinationIntent);

                    return true;
                }
            }
        } else {
            return false;
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            initMap();
        }
         if(resultCode==RESULT_CANCELED){
             mapView.clear();
             for (SportsEvent e : manager.getAllSportsEvents()) {
                 setPinsForLocation(new LatLng(e.getLat(), e.getLng()));
             }
        }
    }


    // On Click Listener

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.doneEditButton:

                Intent currentIntent = getIntent();

                String sort = currentIntent.getStringExtra("sport");
                String players = currentIntent.getStringExtra("players");
                String startDate = currentIntent.getStringExtra("start_date");
                String startTime = currentIntent.getStringExtra("start_time");
                String endDate = currentIntent.getStringExtra("end_date");
                String endTime = currentIntent.getStringExtra("end_time");
                Boolean equipment = currentIntent.getBooleanExtra("equipment", false);
                String description = currentIntent.getStringExtra("description");

                double lat = currentIntent.getDoubleExtra("Lat", 0L);
                double lng = currentIntent.getDoubleExtra("Lng", 0L);

                if (updatedPosition != null) {
                    lat = updatedPosition.latitude;
                    lng = updatedPosition.longitude;
                }

                Intent doneEditLocationIntent = new Intent(this, AddSportsEventActivity.class);
                doneEditLocationIntent.putExtra("sport",sort);
                doneEditLocationIntent.putExtra("players",players);
                doneEditLocationIntent.putExtra("start_date",startDate);
                doneEditLocationIntent.putExtra("start_time",startTime);
                doneEditLocationIntent.putExtra("end_date",endDate);
                doneEditLocationIntent.putExtra("end_time",endTime);
                doneEditLocationIntent.putExtra("equipment",equipment);
                doneEditLocationIntent.putExtra("description", description);
                doneEditLocationIntent.putExtra("Lat",lat);
                doneEditLocationIntent.putExtra("Lng",lng);
                startActivity(doneEditLocationIntent);


                break;
        }

    }

    public void onClickProfile() {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
       // overridePendingTransition(0,0);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.equals("")) {
            mapView.clear();
            for (SportsEvent e :manager.getAllSportsEvents()) {
                mapView.addMarker(new MarkerOptions().position(new LatLng(e.getLat(), e.getLng())));
            }
        } else {
            mapView.clear();
            for (SportsEvent e :manager.getAllSportsEvents()) {
                if (e.toString().toLowerCase().contains(newText.toLowerCase()))
                    mapView.addMarker(new MarkerOptions().position(new LatLng(e.getLat(), e.getLng())));
            }
        }
        return true;
    }

    // Google maps info click listener

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.isInfoWindowShown()) {

            for (SportsEvent e : manager.getAllSportsEvents()) {
                if (e.getLat() == marker.getPosition().latitude && e.getLng() == marker.getPosition().longitude) {

                    Intent destinationIntent = new Intent(getApplicationContext(), DetailActivity.class);
                    destinationIntent.putExtra("sport", e.getSport().toString());
                    destinationIntent.putExtra("joinedPeople", Integer.toString(e.getSubscribedUsers().size()));
                    destinationIntent.putExtra("intensity", Integer.toString(e.getIntensity()));
                    destinationIntent.putExtra("players", Integer.toString(e.getMaxPlayers()));
                    destinationIntent.putExtra("eventCreator",e.getEventCreator().getName());
                    destinationIntent.putExtra("start_time",  DateFormat.getDateTimeInstance().format(e.getStartTime()));
                    destinationIntent.putExtra("end_time", DateFormat.getDateTimeInstance().format(e.getEndTime()));
                    destinationIntent.putExtra("equipment", e.getEquipment().toString());
                    destinationIntent.putExtra("description", e.getDescription());
                    destinationIntent.putExtra("Lat", e.getLat());
                    destinationIntent.putExtra("Lng", e.getLng());
                    startActivity(destinationIntent);
                }
            }
        }
    }
}
