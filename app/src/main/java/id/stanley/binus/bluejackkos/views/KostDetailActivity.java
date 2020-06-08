package id.stanley.binus.bluejackkos.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.models.KostModel;
import id.stanley.binus.bluejackkos.utils.DataStore;

public class KostDetailActivity extends AppCompatActivity {
    
    private DataStore dataStore = new DataStore(this);
    private ImageView kostImage;
    private TextView kostName;
    private TextView kostFacility;
    private TextView kostPrice;
    private TextView kostDescription;
    private TextView kostLat;
    private TextView kostLon;
    private MaterialButton bookButton;
    private MaterialButton mapButton;
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_detail);

        kostImage = findViewById(R.id.kostImage);
        kostName = findViewById(R.id.kostName);
        kostFacility = findViewById(R.id.kostFacilities);
        kostPrice = findViewById(R.id.kostPrice);
        kostDescription = findViewById(R.id.kostDescription);
        kostLat = findViewById(R.id.kostLatitude);
        kostLon = findViewById(R.id.kostLongitude);
        bookButton = findViewById(R.id.buttonBook);
        mapButton = findViewById(R.id.mapButton);
        toolbarTitle = findViewById(R.id.titleText);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KostDetailActivity.this,MapsActivity.class));
            }
        });
        
        Intent intent = getIntent();
        
        int kostId = intent.getIntExtra("kostId", 0);
        userId = intent.getStringExtra("userId");
        
        if (kostId == 0) finish();


        String selectedKost = intent.getStringExtra("selectedKost");
        Gson gson = new Gson();
        KostModel currentKost = gson.fromJson(selectedKost, KostModel.class);
        
        if (currentKost == null) finish();
        else {
            // set data

            // format currency
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');
            kursIndonesia.setDecimalFormatSymbols(formatRp);

            kostImage.setImageBitmap(currentKost.getKostImage());
            kostName.setText(currentKost.getKostName());
            kostFacility.setText(currentKost.getKostFacility());
            kostPrice.setText(kursIndonesia.format(currentKost.getKostPrice()));
            kostDescription.setText(currentKost.getKostDescription());
            kostLat.setText("Latitude: " + currentKost.getKostLatitude());
            kostLon.setText("Longitude: " + currentKost.getKostLongitude());

            KostModel finalCurrentKost = currentKost;
            bookButton.setOnClickListener(view -> {
                String myJson = gson.toJson(finalCurrentKost);

                Intent bookIntent = new Intent(KostDetailActivity.this, BookKostActivity.class);
                bookIntent.putExtra("kostId", finalCurrentKost.getKostId());
                bookIntent.putExtra("userId", userId);
                bookIntent.putExtra("selectedKost", myJson);

                startActivity(bookIntent);
            });
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
