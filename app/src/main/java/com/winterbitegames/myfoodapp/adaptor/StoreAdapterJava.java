package com.winterbitegames.myfoodapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.winterbitegames.myfoodapp.R;
import com.winterbitegames.myfoodapp.custom_view.ElegantNumberButton;
import com.winterbitegames.myfoodapp.data.Store;

import java.util.ArrayList;

public class StoreAdapterJava extends RecyclerView.Adapter<StoreAdapterJava.StoreViewHolder> {

    ArrayList<Store> arrayList;

    updateOrderItem updateOrderItem;

    private boolean showFullList = true;
    private Context mContext;

    public StoreAdapterJava(updateOrderItem updateOrderItem, Context context) {
        this.updateOrderItem = updateOrderItem;
        this.mContext = context;
    }


    public void showTwoItems(boolean value) {
        showFullList = value;
        notifyDataSetChanged();
    }

    public void setArrayList(ArrayList<Store> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public interface updateOrderItem {

        void updateItemAtPos(Store store, int count);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stores_layout, parent, false);

        return new StoreViewHolder(view);
    }

    private static final String TAG = "SoreAdapterJava";

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = arrayList.get(position);
        holder.foodName.setText(store.getFoodName());
        holder.foodDescription.setText(store.getFoodDescription());
        holder.count.setNumber(String.valueOf(store.getCount()));

        holder.count.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                updateOrderItem.updateItemAtPos(store, newValue);
//                Log.d(TAG, "onValueChange: " + oldValue + " new value" + newValue);
            }
        });
        holder.dish_price.setText(mContext.getResources().getString(R.string.cart_total, store.getPrice()));
    }

    @Override
    public int getItemCount() {

        if (arrayList == null) {
            return 0;
        }
        if (showFullList) {
            return arrayList.size();
        } else {
            return Math.min(arrayList.size(), 2);
        }
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView foodName;
        TextView foodDescription, dish_price;
        ElegantNumberButton count;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.dish_name);
            foodDescription = itemView.findViewById(R.id.dish_desc);
            count = itemView.findViewById(R.id.add_button);
            dish_price = itemView.findViewById(R.id.dish_price1);
        }
    }

}
