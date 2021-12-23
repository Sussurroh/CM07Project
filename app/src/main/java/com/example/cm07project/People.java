package com.example.cm07project;

public class People {
    /** Represents People on Firebase DB*/
    public String eventid;
    public String name;

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public People(String eventid, String name) {
        this.eventid = eventid;
        this.name = name;
    }

    public People() {
    }
}
