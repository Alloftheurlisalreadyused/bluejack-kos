package id.stanley.binus.bluejackkos.models;

public class TransactionModel {
    private String bookingId;
    private String userId;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public double getKostLat() {
        return kostLat;
    }

    public void setKostLat(double kostLat) {
        this.kostLat = kostLat;
    }

    public double getKostLon() {
        return kostLon;
    }

    public void setKostLon(double kostLon) {
        this.kostLon = kostLon;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public TransactionModel(String bookingId, String userId, String kostName, String kostFacility, int kostPrice, double kostLat, double kostLon, String bookingDate, int kostImage) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.kostName = kostName;
        this.kostFacility = kostFacility;
        this.kostPrice = kostPrice;
        this.kostLat = kostLat;
        this.kostLon = kostLon;
        this.bookingDate = bookingDate;
        this.kostImage = kostImage;
    }

    private String kostName;
    private String kostFacility;
    private int kostPrice;
    private double kostLat;
    private double kostLon;
    private String bookingDate;
    private int kostImage;

    public int getKostImage() {
        return kostImage;
    }

    public void setKostImage(int kostImage) {
        this.kostImage = kostImage;
    }
}
