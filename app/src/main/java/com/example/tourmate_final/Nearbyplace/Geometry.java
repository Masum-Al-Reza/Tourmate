
package com.example.tourmate_final.Nearbyplace;

import com.example.tourmate_final.Location_fragment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("locationFragment")
    @Expose
    private Location_fragment locationFragment;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    public Location_fragment getLocationFragment() {
        return locationFragment;
    }

    public void setLocationFragment(Location_fragment locationFragment) {
        this.locationFragment = locationFragment;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

}
