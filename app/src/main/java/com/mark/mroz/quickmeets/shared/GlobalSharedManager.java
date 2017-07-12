package com.mark.mroz.quickmeets.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mark.mroz.quickmeets.models.SportsEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmroz on 2017-07-09.
 */

public class GlobalSharedManager {

    private Context appContext;

    private static final String USERS_LIST = "UserList";

    private static final String SPORTS_EVENT_LIST = "SportsEventList";
    private static final String ALL_SPORTS_EVENTS = "AllSportsEvents";

    public GlobalSharedManager(Context c) {
        this.appContext = c;
    }

    public boolean saveSportsEvent(SportsEvent sportsEvent) {

        SharedPreferences settings = appContext.getSharedPreferences(SPORTS_EVENT_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = settings.edit();

        List<SportsEvent> events = getAllSportsEvents();
        events.add(sportsEvent);

        String eventsJson = new Gson().toJson(events);

        editor.putString(ALL_SPORTS_EVENTS, eventsJson);

        return editor.commit();
    }

    public List<SportsEvent> getAllSportsEvents() {

        SharedPreferences settings = appContext.getSharedPreferences(SPORTS_EVENT_LIST, Context.MODE_PRIVATE);

        String json = settings.getString(ALL_SPORTS_EVENTS, null);

        if (json == null) {
            return new ArrayList<SportsEvent>();
        }

        Type type = new TypeToken<List<SportsEvent>>(){}.getType();
        List<SportsEvent> sportsEventList = new Gson().fromJson(json, type);

        return sportsEventList;
    }

    public void clearAllSportsEvents() {
        SharedPreferences preferences = appContext.getSharedPreferences(SPORTS_EVENT_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
