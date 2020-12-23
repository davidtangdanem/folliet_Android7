package com.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.menadinteractive.folliet2016.R;

import java.util.ArrayList;

/**
 * Created by Loic on 03/08/2017.
 */

public class PdvAdapter extends ArrayAdapter<PdvModel> implements View.OnClickListener{

    private ArrayList<PdvModel> dataSet;
    Context mContext;
    int layout;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtLat;
        TextView txtLng;
        TextView txtDuration;
        TextView txtDistance;
    }

    public PdvAdapter(MapsActivity context, int layout, ArrayList<PdvModel> data) {
        super(context, R.layout.item_list_pdv, data);
        this.layout = layout;
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        PdvModel dataModel=(PdvModel)object;

       /* switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PdvModel pdvModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_pdv, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.nom_pdv);
            viewHolder.txtLat = (TextView) convertView.findViewById(R.id.lat_pdv);
            viewHolder.txtLng = (TextView) convertView.findViewById(R.id.lng_pdv);
            viewHolder.txtDuration = (TextView) convertView.findViewById(R.id.duration_pdv);
            viewHolder.txtDistance = (TextView) convertView.findViewById(R.id.distance_pdv);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtName.setText(pdvModel.getName());
        viewHolder.txtLat.setText(pdvModel.getLat());
        viewHolder.txtLng.setText(pdvModel.getLng());
        viewHolder.txtDuration.setText(pdvModel.getDuration());
        viewHolder.txtDistance.setText(pdvModel.getDistance());

        // Return the completed view to render on screen
        return convertView;
    }
}

