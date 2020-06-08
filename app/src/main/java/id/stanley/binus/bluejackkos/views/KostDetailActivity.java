package id.stanley.binus.bluejackkos.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

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
        toolbarTitle = findViewById(R.id.titleText);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = getIntent();
        
        int kostId = intent.getIntExtra("kostId", 0);
        userId = intent.getStringExtra("userId");
        
        if (kostId == 0) finish();

        ArrayList<KostModel> kosts = dataStore.getKostArrayList();
        KostModel currentKost = null;
        
        for (int x = 0; x<kosts.size(); ++x) {
            if (kosts.get(x).getKostId() == kostId) {
                currentKost = kosts.get(x);
                Log.d("KOST2", String.valueOf(kostId));
                break;
            }
        }
        
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

            kostImage.setImageResource(currentKost.getKostImage());
            kostName.setText(currentKost.getKostName());
            kostFacility.setText(currentKost.getKostFacility());
            kostPrice.setText(kursIndonesia.format(currentKost.getKostPrice()));
            kostDescription.setText(currentKost.getKostDescription());
            kostLat.setText("Latitude: " + currentKost.getKostLatitude());
            kostLon.setText("Longitude: " + currentKost.getKostLongitude());

            KostModel finalCurrentKost = currentKost;
            bookButton.setOnClickListener(view -> {
                Intent bookIntent = new Intent(KostDetailActivity.this, BookKostActivity.class);
                bookIntent.putExtra("kostId", finalCurrentKost.getKostId());
                bookIntent.putExtra("userId", userId);

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
