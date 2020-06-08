package id.stanley.binus.bluejackkos.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.adapters.BookingTransactionRecyclerViewAdapter;
import id.stanley.binus.bluejackkos.adapters.KostRecyclerViewAdapter;
import id.stanley.binus.bluejackkos.models.TransactionModel;
import id.stanley.binus.bluejackkos.utils.DataStore;
import id.stanley.binus.bluejackkos.utils.TransactionsDB;

public class BookingTransactionsActivity extends AppCompatActivity implements BookingTransactionRecyclerViewAdapter.ItemClickListener {

    private DataStore dataStore = new DataStore(this);
    private BookingTransactionRecyclerViewAdapter adapter;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ArrayList<TransactionModel> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_transactions);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.titleText);

        list = dataStore.getTransactionsArrayList();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.booking_transaction_form));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvKosts);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookingTransactionRecyclerViewAdapter(this, list);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        if (list.size() == 0) {
            Toast.makeText(this, getString(R.string.no_transactions), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.cancel))
                .setMessage(getString(R.string.really_cancel))

                .setPositiveButton(R.string.yes, (dialog, whichButton) -> {
                    for (int x=0; x<list.size(); ++x) {
                        if (x == position) {
                            TransactionsDB transactionsDB = new TransactionsDB(this);
                            transactionsDB.removeTransaction(list.get(x).getBookingId());
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(this, "Booking cancelled", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss())
                .create();
        myQuittingDialogBox.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        toolbarTitle.setText(title);
    }
}
