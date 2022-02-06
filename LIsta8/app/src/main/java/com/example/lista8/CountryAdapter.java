package com.example.lista8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private List<Country> countries;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewCountry.setText(countries.get(position).getCountry());
        holder.textViewCapital.setText(countries.get(position).getCapital());
    }

    @Override
    public int getItemCount() {
        if(countries != null)
            return countries.size();
        else
            return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAllCountries(List<Country> list) {
        this.countries = list;
        notifyDataSetChanged();
    }
    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewCountry;
        private final TextView textViewCapital;

        public ViewHolder(View view) {
            super(view);
            this.textViewCountry = view.findViewById(R.id.countryTabName);
            this.textViewCapital = view.findViewById(R.id.countryTabCapital);
        }

        public TextView getTextViewCountry() {
            return textViewCountry;
        }

        public TextView getTextViewCapital() {
            return textViewCapital;
        }
    }
}
