package com.dinus.ec.service;

/**
 * Created by crocodic-mbp-9 on 9/5/17.
 */

public class Config {

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    public static final String SHARED_PREF = "danamonNotif";

    public static final String base = "http://crocodic.net/danamon/public/api/" ;

    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static String API_KEY = "AIzaSyBCVHwa52eXdR0iBwwxkpyXd9WChqC0mVQ";

    public static final String PACKAGE_NAME = "com.crocodic.bluegaz";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA";
    public static final String LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY";
    public static final String LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET";
}
