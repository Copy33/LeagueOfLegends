package com.joemerhej.leagueoflegends.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.models.Profile;
import com.joemerhej.leagueoflegends.models.WidgetListItem;

import java.util.List;

/**
 * Created by Joe Merhej on 4/19/18.
 */
public class WidgetListAdapter extends ArrayAdapter
{
    private Context mContext;
    private List<WidgetListItem> mItems;

    public WidgetListAdapter(Context context, List<WidgetListItem> items)
    {
        super(context, R.layout.row_summary, items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        View rowView = LayoutInflater.from(mContext).inflate(R.layout.row_summary, parent, false);

        ImageView imageView = rowView.findViewById(R.id.mainactivity_row_image);
        TextView txtTitle = rowView.findViewById(R.id.mainactivity_row_name);

        txtTitle.setText(mItems.get(position).getSummonerName());

        imageView.setImageResource(mItems.get(position).getRankImageId());
        return rowView;
    }
}
