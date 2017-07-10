package com.mark.mroz.quickmeets.models;

import com.mark.mroz.quickmeets.enums.SportEnum;

import java.util.Date;
import java.util.List;

/**
 * Created by markmroz on 2017-07-09.
 * Filled out by dylangreene
 */

public class User {

    private int userID;
    private String name;
    private int age;
    private String email;
    private String password;
    private List<SportsEvent> joinedEvents;
    private List<SportsEvent> createdEvents;
    private List<SportEnum> favouriteSports;
    private String bio;

    public User(int userID, String name, int age,String email,String password, List<SportsEvent> joinedEvents, List<SportsEvent> createdEvents,List<SportEnum> favouriteSports, String bio) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.joinedEvents = joinedEvents;
        this.createdEvents = createdEvents;
        this.favouriteSports = favouriteSports;
        this.bio = bio;
    }
    public User() {
        this.name = "Test User";
        this.age = 18;
        this.joinedEvents = joinedEvents;
        this.createdEvents = createdEvents;
        this.favouriteSports = favouriteSports;
        this.bio = "Create a Bio";

    }
    public int getUserID() {
        return userID;
    }

    public void setUSerID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SportsEvent> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(List<SportsEvent> joinedEvents) { this.joinedEvents = joinedEvents; }

    public void addJoinedEvent(SportsEvent joinedEvent) {  this.joinedEvents.add(joinedEvent); }

    public void removeJoinedEvent(SportsEvent removeJoinedEvent) {  this.joinedEvents.remove(removeJoinedEvent); }

    public List<SportsEvent> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<SportsEvent> createdEvents) { this.createdEvents = createdEvents; }

    public void addCreatedEvent(SportsEvent createdEvent) {  this.createdEvents.add(createdEvent); }

    public void removeCreatedEvent(SportsEvent removeCreatedEvent) {  this.createdEvents.remove(removeCreatedEvent); }

    public List<SportEnum> getfavouriteSports() { return favouriteSports;  }

    public void setFavouriteSports(List<SportEnum> favouriteSports) { this.favouriteSports = favouriteSports; }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {this.bio = bio;}
}
