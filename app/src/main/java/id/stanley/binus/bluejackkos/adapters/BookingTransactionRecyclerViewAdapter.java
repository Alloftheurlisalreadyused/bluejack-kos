package id.stanley.binus.bluejackkos.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import id.stanley.binus.bluejackkos.R;
import id.stanley.binus.bluejackkos.models.TransactionModel;

public class BookingTransactionRecyclerViewAdapter extends RecyclerView.Adapter<BookingTransactionRecyclerViewAdapter.ViewHolder> {

    private ArrayList<TransactionModel> mKostData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public BookingTransactionRecyclerViewAdapter(Context context, ArrayList<TransactionModel> kostData) {
        this.mInflater = LayoutInflater.from(context);
        this.mKostData = kostData;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_kosts, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String kostName = mKostData.get(position).getKostName();
        int kostPrice = mKostData.get(position).getKostPrice();
        String kostFacilities = mKostData.get(position).getBookingDate();
        Bitmap kostImage = mKostData.get(position).getKostImage();

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        holder.kostName.setText(kostName);
        holder.kostFacility.setText(kostFacilities);
        holder.kostPrice.setText(kursIndonesia.format(kostPrice));
        holder.kostImage.setImageBitmap(kostImage);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mKostData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView kostImage;
        TextView kostName;
        TextView kostPrice;
        TextView kostFacility;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            kostImage = itemView.findViewById(R.id.kostImage);
            kostName = itemView.findViewById(R.id.kostName);
            kostPrice = itemView.findViewById(R.id.kostPrice);
            kostFacility = itemView.findViewById(R.id.kostFacilities);

            cardView = itemView.findViewById(R.id.kostCard);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getKostId(int id) {
        return mKostData.get(id).getBookingId();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}