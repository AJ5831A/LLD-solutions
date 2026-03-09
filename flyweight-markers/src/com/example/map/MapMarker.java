package com.example.map;

/**
 * A map marker that stores only extrinsic state (lat, lng, label)
 * plus a reference to a shared MarkerStyle (intrinsic state).
 */
public class MapMarker {

    private final double lat;
    private final double lng;
    private final String label;
    private final MarkerStyle style;

    public MapMarker(double lat, double lng, String label, MarkerStyle style) {
        this.lat = lat;
        this.lng = lng;
        this.label = label;
        this.style = style;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getLabel() {
        return label;
    }

    public MarkerStyle getStyle() {
        return style;
    }
}
