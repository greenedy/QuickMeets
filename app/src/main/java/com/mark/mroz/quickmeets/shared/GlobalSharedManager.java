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
    private static final String defaultsLoaded = "defaultsLoaded";
    private static final String USERS_LIST = "UserList";
    private static final String ALL_USERS = "allUsers";
    private static final String CURRENT_USER = "CurrentUser";
    private static final String SPORTS_EVENT_LIST = "SportsEventList";
    private static final String ALL_SPORTS_EVENTS = "AllSportsEvents";
    private User currentUser;


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

    public boolean updateSportsEvent(SportsEvent updatedEvent) {

        Boolean updated = false;

        List<SportsEvent> allEvents = getAllSportsEvents();

        clearAllSportsEvents();

        for (SportsEvent e : allEvents) {
            if (e.getLng() == updatedEvent.getLng() && e.getLat() == updatedEvent.getLat()) {
                updated = saveSportsEvent(updatedEvent);
            } else {
                updated = saveSportsEvent(e);
            }
        }

        return updated;
    }
    // TODO - Add this

    public boolean addUserToEvent(User user, SportsEvent event) {

        List<User> subscribers = event.getSubscribedUsers();
        subscribers.add(user);

        SportsEvent eventToSave = event;

        eventToSave.setSubscribedUsers(subscribers);

        return updateSportsEvent(eventToSave);
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

    public Boolean setCurrentUser(User user) {
        SharedPreferences cUser = appContext.getSharedPreferences(CURRENT_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor  = cUser.edit();


        String eventsJson = new Gson().toJson(user);

        editor.putString(CURRENT_USER, eventsJson);

        return editor.commit();
    }
    public void setDefaults() {
        SharedPreferences dloaded = appContext.getSharedPreferences(defaultsLoaded, MODE_PRIVATE);
        SharedPreferences.Editor editor  = dloaded.edit();


        String eventsJson = new Gson().toJson("true");

        editor.putString(defaultsLoaded, eventsJson);

        editor.commit();

    }
    public boolean getDefaults(){
        SharedPreferences users = appContext.getSharedPreferences(defaultsLoaded, MODE_PRIVATE);
        String json = users.getString(defaultsLoaded, null);

        if (json == null) {
            return false;
        }
        Type type = new TypeToken<String>(){}.getType();
        String r = new Gson().fromJson(json, type);
        if(r.equals("true")){
            return true;
        }
        else{
            return false;
        }

    }

    public User getCurrentUser() {
        SharedPreferences users = appContext.getSharedPreferences(CURRENT_USER, MODE_PRIVATE);
        String json = users.getString(CURRENT_USER, null);

        if (json == null) {
            return new User();
        }

        Type type = new TypeToken<User>(){}.getType();
        User currentUser = new Gson().fromJson(json, type);



        return currentUser;

    }

    }
