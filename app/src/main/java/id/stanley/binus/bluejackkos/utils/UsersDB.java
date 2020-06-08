package id.stanley.binus.bluejackkos.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.models.UserModel;

public class UsersDB {
    private DatabaseHelper dbHelper;
    private Context ctx;

    public UsersDB(Context ctx) {
        this.ctx = ctx;
        dbHelper = new DatabaseHelper(ctx);
    }

    public UserModel getUser(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null,
                selection, selectionArgs, null, null, null);

        cursor.moveToFirst();

        UserModel user = new UserModel(
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_NAME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_PHONE)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_GENDER)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_DOB))
        );

        cursor.close();
        db.close();
        return user;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                arrayList.add(
                        new UserModel(
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_NAME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_PASSWORD)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_PHONE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_GENDER)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIELD_USER_DOB))
                        )
                );
                cursor.moveToNext();
            }
        }

        cursor.close();
        return arrayList;
    }

    public void insertUser(UserModel product) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.FIELD_USER_ID, product.getUserId());
        cv.put(DatabaseHelper.FIELD_USER_NAME, product.getUsername());
        cv.put(DatabaseHelper.FIELD_USER_PASSWORD, product.getPassword());
        cv.put(DatabaseHelper.FIELD_USER_PHONE, product.getPhoneNumber());
        cv.put(DatabaseHelper.FIELD_USER_GENDER, product.getGender());
        cv.put(DatabaseHelper.FIELD_USER_DOB, product.getDob());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.insert(DatabaseHelper.TABLE_USERS, null, cv);
        db.close();
    }

    public void updateUser(int id, UserModel product) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.FIELD_USER_NAME, product.getUsername());
        cv.put(DatabaseHelper.FIELD_USER_PASSWORD, product.getPassword());
        cv.put(DatabaseHelper.FIELD_USER_PHONE, product.getPhoneNumber());
        cv.put(DatabaseHelper.FIELD_USER_GENDER, product.getGender());
        cv.put(DatabaseHelper.FIELD_USER_DOB, product.getDob());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};

        db.update(DatabaseHelper.TABLE_USERS, cv, selection, selectionArgs);
    }

    public void removeUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = {"" + id};

        db.delete(DatabaseHelper.TABLE_USERS, selection, selectionArgs);
    }
}
