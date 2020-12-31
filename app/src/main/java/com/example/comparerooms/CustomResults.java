package com.example.comparerooms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.comparerooms.R;
import com.example.comparerooms.CustomResults;

import java.util.ArrayList;

public class CustomResults extends ArrayAdapter {
    private String[] result;
    private Integer[] imageid;
    private Activity context;

    public CustomResults(Activity context, String[] result, Integer[] imageid) {
        super(context, R.layout.row_item, result);
        this.context = context;
        this.result = result;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.row_item, null, true);
        TextView textViewResult = (TextView) row.findViewById(R.id.textViewResults);
        ImageView imageRoom = (ImageView) row.findViewById(R.id.imageViewRoom);

        textViewResult.setText(result[position]);
        imageRoom.setImageResource(imageid[position]);
        return  row;
    }
}


