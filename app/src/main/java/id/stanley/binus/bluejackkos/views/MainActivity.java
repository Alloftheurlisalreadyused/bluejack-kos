package id.stanley.binus.bluejackkos.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.adapters.KostRecyclerViewAdapter;
import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.utils.DataStore;

public class MainActivity extends AppCompatActivity implements KostRecyclerViewAdapter.ItemClickListener{

    private DataStore dataStore = DataStore.getInstance();
    private KostRecyclerViewAdapter adapter;
    private Toolbar toolbar;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        if (userId == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        ArrayList<KostModel> kostArrayList = dataStore.getKostArrayList();

        // remove data
        kostArrayList.clear();

        // insert data
        kostArrayList.add(new KostModel(
                1,
                "Maharaja",
                "AC, WiFi, Bathroom",
                1450000,
                "The best boarding",
                -6.2000809,
                106.7833355,
                R.drawable.kost2)
        );

        kostArrayList.add(new KostModel(
                2,
                "Haji Indra",
                "AC, WiFi",
                1900000,
                "The cheapest boarding",
                -6.2261741,
                106.9078293,
                R.drawable.kost3)
        );

        dataStore.setKostArrayList(kostArrayList);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvKosts);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new KostRecyclerViewAdapter(this, dataStore.getKostArrayList());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, KostDetailActivity.class)
                .putExtra("kostId", dataStore.getKostArrayList().get(position).getKostId())
                .putExtra("userId", userId);

        Log.d("KOST1", String.valueOf(dataStore.getKostArrayList().get(position).getKostId()));

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            case R.id.bookingTrans:
                startActivity(new Intent(MainActivity.this, BookingTransactionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
