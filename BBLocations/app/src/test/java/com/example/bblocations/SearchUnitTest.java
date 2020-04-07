package com.example.bblocations;

import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchUnitTest {

    /**
     * We know that the list contains 4 cities that start with "Te". We are asserting that the
     * filtered list contains the expected 4 cities.
     */
    @Test
    public void search() {
        ArrayList<City> cities = createList();
        ArrayList<City> filtered = Utils.getFilteredList(cities, "te");
        assertEquals(4, filtered.size());

        filtered = Utils.getFilteredList(cities, "Cali");
        assertEquals(1, filtered.size());

        filtered = Utils.getFilteredList(cities, "Wash");
        assertNotEquals(cities.size(), filtered.size());
    }

    /**
     * Here we have fake data for testing. There are 4 cities that start with "Te".
     */
    private ArrayList<City> createList() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City("US", "Texas", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Tennessee", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Oregon", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Temecula", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Tecopa", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Colorado", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Washington", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "Missouri", 0, new City.Coordinates(0.0f, 0.0f)));
        cities.add(new City("US", "California", 0, new City.Coordinates(0.0f, 0.0f)));
        return cities;
    }
}