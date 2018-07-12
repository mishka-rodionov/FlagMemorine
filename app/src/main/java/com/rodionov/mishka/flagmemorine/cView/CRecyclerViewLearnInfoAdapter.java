package com.rodionov.mishka.flagmemorine.cView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.logic.CountryInfo;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Player;

import java.util.List;

/**
 * Created by Lab on 12.07.2018.
 */

public class CRecyclerViewLearnInfoAdapter extends RecyclerView.Adapter<CRecyclerViewLearnInfoAdapter.CountryViewHolder>{

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView learnCountryImage;
        TextView countryName;
        TextView capital;
        TextView population;
        TextView square;
        CountryViewHolder(View itemView) {
            super(itemView);
            learnCountryImage = (ImageView) itemView.findViewById(R.id.learnCountryImage);
            countryName = (TextView) itemView.findViewById(R.id.countryName);
            capital = (TextView) itemView.findViewById(R.id.capital);
            population = (TextView) itemView.findViewById(R.id.population);
            square = (TextView) itemView.findViewById(R.id.square);
        }
    }


    public CRecyclerViewLearnInfoAdapter(List<CountryInfo> countries){
        this.countries = countries;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(CRecyclerViewLearnInfoAdapter.CountryViewHolder holder, int position) {
        holder.learnCountryImage.setImageResource(CountryList.getCountry(countries.get(position).getCountryName()));
        holder.countryName.setText(countries.get(position).getCountryName());
        holder.capital.setText(countries.get(position).getCapital());
        holder.population.setText(countries.get(position).getPopulation());
        holder.square.setText(countries.get(position).getSquare());

    }

    @Override
    public CRecyclerViewLearnInfoAdapter.CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.learn_item_cardview, parent, false);
        CRecyclerViewLearnInfoAdapter.CountryViewHolder cvh = new CRecyclerViewLearnInfoAdapter.CountryViewHolder(v);
        return cvh;
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    private List<CountryInfo> countries;
}
