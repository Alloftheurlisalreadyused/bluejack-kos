package id.stanley.binus.bluejackkos.utils;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.models.TransactionModel;
import id.stanley.binus.bluejackkos.models.UserModel;

public class DataStore {
    private static DataStore instance;

    private ArrayList<UserModel> usersArrayList = new ArrayList<>();
    private ArrayList<KostModel> kostArrayList = new ArrayList<>();
    private ArrayList<TransactionModel> transactionsArrayList = new ArrayList<>();

    private DataStore() {}

    public static synchronized DataStore getInstance(){
        if(instance==null){
            instance=new DataStore();
        }
        return instance;
    }

    public ArrayList<UserModel> getUsersArrayList() {
        return usersArrayList;
    }

    public void setUsersArrayList(ArrayList<UserModel> usersArrayList) {
        this.usersArrayList = usersArrayList;
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
