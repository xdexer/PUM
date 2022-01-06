package com.example.studentcrimeapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CrimeAdapter extends RecyclerView.Adapter<CrimeAdapter.ViewHolder> implements Filterable {
    private CrimeLab mData;
    private List<Crime> crimeList;
    private List<Crime> crimeListAll;

    public CrimeAdapter(CrimeLab data) {
        this.mData = data;
        this.crimeListAll = mData.getCrimes();
        this.crimeList = new ArrayList<>();
        crimeList.addAll(crimeListAll);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(crimeList.get(position).getTitle());
        holder.textViewDate.setText(crimeList.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Crime> filteredCrimes = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredCrimes.addAll(crimeListAll);
            } else{
                for (Crime crime: crimeListAll){
                    if (crime.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){

                        filteredCrimes.add(crime);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredCrimes;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            crimeList.clear();
            crimeList.addAll((Collection<? extends Crime>) results.values);
            notifyDataSetChanged();
        }
    };

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
            newActivity.putExtra("crimeID", crimeList.get(getLayoutPosition()).getUU().toString());
            view.getContext().startActivity(newActivity);
        }
    }

}
