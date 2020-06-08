package id.stanley.binus.bluejackkos.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BluejackDB";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String FIELD_USER_ID = "id";
    public static final String FIELD_USER_NAME = "name";
    public static final String FIELD_USER_PASSWORD = "password";
    public static final String FIELD_USER_PHONE = "phone";
    public static final String FIELD_USER_GENDER = "gender";
    public static final String FIELD_USER_DOB = "dob";

    public static final String TABLE_BOOKINGS = "bookings";
    public static final String FIELD_BOOKING_ID = "bookingId";
    public static final String FIELD_BOOKING_USER_ID = "userId";
    public static final String FIELD_BOOKING_KOST_NAME = "kostName";
    public static final String FIELD_BOOKING_KOST_FACILITY = "kostFacility";
    public static final String FIELD_BOOKING_KOST_PRICE = "kostPrice";
    public static final String FIELD_BOOKING_KOST_LAT = "kostLat";
    public static final String FIELD_BOOKING_KOST_LON = "kostLon";
    public static final String FIELD_BOOKING_KOST_DESCRIPTION = "kostDescription";
    public static final String FIELD_BOOKING_DATE = "bookingDate";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
            FIELD_USER_ID + " TEXT PRIMARY KEY, " +
            FIELD_USER_NAME + " TEXT, " +
            FIELD_USER_PASSWORD + " TEXT, " +
            FIELD_USER_PHONE + " TEXT, " +
            FIELD_USER_GENDER + " TEXT, " +
            FIELD_USER_DOB + " TEXT) ";

    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS + ";";

    private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKINGS + "(" +
            FIELD_BOOKING_ID + " TEXT PRIMARY KEY, " +
            FIELD_BOOKING_USER_ID + " INTEGER, " +
            FIELD_BOOKING_KOST_NAME + " TEXT, " +
            FIELD_BOOKING_KOST_FACILITY + " TEXT, " +
            FIELD_BOOKING_KOST_DESCRIPTION + " TEXT, " +
            FIELD_BOOKING_KOST_PRICE + " INTEGER, " +
            FIELD_BOOKING_KOST_LAT + " REAL, " +
            FIELD_BOOKING_KOST_LON + " REAL, " +
            FIELD_BOOKING_DATE + " TEXT) ";

    private static final String DROP_TABLE_BOOKINGS = "DROP TABLE IF EXISTS " + TABLE_BOOKINGS + ";";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP THE EXISTING TABLES AND RECREATE AGAIN
        db.execSQL(DROP_TABLE_USERS);
        db.execSQL(DROP_TABLE_BOOKINGS);
        onCreate(db);
    }
}
