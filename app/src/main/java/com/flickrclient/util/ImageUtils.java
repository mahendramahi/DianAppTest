package com.flickrclient.util;

import com.flickrclient.data.model.Person;



public final class ImageUtils {

    private ImageUtils() {
        //no-instance
    }

    public static String getProfileImage(Person person) {
        if (person == null) {
            return "";
        }

        return "http://farm" + person.getIconfarm() + ".staticflickr.com/"
                + person.getIconserver()
                + "/buddyicons/" + person.getNsid() + "_l.jpg";
    }

}
