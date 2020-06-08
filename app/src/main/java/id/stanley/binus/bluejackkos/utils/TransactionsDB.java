package id.stanley.binus.bluejackkos.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.models.TransactionModel;
import id.stanley.binus.bluejackkos.models.UserModel;

public class TransactionsDB {
    private DatabaseHelper dbHelper;
    private Context ctx;

    public TransactionsDB(Context ctx) {
        this.ctx = ctx;
        dbHelper = new DatabaseHelper(ctx);
    }

    public TransactionModel getTransaction(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKINGS, null,
                selection, selectionArgs, null, null, null);

        cursor.moveToFirst();

        TransactionModel transactionModel = new TransactionModel(
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_ID)),
                cursor.getString(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_NAME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_FACILITY)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_PRICE)),
                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_LAT)),
                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_LON)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_DATE))
        );

        cursor.close();
        db.close();
        return transactionModel;
    }

    public ArrayList<TransactionModel> getAllTransactions() {
        ArrayList<TransactionModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BOOKINGS,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                arrayList.add(
                        new TransactionModel(
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_ID)),
                                cursor.getString(cursor.getColumnIndex("userId")),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_FACILITY)),
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_PRICE)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_LAT)),
                                cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_KOST_LON)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_BOOKING_DATE))
                        )
                );
                cursor.moveToNext();
            }
        }

        cursor.close();
        return arrayList;
    }

    public void insertTransaction(TransactionModel product) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.FIELD_BOOKING_ID, product.getUserId());
        cv.put("userId", product.getUserId());
        cv.put(DatabaseHelper.FIELD_BOOKING_KOST_NAME, product.getKostName());
        cv.put(DatabaseHelper.FIELD_BOOKING_KOST_FACILITY, product.getKostFacility());
        cv.put(DatabaseHelper.FIELD_BOOKING_KOST_PRICE, product.getKostPrice());
        cv.put(DatabaseHelper.FIELD_BOOKING_KOST_LAT, product.getKostLat());
        cv.put(DatabaseHelper.FIELD_BOOKING_KOST_LON, product.getKostLon());
        cv.put(DatabaseHelper.FIELD_BOOKING_DATE, product.getBookingDate());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(DatabaseHelper.TABLE_BOOKINGS, null, cv);
        db.close();
    }

    public void removeTransaction(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "bookingId = ?";
        String[] selectionArgs = {"" + id};

        db.delete(DatabaseHelper.TABLE_BOOKINGS, selection, selectionArgs);
    }
}
