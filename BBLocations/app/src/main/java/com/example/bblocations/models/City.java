package com.example.bblocations.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {
    @SerializedName("country")
    private String country;
    @SerializedName("name")
    private String name;
    @SerializedName("_id")
    private Integer id;
    @SerializedName("coord")
    private Coordinates coord;

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public Integer get_id() {
        return id;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public static class Coordinates implements Serializable {
        @SerializedName("lon")
        private Float lon;
        @SerializedName("lat")
        private Float lat;

        public Float getLon() {
            return lon;
        }

        public Float getLat() {
            return lat;
        }
    }
}


