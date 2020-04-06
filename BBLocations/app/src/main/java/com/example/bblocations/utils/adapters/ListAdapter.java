package com.example.bblocations.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.bblocations.R;
import com.example.bblocations.models.City;
import com.example.bblocations.utils.Utils;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    // Variable
    private static final long BASE_ID = 0x01011;
    private final Context context;
    private final List<City> cities;
    private List<City> filteredCities;

    public ListAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        if(cities != null && cities.size() > 0){
            return cities.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(!Utils.isNull(cities) && position >= 0 || position < cities.size()){
            return cities.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }

    static class ViewHolder {
        final TextView title;
        final TextView subtitle;
        public ViewHolder(View mLayout) {
            title = mLayout.findViewById(R.id.title);
            subtitle = mLayout.findViewById(R.id.subtitle);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row,
                    parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        String cityName = ((City)getItem(position)).getName();
        String country = ((City)getItem(position)).getCountry();
        Float cityLat = ((City)getItem(position)).getCoord().getLat();
        Float cityLon = ((City)getItem(position)).getCoord().getLon();
        if (!Utils.isNull(cityName) && !Utils.isNull(country) && !Utils.isNull(cityLat) && !Utils.isNull(cityLon)) {
            final String cityTitle = cityName + " - " + country;
            final String cityCoord = cityLat + " - " + cityLon;
            mViewHolder.title.setText(cityTitle);
            mViewHolder.subtitle.setText(cityCoord);
        }

        return convertView;
    }
}
