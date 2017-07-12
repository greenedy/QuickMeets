package com.mark.mroz.quickmeets.enums;

import com.mark.mroz.quickmeets.models.SportsEvent;

/**
 * Created by markmroz on 2017-07-09.
 */

public enum SportEnum {
    SOCCER, FOOTBALL, BASKETBALL, HOCKEY, TENNIS, DANCING;

    public static SportEnum getEnumFromString(String string) {

        if (string.equals("Soccer")) {
            return SportEnum.SOCCER;
        } else if  (string.equals("Soccer")) {
            return SportEnum.FOOTBALL;
        }  else if (string.equals("Basketball")) {
            return SportEnum.BASKETBALL;
        }  else if (string.equals("Hockey")){
            return SportEnum.HOCKEY;
        }  else if (string.equals("Tennis")) {
            return SportEnum.TENNIS;
        }  else if (string.equals("Dancing")) {
            return SportEnum.DANCING;
        }

        return null;
    }

    public static String asString(SportEnum sportsEnum) {
        switch (sportsEnum) {
            case SOCCER:
                return "Soccer";
            case FOOTBALL:
                return "Football";
            case BASKETBALL:
                return "Basketball";
            case HOCKEY:
                return "Hockey";
            case TENNIS:
                return "Tennis";
            case DANCING:
                return "Dancing";
        }
        return "";
    }


}
