package com.joemerhej.leagueoflegends.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.models.Region;

import java.util.List;

/**
 * Created by Joe Merhej on 4/17/18.
 */
public class RegionSpinnerAdapter extends ArrayAdapter
{
    private Context mContext;
    private List<Region> mRegions;

    public RegionSpinnerAdapter(Context context, List<Region> regions)
    {
        super(context, R.layout.region_spinner_item, regions);
        this.mContext = context;
        this.mRegions = regions;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View spinnerRow = LayoutInflater.from(mContext).inflate(R.layout.region_spinner_item_collapsed, parent, false);

        ImageView rowFlag = spinnerRow.findViewById(R.id.spinner_region_image);

        rowFlag.setImageResource(mRegions.get(position).getImageResourceId());

        return spinnerRow;
    }

    @Override
    public @NonNull View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View spinnerRow = LayoutInflater.from(mContext).inflate(R.layout.region_spinner_item, parent, false);

        ImageView rowFlag = spinnerRow.findViewById(R.id.spinner_region_image);
        TextView rowRegionName = spinnerRow.findViewById(R.id.spinner_region_name);

        rowFlag.setImageResource(mRegions.get(position).getImageResourceId());
        rowRegionName.setText(mRegions.get(position).getName());

        return spinnerRow;
    }

    public List<Region> getRegions()
    {
        return mRegions;
    }
}
