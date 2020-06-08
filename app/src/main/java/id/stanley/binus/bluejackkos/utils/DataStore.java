package id.stanley.binus.bluejackkos.utils;

import android.content.Context;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.models.TransactionModel;
import id.stanley.binus.bluejackkos.models.UserModel;

public class DataStore {
//    private static DataStore instance;
    private Context ctx;

    private ArrayList<KostModel> kostArrayList = new ArrayList<>();
    private ArrayList<TransactionModel> transactionsArrayList = new ArrayList<>();

    public DataStore(Context ctx) { this.ctx = ctx; }

    public ArrayList<UserModel> getUsersArrayList() {
        UsersDB usersDB = new UsersDB(ctx);
        return usersDB.getAllUsers();
    }

    public void insertUser(UserModel user) {
        UsersDB usersDB = new UsersDB(ctx);
        usersDB.insertUser(user);
    }

    public ArrayList<KostModel> getKostArrayList() {
        return kostArrayList;
    }

    public void setKostArrayList(ArrayList<KostModel> kostArrayList) {
        this.kostArrayList = kostArrayList;
    }

    public ArrayList<TransactionModel> getTransactionsArrayList() {
        return transactionsArrayList;
    }

    public void setTransactionsArrayList(ArrayList<TransactionModel> transactionsArrayList) {
        this.transactionsArrayList = transactionsArrayList;
    }
}
