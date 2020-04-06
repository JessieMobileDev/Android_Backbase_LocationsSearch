package com.example.bblocations.utils;

import java.util.Objects;

public class Utils {

    /**
     * Checks if a given object is null
     * @param object
     * @return boolean
     */
    public static boolean isNull(Object object) {
        return Objects.equals(object, null);
    }
}
