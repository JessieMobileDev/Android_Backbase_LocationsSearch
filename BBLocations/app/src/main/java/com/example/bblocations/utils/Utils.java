package com.example.bblocations.utils;

import android.content.Context;
import com.example.bblocations.models.City;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Utils {

    /**
     * Checks if a given object is null
     * @param object the object that needs to be checked
     * @return boolean
     */
    public static boolean isNull(Object object) {
        return Objects.equals(object, null);
    }

    /**
     * Reads JSON file in assets and convert to string
     * @param context
     * @param fileId id of the file in the assets
     * @return String
     */
    public static String getJSONString(Context context, Integer fileId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(fileId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Takes in the list that needs sorting. Provides sort type to determine how the list will be
     * sorted.
     * @param type enum provided in this class
     * @param list list of City objects to be sorted
     * @return the same List<City> but sorted in the way chosen
     */
    public static List<City> sortListBy(final SortType type, List<City> list) {
        List<City> tempList = list;
        if(!Utils.isNull(tempList)) {
            Collections.sort(tempList, new Comparator<City>() {
                @Override
                public int compare(City city1, City city2) {
                    if (type == SortType.NAME) {
                        return city1.getName().compareTo(city2.getName());
                    }
                    return city1.getCountry().compareTo(city2.getCountry());
                }
            });
        }
        return tempList;
    }

    /**
     * Filter a list based on given text.
     * @param originalList
     * @param text
     * @return
     */
    public static List<City> getFilteredList(List<City> originalList, CharSequence text) {
        List<City> filteredList;
        if(Utils.isNull(text) || text.length() == 0) {
            return originalList;
        } else {
            filteredList = new ArrayList<>();
            for (City city: originalList) {
                if(city.getName().startsWith(text.toString()))  {
                    filteredList.add(city);
                }
            }
        }
        return filteredList.size() == 0 ? originalList : filteredList;
    }

    /**
     * Enum to help with sorting list.
     */
    public enum SortType {
        NAME,
        COUNTRY
    }
}
