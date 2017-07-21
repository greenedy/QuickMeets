package com.mark.mroz.quickmeets;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mark.mroz.quickmeets.enums.SportEnum;
import com.mark.mroz.quickmeets.models.SportAdapter;
import com.mark.mroz.quickmeets.models.SportsEvent;
import com.mark.mroz.quickmeets.models.User;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    GlobalSharedManager manager;
    int currentUserID;
    User currentUser;
    CollapsingToolbarLayout collapsingToolbar;
    ListView listView, listViewC;

    private TextView userName, userAge, userBio, favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_header);
        // getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        currentUserID = 101;
        manager = new GlobalSharedManager(this);
        currentUser = manager.getCurrentUser();

        //userName = (TextView) findViewById(R.id.lblName);
        userAge = (TextView) findViewById(R.id.lblAge);
        userBio = (TextView) findViewById(R.id.txtBio);
        favourites =(TextView) findViewById(R.id.lblFavSports);

       setup();

    }

    private boolean setup(){
        collapsingToolbar.setTitle(currentUser.getName());
        if(currentUser.getAge()!=0) {
            userAge.setText("Age: " + Integer.toString(currentUser.getAge()));
        }
        else{
            userAge.setText("Age: Hidden");
        }
        userBio.setText(currentUser.getBio());
        // favourites.setText(currentUser);
        // Construct the data source
        ArrayList<SportsEvent> arrayOfJoinedEvents = new ArrayList<SportsEvent>();
        // Create the adapter to convert the array to views
        SportAdapter adapter = new SportAdapter(this, arrayOfJoinedEvents);
        // Attach the adapter to a ListView
        listView =  (ListView)findViewById(R.id.listViewJoinedEvents);
        listView.setAdapter(adapter);

        int height =0;
        for (SportsEvent event : manager.getCurrentUser().getJoinedEvents()) { {
            height= height+500;
            adapter.add(event);
        }}

        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, height));

        //When an event from the list is clicked open the event details and pass information in intent to it
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                SportsEvent selectedFromList =(SportsEvent) (listView.getItemAtPosition(myItemInt));
                String s = (new Gson().toJson(selectedFromList));
                Intent destinationIntent = new Intent(getApplicationContext(), DetailActivity.class);
                destinationIntent.putExtra("sportevent", s);
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


        //------
        ArrayList<SportsEvent> arrayOfCreatedEvents = new ArrayList<SportsEvent>();
        // Create the adapter to convert the array to views
        SportAdapter adapterC = new SportAdapter(this, arrayOfCreatedEvents);
        // Attach the adapter to a ListView
        listViewC =  (ListView)findViewById(R.id.listViewCreatedEvents);  //id in xml
        listViewC.setAdapter(adapterC);

        int heightC =0;
        for (SportsEvent event : manager.getCurrentUser().getCreatedEvents()) { {
            heightC= heightC+500;
            adapterC.add(event);
        }}

        listViewC.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, heightC));
        //When an event from the list is clicked open the event details and pass information in intent to it
        listViewC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                SportsEvent selectedFromList =(SportsEvent) (listViewC.getItemAtPosition(myItemInt));
                String s = (new Gson().toJson(selectedFromList));
                Intent destinationIntent = new Intent(getApplicationContext(), DetailActivity.class);
                destinationIntent.putExtra("sportevent", s);
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
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:
                Intent intent = new Intent(this, UserProfileEdit.class);
                startActivityForResult(intent, 1);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        currentUser = manager.getCurrentUser();
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            setup();
        }
    }

}
