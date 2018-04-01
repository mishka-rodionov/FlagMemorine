package com.example.mishka.flagmemorine.cView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.CountryList;
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
        TextView date;
        ImageView personPhoto;
        PlayerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardview);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            date = (TextView)itemView.findViewById(R.id.date);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }


    public CRecyclerViewAdapter(List<Player> players){
        this.players = players;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(CRecyclerViewAdapter.PlayerViewHolder holder, int position) {
        holder.personName.setText(players.get(position).getName());
        holder.personAge.setText(Integer.toString(players.get(position).getTotalScore()));
        holder.personPhoto.setImageResource(CountryList.getCountry(players.get(position).getCountry()));
        holder.date.setText(players.get(position).getName());

    }

    @Override
    public CRecyclerViewAdapter.PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        PlayerViewHolder pvh = new PlayerViewHolder(v);
        return pvh;
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    private List<Player> players;
}
