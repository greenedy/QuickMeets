package com.mark.mroz.quickmeets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mark.mroz.quickmeets.enums.SportEnum;
import com.mark.mroz.quickmeets.models.SportAdapter;
import com.mark.mroz.quickmeets.models.SportsEvent;
import com.mark.mroz.quickmeets.shared.GlobalSharedManager;

import java.util.ArrayList;

public class Event_list extends AppCompatActivity {


    private GlobalSharedManager manager;

    public void Event_List(Context x) {
        manager = new GlobalSharedManager(x);

        ArrayList<SportsEvent> arrayOfAllEvents = new ArrayList<SportsEvent>();
        // Create the adapter to convert the array to views
        SportAdapter adapterC = new SportAdapter(this, arrayOfAllEvents);
        // Attach the adapter to a ListView
        ListView listViewC =  (ListView)findViewById(R.id.listViewAllEvents);  //id in xml
        listViewC.setAdapter(adapterC);

       // int heightC =0;
        for (SportsEvent event : manager.getCurrentUser().getCreatedEvents()) { {
       //     heightC= heightC+500;
           adapterC.add(event);
        }}

      //  listViewC.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, heightC));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


        manager = new GlobalSharedManager(getApplicationContext());






    }








}

