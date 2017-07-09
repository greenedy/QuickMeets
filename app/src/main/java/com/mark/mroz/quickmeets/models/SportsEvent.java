package com.mark.mroz.quickmeets.models;

import com.mark.mroz.quickmeets.enums.SportEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by markmroz on 2017-07-09.
 */

public class SportsEvent {

    private Long id;

    private SportEnum sport;

    private int maxPlayers;
    private int intensity;

    private Date startTime;
    private Date endTime;

    private User eventCreator;
    private List<User> subscribedUsers;

    public SportsEvent(Long id, SportEnum sport, int maxPlayers, int intensity, Date startTime, Date endTime, User eventCreator, List<User> subscribedUsers) {
        this.id = id;
        this.sport = sport;
        this.maxPlayers = maxPlayers;
        this.intensity = intensity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventCreator = eventCreator;
        this.subscribedUsers = subscribedUsers;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportEnum getSport() {
        return sport;
    }

    public void setSport(SportEnum sport) {
        this.sport = sport;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public User getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(User eventCreator) {
        this.eventCreator = eventCreator;
    }

    public List<User> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<User> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    @Override
    public String toString() {
        return "SportsEvent{" +
                "id=" + id +
                ", sport=" + sport +
                ", maxPlayers=" + maxPlayers +
                ", intensity=" + intensity +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", eventCreator=" + eventCreator +
                ", subscribedUsers=" + subscribedUsers +
                '}';
    }
}
