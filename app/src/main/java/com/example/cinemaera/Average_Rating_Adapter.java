package com.example.cinemaera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class Average_Rating_Adapter extends BaseAdapter {
    Context ctx;
    List<Film.ReviewInfo> RateInfo;
    LayoutInflater inflater;

    public Average_Rating_Adapter(Context ctx, List<Film.ReviewInfo> rateInfo) {
        this.ctx = ctx;
        RateInfo = rateInfo;
    }

    @Override
    public int getCount() {
        return RateInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return RateInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.activity_film_gallery,null);
        }
        TextView AvgRatings = convertView.findViewById(R.id.AvgRatings);
        AvgRatings.setText(RateInfo.get(position).getRatingAvg());
        return convertView;
    }
}
