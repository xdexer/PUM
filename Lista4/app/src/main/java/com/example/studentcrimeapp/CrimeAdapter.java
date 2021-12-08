package com.example.studentcrimeapp;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.ViewHolder> {
    private CrimeLab mData;
    private static CrimeAdapter sCrimeAdapter;

    public static CrimeAdapter get(Context context, CrimeLab data){
        if (sCrimeAdapter == null){
            sCrimeAdapter = new CrimeAdapter(data);
        }
        return  sCrimeAdapter;
    }
    private CrimeAdapter(CrimeLab data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(this.mData.getCrime(position).getmTitle());
        holder.textViewDate.setText(this.mData.getCrime(position).getmDate().toString());
    }

    @Override
    public int getItemCount() {
        return this.mData.getCrimeLabSize();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewName;
        private final TextView textViewDate;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textViewName = view.findViewById(R.id.crimeTabName);
            this.textViewDate = view.findViewById(R.id.crimeTabDate);
        }

        @Override
        public void onClick(View view) {
            Intent newActivity = new Intent(view.getContext(), CrimeActivity.class);
            newActivity.putExtra("crimeID", mData.getCrime(getLayoutPosition()).getmId().toString());
            System.out.println(getLayoutPosition());
            newActivity.putExtra("position", getLayoutPosition());
            view.getContext().startActivity(newActivity);
        }
    }

}
