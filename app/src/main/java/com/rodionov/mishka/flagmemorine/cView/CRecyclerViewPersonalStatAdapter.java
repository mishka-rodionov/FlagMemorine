package com.rodionov.mishka.flagmemorine.cView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodionov.mishka.flagmemorine.R;
import com.rodionov.mishka.flagmemorine.logic.CountryInfo;
import com.rodionov.mishka.flagmemorine.logic.CountryList;
import com.rodionov.mishka.flagmemorine.logic.Data;
import com.rodionov.mishka.flagmemorine.logic.PersonalStat;
import com.rodionov.mishka.flagmemorine.service.SqLiteTableManager;

import java.util.List;

/**
 * Created by Lab on 19.07.2018.
 */

public class CRecyclerViewPersonalStatAdapter extends RecyclerView.Adapter<CRecyclerViewPersonalStatAdapter.PersonalStatHolder>{
    public static class PersonalStatHolder extends RecyclerView.ViewHolder {
        ImageView localOriginImage;
        ImageView enemyOriginImage;
        TextView localNamePersonalStat;
        TextView enemyNamePersonalStat;
        TextView scorePersonalStat;
        TextView enemyScorePersonalStat;
        PersonalStatHolder(View itemView) {
            super(itemView);
            localOriginImage = (ImageView) itemView.findViewById(R.id.localOriginImage);
            enemyOriginImage = (ImageView) itemView.findViewById(R.id.enemyOriginImage);
            localNamePersonalStat = (TextView) itemView.findViewById(R.id.localNamePersonalStat);
            enemyNamePersonalStat = (TextView) itemView.findViewById(R.id.enemyNamePersonalStat);
            scorePersonalStat = (TextView) itemView.findViewById(R.id.scorePersonalStat);
            enemyScorePersonalStat = (TextView) itemView.findViewById(R.id.enemyScorePersonalStat);
        }
    }


    public CRecyclerViewPersonalStatAdapter(List<PersonalStat> personalStatList){
        this.personalStatList = personalStatList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(CRecyclerViewPersonalStatAdapter.PersonalStatHolder holder, int position) {
        String localOrigin = personalStatList.get(position).getLocalOrigin();
        Log.i(Data.getLOG_TAG(), "onBindViewHolder: localOrigin = " + localOrigin);
        holder.localOriginImage.setImageResource(CountryList.getCountry(localOrigin));
        holder.enemyOriginImage.setImageResource(CountryList.getCountry(personalStatList.get(position).getEnemyOrigin()));
        holder.localNamePersonalStat.setText(personalStatList.get(position).getLocalName());
        holder.enemyNamePersonalStat.setText(personalStatList.get(position).getEnemyName());
        holder.scorePersonalStat.setText(Integer.toString(personalStatList.get(position).getScore()));
        holder.enemyScorePersonalStat.setText(Integer.toString(personalStatList.get(position).getEnemyScore()));

    }

    @Override
    public CRecyclerViewPersonalStatAdapter.PersonalStatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_stat_cardview, parent, false);
        CRecyclerViewPersonalStatAdapter.PersonalStatHolder cvh = new CRecyclerViewPersonalStatAdapter.PersonalStatHolder(v);
        return cvh;
    }

    @Override
    public int getItemCount() {
        return personalStatList.size();
    }

    private List<PersonalStat> personalStatList;
    private SqLiteTableManager sqLiteTableManager;

}
