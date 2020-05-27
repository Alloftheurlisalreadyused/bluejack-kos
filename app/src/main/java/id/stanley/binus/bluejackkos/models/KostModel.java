package id.stanley.binus.bluejackkos.models;

public class KostModel {
    private int kostId;
    private String kostName;
    private String kostFacility;
    private int kostPrice;
    private String kostAddress;
    private double kostLongitude;
    private double kostLatitude;
    private String kostImage;

    public KostModel(int kostId, String kostName, String kostFacility, int kostPrice, String kostAddress, double kostLongitude, double kostLatitude, String kostImage) {
        this.kostId = kostId;
        this.kostName = kostName;
        this.kostFacility = kostFacility;
        this.kostPrice = kostPrice;
        this.kostAddress = kostAddress;
        this.kostLongitude = kostLongitude;
        this.kostLatitude = kostLatitude;
        this.kostImage = kostImage;
    }

    public KostModel() {

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

    public String getKostAddress() {
        return kostAddress;
    }

    public void setKostAddress(String kostAddress) {
        this.kostAddress = kostAddress;
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

    public String getKostImage() {
        return kostImage;
    }

    public void setKostImage(String kostImage) {
        this.kostImage = kostImage;
    }
}
