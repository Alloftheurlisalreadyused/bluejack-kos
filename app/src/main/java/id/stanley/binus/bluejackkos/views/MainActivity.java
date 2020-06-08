package id.stanley.binus.bluejackkos.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String API_URL = "https://raw.githubusercontent.com/dnzrx/SLC-REPO/master/MOBI6006/E202-MOBI6006-KR01-00.json";

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

        // fetch data from API
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                response -> {
                    Log.d("MainActivity", response);

                    ArrayList<KostModel> kostArrayList = dataStore.getKostArrayList();

                    // remove data
                    kostArrayList.clear();

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int x = 0; x < jsonArray.length(); ++x) {
                            JSONObject kostObject = jsonArray.getJSONObject(x);

                            Glide.with(this)
                                    .asBitmap()
                                    .load(kostObject.getString("image"))
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            try {
                                                KostModel kost = new KostModel(
                                                        kostObject.getInt("id"),
                                                        kostObject.getString("name"),
                                                        kostObject.getString("facilities"),
                                                        kostObject.getInt("price"),
                                                        kostObject.getString("address"),
                                                        kostObject.getDouble("LNG"),
                                                        kostObject.getDouble("LAT"),
                                                        resource);

                                                kostArrayList.add(kost);

                                                adapter.notifyDataSetChanged();

                                            } catch (JSONException e) {
                                                Log.e("MainActivity", e.getMessage());
                                            }
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {

                                        }
                                    });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // set ArrayList to RecyclerView
                    dataStore.setKostArrayList(kostArrayList);

                }, error -> {
                    Log.e("MainActivity", "Error " + error);

                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        // insert data
//        kostArrayList.add(new KostModel(
//                1,
//                "Maharaja",
//                "AC, WiFi, Bathroom",
//                1450000,
//                "The best boarding",
//                -6.2000809,
//                106.7833355,
//                R.drawable.kost2)
//        );
//
//        kostArrayList.add(new KostModel(
//                2,
//                "Haji Indra",
//                "AC, WiFi",
//                1900000,
//                "The cheapest boarding",
//                -6.2261741,
//                106.9078293,
//                R.drawable.kost3)
//        );

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
