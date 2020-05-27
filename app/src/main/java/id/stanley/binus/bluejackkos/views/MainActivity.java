package id.stanley.binus.bluejackkos.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.nfc.Tag;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.adapters.KostRecyclerViewAdapter;
import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.utils.DataStore;

public class MainActivity extends AppCompatActivity implements KostRecyclerViewAdapter.ItemClickListener{
    
    private DataStore dataStore = DataStore.getInstance();
    private KostRecyclerViewAdapter adapter;
    private Toolbar toolbar;
    private String userId;
    private RequestQueue mQueue;
    List<KostModel> kostModels;
    String url = "https://raw.githubusercontent.com/dnzrx/SLC-REPO/master/MOBI6006/E202-MOBI6006-KR01-00.json";
    private Object MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        mQueue = Volley.newRequestQueue(this);

        if (userId == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        jsonParse();

        ArrayList<KostModel> kostArrayList = dataStore.getKostArrayList();

        // remove data
        kostArrayList.clear();

        dataStore.setKostArrayList(kostArrayList);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvKosts);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new KostRecyclerViewAdapter(this, dataStore.getKostArrayList());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Log.d(null, "jsonParse()"+ kostModels);
    }

    private void jsonParse(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        KostModel kostModel = new KostModel();
                        kostModel.setKostId(jsonObject.getInt("id"));
                        kostModel.setKostName(jsonObject.getString("name"));
                        kostModel.setKostPrice(jsonObject.getInt("price"));
                        kostModel.setKostFacility(jsonObject.getString("facilities"));
                        kostModel.setKostImage(jsonObject.getString("image"));
                        kostModel.setKostAddress(jsonObject.getString("address"));
                        kostModel.setKostLatitude(jsonObject.getDouble("LAT"));
                        kostModel.setKostLongitude(jsonObject.getDouble("LNG"));

                        kostModels.add(kostModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
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
