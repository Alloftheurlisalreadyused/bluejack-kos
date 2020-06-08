package id.stanley.binus.bluejackkos.utils;

import android.content.Context;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.models.TransactionModel;
import id.stanley.binus.bluejackkos.models.UserModel;

public class DataStore {
//    private static DataStore instance;
    private Context ctx;

    public DataStore(Context ctx) { this.ctx = ctx; }

    public ArrayList<UserModel> getUsersArrayList() {
        UsersDB usersDB = new UsersDB(ctx);
        return usersDB.getAllUsers();
    }

    public void insertUser(UserModel user) {
        UsersDB usersDB = new UsersDB(ctx);
        usersDB.insertUser(user);
    }

    public ArrayList<TransactionModel> getTransactionsArrayList() {
        TransactionsDB transactionsDB = new TransactionsDB(ctx);
        return transactionsDB.getAllTransactions();
    }

    public void insertTransaction(TransactionModel newTransaction) {
        TransactionsDB transactionsDB = new TransactionsDB(ctx);
        transactionsDB.insertTransaction(newTransaction);
    }
}
