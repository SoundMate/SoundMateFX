/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 27/01/21, 00:19
 * Last edited: 27/01/21, 00:19
 */

package it.soundmate.bean;

public class MapBean {

    private final double lat;
    private final double lng;

    public MapBean(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
