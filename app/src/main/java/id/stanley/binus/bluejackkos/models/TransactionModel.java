package id.stanley.binus.bluejackkos.models;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import id.stanley.binus.bluejackkos.views.BookingTransactionsActivity;

public class TransactionModel {
    private String bookingId;
    private String userId;
    private Object BookingTransactionsActivity;

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

    public TransactionModel(String bookingId, String userId, String kostName, String kostFacility, int kostPrice, double kostLat, double kostLon, String bookingDate, ImageView kostImage) {
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
    private ImageView kostImage;
    private String kostImageUrl;

    public ImageView getKostImage() {
        return kostImage;
    }

    public void setKostImage(ImageView kostImage) {
        Glide.with((Activity) BookingTransactionsActivity).load(kostImageUrl).into(kostImage);
    }
}
