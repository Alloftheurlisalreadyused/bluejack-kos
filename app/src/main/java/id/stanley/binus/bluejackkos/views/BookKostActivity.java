package id.stanley.binus.bluejackkos.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.models.TransactionModel;
import id.stanley.binus.bluejackkos.models.UserModel;
import id.stanley.binus.bluejackkos.utils.DataStore;

public class BookKostActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private DataStore dataStore = DataStore.getInstance();
    private TextView bookingMessage;
    private EditText dateTextField;
    private DatePickerDialog picker;
    private MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_kost);

        toolbarTitle = findViewById(R.id.titleText);
        toolbar = findViewById(R.id.toolbar);
        bookingMessage = findViewById(R.id.bookingMessage);
        dateTextField = findViewById(R.id.dateTextField);
        button = findViewById(R.id.buttonBook);

        dateTextField.setOnClickListener(view -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> dateTextField.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
            picker.show();
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.book_this_kost));

        Intent intent = getIntent();

        int kostId = intent.getIntExtra("kostId", 0);
        String userId = intent.getStringExtra("userId");

        if (kostId == 0) finish();

        ArrayList<KostModel> kosts = dataStore.getKostArrayList();
        KostModel currentKost = null;

        for (int x = 0; x<kosts.size(); ++x) {
            if (kosts.get(x).getKostId() == kostId) {
                currentKost = kosts.get(x);
                Log.d("KOST3", String.valueOf(kostId));
                break;
            }
        }

        if (currentKost != null && userId != null) {
            ArrayList<UserModel> users = dataStore.getUsersArrayList();
            UserModel currentUser = null;

            // get user data
            for (int x = 0; x<users.size(); ++x) {
                if (users.get(x).getUserId().equals(userId)) {
                    currentUser = users.get(x);
                    break;
                }
            }

            // check if already booked
            ArrayList<TransactionModel> transactions = dataStore.getTransactionsArrayList();
            for (int x = 0; x<transactions.size(); ++x) {
                if (transactions.get(x).getUserId().equals(userId) && transactions.get(x).getKostName().equals(currentKost.getKostName())) {
                    // already booked
                    Toast.makeText(this, getString(R.string.already_booked), Toast.LENGTH_LONG).show();
                    finish();
                    break;
                }
            }

            if (currentUser != null) {
                bookingMessage.setText(getString(R.string.booking_message, currentUser.getUsername(), currentKost.getKostName()));

                // button onclick
                KostModel finalCurrentKost = currentKost;
                button.setOnClickListener(v -> {
                    String bookingId = "BK" + String.format("%03d", kosts.size() + 1);
                    String bookingDate = dateTextField.getText().toString();
                    String kostName = finalCurrentKost.getKostName();
                    String kostFacility = finalCurrentKost.getKostFacility();
                    int kostPrice = finalCurrentKost.getKostPrice();
                    double kostLat = finalCurrentKost.getKostLatitude();
                    double kostLon = finalCurrentKost.getKostLongitude();
                    int kostImage = finalCurrentKost.getKostImage();

                    // verify date
                    if (bookingDate.isEmpty()) {
                        dateTextField.setError(getString(R.string.must_be_filled));
                        dateTextField.requestFocus();
                        return;
                    }

                    // generate new data
                    TransactionModel newTransaction = new TransactionModel(bookingId, userId, kostName, kostFacility, kostPrice, kostLat, kostLon, bookingDate, kostImage);

                    // add to array
                    transactions.add(newTransaction);
                    dataStore.setTransactionsArrayList(transactions);

                    Toast.makeText(this, getString(R.string.book_done), Toast.LENGTH_LONG).show();
                    finish();
                });

            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        toolbarTitle.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
