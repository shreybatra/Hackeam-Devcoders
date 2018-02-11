package com.varshney.hackeamdevcoders_partners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yash on 11/2/18.
 */

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.medicineViewHolder>{
    Context context;
    ArrayList<RequestPOJO> pojoArrayList;
    public static final String TAG = "Adapter";
    
    public MedicineListAdapter(Context context, ArrayList<RequestPOJO> pojoArrayList) {
        this.context = context;
        this.pojoArrayList = pojoArrayList;
    }

    public class medicineViewHolder extends RecyclerView.ViewHolder{

        TextView tvMedicineName;
        TextView tvQty;

        public medicineViewHolder(View itemView) {
            super(itemView);
            tvMedicineName = itemView.findViewById(R.id.tvMedicineName);
            tvQty = itemView.findViewById(R.id.tvQty);

        }
    }

    @Override
    public medicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.medicine_list_item,parent,false);
        return new medicineViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(medicineViewHolder holder, int position) {
        RequestPOJO rPojo = pojoArrayList.get(position);


        String med= "hello";

        holder.tvMedicineName.setText(med);
        Log.d(TAG, "onBindViewHolder: "+rPojo.getMedicines().toString());

        holder.tvQty.setText(rPojo.getQuantities().toString());
        

    }

    @Override
    public int getItemCount() {
        return pojoArrayList.size();
    }


}
