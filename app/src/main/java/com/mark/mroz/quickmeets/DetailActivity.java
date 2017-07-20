package com.mark.mroz.quickmeets;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import static com.mark.mroz.quickmeets.enums.SportEnum.DANCING;
import static com.mark.mroz.quickmeets.enums.SportEnum.FOOTBALL;
import static com.mark.mroz.quickmeets.enums.SportEnum.SOCCER;
import static com.mark.mroz.quickmeets.enums.SportEnum.TENNIS;

public class DetailActivity extends AppCompatActivity {
    GlobalSharedManager manager;
    private String eventSport, capacity, intensity, equipement,eventCreator, description, joinedPeople, startTime,endTime, startDate, endDate;
    private TextView txtEventSport, txtCapcity, txtIntensity, txtEquipment, txtEventCreator, txtDescription, txtStartTime, txtEndTime,txtStartDate,txtEndDate;
    private ImageView sportpic;
    private Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        manager = new GlobalSharedManager(this);

        txtEventSport = (TextView) findViewById(R.id.txtTitle);
        txtCapcity = (TextView) findViewById(R.id.txtPlayers);
        txtIntensity = (TextView) findViewById(R.id.txtIntensity);
        txtEquipment = (TextView) findViewById(R.id.txtEquipment);
        txtEventCreator = (TextView) findViewById(R.id.txtEventCreator);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        sportpic = (ImageView) findViewById(R.id.imgSportView);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        txtStartDate = (TextView) findViewById(R.id.txtDate);
        txtEndDate = (TextView) findViewById(R.id.txtDate2);

        //get values from intent
        Intent mIntent = getIntent();
        eventSport = mIntent.getStringExtra("sport");
        capacity = mIntent.getStringExtra("players");
        intensity = mIntent.getStringExtra("intensity");
        equipement = mIntent.getStringExtra("equipment");
        eventCreator = mIntent.getStringExtra("eventCreator");
        description = mIntent.getStringExtra("description");
        joinedPeople = mIntent.getStringExtra("joinedPeople");
        startTime = mIntent.getStringExtra("start_time");
        endTime = mIntent.getStringExtra("end_time");

        //Set the values int the labels
        txtEventSport.setText(eventSport);
        txtCapcity.setText("Capacity: "+joinedPeople + " / " + capacity + " Players");
        txtIntensity.setText("Intensity: "+ intensity);
        txtEquipment.setText("Equipment: "+ equipement);
        txtDescription.setText("Description: "+ description);
        txtEventCreator.setText("Event Creator: "+ eventCreator);
        txtStartDate.setText("Start: " + startTime);
        txtEndDate.setText("End: "+endTime);
        Drawable drawableImage = null;
        //Display sport image
       switch (eventSport) {
            case "SOCCER":
                drawableImage = ContextCompat.getDrawable(this, R.drawable.soccer);
                break;
            case "TENNIS":
                drawableImage = ContextCompat.getDrawable(this, R.drawable.tennis);
                break;
            case "FOOTBALL":
                drawableImage = ContextCompat.getDrawable(this, R.drawable.football);
                break;
            case "DANCING":
                drawableImage = ContextCompat.getDrawable(this, R.drawable.dancing);
                break;
        }

        if (drawableImage != null) {
            sportpic.setImageDrawable(drawableImage);
       }

    }

    //When the join button is clicked
    public void onJoin(View view) {

        Toast.makeText(getApplicationContext(), "Joined Event", Toast.LENGTH_LONG).show();
        btnJoin.setText("Joined");
        btnJoin.setEnabled(false);
    }





}
