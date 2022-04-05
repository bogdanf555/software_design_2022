package com.foodpanda.common;

public class RestaurantDTO {

    private String name;
    private String location;

    public RestaurantDTO(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
