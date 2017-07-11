package com.mark.mroz.quickmeets.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mark.mroz.quickmeets.models.SportsEvent;
import com.mark.mroz.quickmeets.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by markmroz on 2017-07-09.
 */

public class GlobalSharedManager {

    private Context appContext;

    private static final String USERS_LIST = "UserList";
    private static final String ALL_USERS = "allUsers";

    private static final String SPORTS_EVENT_LIST = "SportsEventList";
    private static final String ALL_SPORTS_EVENTS = "allSportsEvents";

    public GlobalSharedManager(Context c) {
        this.appContext = c;
    }

    public boolean saveSportsEvent(SportsEvent sportsEvent) {

        SharedPreferences settings = appContext.getSharedPreferences(SPORTS_EVENT_LIST, MODE_PRIVATE);
        SharedPreferences.Editor editor  = settings.edit();

        List<SportsEvent> events = getAllSportsEvents();
        events.add(sportsEvent);

        String eventsJson = new Gson().toJson(events);

        editor.putString(ALL_SPORTS_EVENTS, eventsJson);

        return editor.commit();
    }

    public List<SportsEvent> getAllSportsEvents() {

        SharedPreferences settings = appContext.getSharedPreferences(SPORTS_EVENT_LIST, MODE_PRIVATE);

        String json = settings.getString(ALL_SPORTS_EVENTS, null);

        if (json == null) {
            return new ArrayList<SportsEvent>();
        }

        Type type = new TypeToken<List<SportsEvent>>(){}.getType();
        List<SportsEvent> sportsEventList = new Gson().fromJson(json, type);

        return sportsEventList;
    }

    public void clearAllSportsEvents() {
        SharedPreferences preferences = appContext.getSharedPreferences(SPORTS_EVENT_LIST, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean saveUser(User user) {
        SharedPreferences users = appContext.getSharedPreferences(USERS_LIST, MODE_PRIVATE);
        SharedPreferences.Editor editor  = users.edit();

        List<User> usersList = getAllUsers();
        usersList.add(user);

        String eventsJson = new Gson().toJson(usersList);

        editor.putString(ALL_USERS, eventsJson);

        return editor.commit();
    }

    public List<User> getAllUsers() {
        SharedPreferences users = appContext.getSharedPreferences(USERS_LIST, MODE_PRIVATE);
        String json = users.getString(ALL_USERS, null);

        if (json == null) {
            return new ArrayList<User>();
        }

        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> userList = new Gson().fromJson(json, type);

        return userList;
    }
    public User getUser(int requestID) {
        SharedPreferences users = appContext.getSharedPreferences(USERS_LIST, MODE_PRIVATE);
        String json = users.getString(ALL_USERS, null);

        if (json == null) {
            return new User();
        }

        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> userList = new Gson().fromJson(json, type);
        User foundUser = new User();
        for (int x=0; x<userList.size(); x++) {
            if (userList.get(x).getUserID() == requestID) {
                foundUser=userList.get(x);
            }
        }
        return foundUser;

    }

    }
