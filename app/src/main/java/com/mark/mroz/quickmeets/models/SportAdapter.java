package com.mark.mroz.quickmeets.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mark.mroz.quickmeets.R;

import java.util.ArrayList;

/**
 * Created by green on 2017-07-13.
 */

public class SportAdapter extends ArrayAdapter<SportsEvent>{


    // View lookup cache
    private static class ViewHolder {
        TextView sportName;
        TextView totalPlayers;
        TextView intensity;
        TextView equipment;
        ImageView picSport;
    }

    public SportAdapter(Context context, ArrayList<SportsEvent> events) {
        super(context, R.layout.list_row_style, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SportsEvent event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row_style, parent, false);
            viewHolder.sportName = (TextView) convertView.findViewById(R.id.textView_SportName);
            viewHolder.totalPlayers = (TextView) convertView.findViewById(R.id.totalplayer);
            viewHolder.intensity= (TextView) convertView.findViewById(R.id.intencity);
            viewHolder.equipment = (TextView) convertView.findViewById(R.id.equipments);
            viewHolder.picSport = (ImageView) convertView.findViewById(R.id.eventpic);


            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.sportName.setText(event.getSport().toString());
        viewHolder.totalPlayers.setText("Capacity: "+event.getSubscribedUsers().size() + " / " + event.getMaxPlayers() + " Players");
        viewHolder.intensity.setText("Intensity: "+String.valueOf(event.getIntensity()));
        viewHolder.equipment.setText("Equipment Available: "+event.getEquipment().toString());

        Drawable drawableImage = null;

        switch (event.getSport()) {
            case SOCCER:
                drawableImage = ContextCompat.getDrawable(getContext(), R.drawable.soccer);
                break;
            case TENNIS:
                drawableImage = ContextCompat.getDrawable(getContext(), R.drawable.tennis);
                break;
            case FOOTBALL:
                drawableImage = ContextCompat.getDrawable(getContext(), R.drawable.football);
                break;
            case DANCING:
                drawableImage = ContextCompat.getDrawable(getContext(), R.drawable.dancing);
                break;
        }

        if (drawableImage != null) {
            viewHolder.picSport.setImageDrawable(drawableImage);
        }

        // Return the completed view to render on screen
        return convertView;
    }

  //  @Override
  //  public View getView(int position, View convertView, ViewGroup parent) {
        // ...
        // Lookup view for data population
   //   / Button btButton = (Button) convertView.findViewById(R.id.btButton);
        // Cache row position inside the button using `setTag`
    //    btButton.setTag(position);
        // Attach the click event handler
     //   btButton.setOnClickListener(new View.OnClickListener() {
     //       @Override
     //       public void onClick(View view) {
      //          int position = (Integer) view.getTag();
                // Access the row position here to get the correct data item
      //          SportsEvent sEvent = getItem(position);
                // Do what you want here...
      //      }
    ////    });
        // ... other view population as needed...
        // Return the completed view
     //   return convertView;
   // }
}
