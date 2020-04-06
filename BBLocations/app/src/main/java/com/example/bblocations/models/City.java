package com.example.bblocations.models;

import java.io.Serializable;

public class City implements Serializable {
    private String country;
    private String city;
    private Integer id;
    private Coordinates coordinates;

    private static class Coordinates implements Serializable {
        private Float longitude;
        private Float latitude;
    }
}
