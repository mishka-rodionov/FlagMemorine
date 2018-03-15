package com.example.mishka.flagmemorine.cView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Player;

import java.util.List;

/**
 * Created by mishka on 04/03/18.
 */
public class CRecyclerViewAdapter extends RecyclerView.Adapter<CRecyclerViewAdapter.PlayerViewHolder> {

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        PlayerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardview);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }


    CRecyclerViewAdapter(List<Player> persons){
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(CRecyclerViewAdapter.PlayerViewHolder holder, int position) {

    }

    @Override
    public CRecyclerViewAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    private List<Player> persons;
}
