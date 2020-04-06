package com.example.bblocations.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.Objects;

public class ConnectionHandler {

    /**
     * Checks if the device has connectivity
     * @param context
     * @return boolean
     */
    public static boolean isConnected(Context context) {

        // Check if the user's phone is connected to the internet
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connManager).getActiveNetworkInfo();
        return !Utils.isNull(networkInfo) && networkInfo.isConnected();
    }
}
