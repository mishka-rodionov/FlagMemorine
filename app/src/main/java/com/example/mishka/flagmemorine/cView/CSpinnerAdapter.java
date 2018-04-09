package com.example.mishka.flagmemorine.cView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mishka.flagmemorine.R;
import com.example.mishka.flagmemorine.logic.Data;

/**
 * Created by Lab1 on 06.04.2018.
 */

public class CSpinnerAdapter extends ArrayAdapter<String> {
    String[] spinnerTitles;
    int[] spinnerImages;
//    String[] spinnerPopulation;
    Context mContext;
    int pos = -1;
    String username = "unknown";

    public CSpinnerAdapter(@NonNull Context context, String[] titles, int[] images) {
        super(context, R.layout.cspinner_row);
        this.spinnerTitles = titles;
        this.spinnerImages = images;
//        this.spinnerPopulation = population;
        this.mContext = context;
    }

    public int getPosition(){
        return pos;
    }

    public String getUsername(){
        return username;
    }

    private void setUsername(String un){
        username = un;
    }

    private void setPosition(int position){
        pos = position;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return spinnerTitles.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(Data.getLOG_TAG(), "getView position: " + position);
        setPosition(position);
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.cspinner_row, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.ivFlag);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.tvName);
//            mViewHolder.mPopulation = (TextView) convertView.findViewById(R.id.tvPopulation);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mFlag.setImageResource(spinnerImages[position]);
        mViewHolder.mName.setText(spinnerTitles[position]);
        setUsername(mViewHolder.mName.getText().toString());
//        mViewHolder.mPopulation.setText(spinnerPopulation[position]);

        return convertView;
//        return mViewHolder;
    }

    private static class ViewHolder {
        ImageView mFlag;
        TextView mName;

        public TextView getmName() {
            return mName;
        }

        //        TextView mPopulation;
    }
}
