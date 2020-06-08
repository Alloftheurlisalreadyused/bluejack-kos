package id.stanley.binus.bluejackkos.models;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class KostModel {
    private int kostId;
    private String kostName;
    private String kostFacility;
    private int kostPrice;
    private String kostDescription;
    private double kostLongitude;
    private double kostLatitude;
    private int kostImage;

    public KostModel(int kostId, String kostName, String kostFacility, int kostPrice, String kostDescription, double kostLongitude, double kostLatitude, int kostImage) {
        this.kostId = kostId;
        this.kostName = kostName;
        this.kostFacility = kostFacility;
        this.kostPrice = kostPrice;
        this.kostDescription = kostDescription;
        this.kostLongitude = kostLongitude;
        this.kostLatitude = kostLatitude;
        this.kostImage = kostImage;
    }

    public int getKostId() {
        return kostId;
    }

    public void setKostId(int kostId) {
        this.kostId = kostId;
    }

    public String getKostName() {
        return kostName;
    }

    public void setKostName(String kostName) {
        this.kostName = kostName;
    }

    public String getKostFacility() {
        return kostFacility;
    }

    public void setKostFacility(String kostFacility) {
        this.kostFacility = kostFacility;
    }

    public int getKostPrice() {
        return kostPrice;
    }

    public void setKostPrice(int kostPrice) {
        this.kostPrice = kostPrice;
    }

    public String getKostDescription() {
        return kostDescription;
    }

    public void setKostDescription(String kostDescription) {
        this.kostDescription = kostDescription;
    }

    public double getKostLongitude() {
        return kostLongitude;
    }

    public void setKostLongitude(double kostLongitude) {
        this.kostLongitude = kostLongitude;
    }

    public double getKostLatitude() {
        return kostLatitude;
    }

    public void setKostLatitude(double kostLatitude) {
        this.kostLatitude = kostLatitude;
    }

    public int getKostImage() {
        return kostImage;
    }

    public void setKostImage(int kostImage) {
        this.kostImage = kostImage;
    }
}
