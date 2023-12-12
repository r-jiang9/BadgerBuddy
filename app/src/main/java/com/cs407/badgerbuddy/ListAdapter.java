package com.cs407.badgerbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<DisplayListModel> {

    public ListAdapter(Context context, ArrayList<DisplayListModel> listElements){
        super(context, R.layout.list_item, listElements);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DisplayListModel element = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView listName = (TextView) convertView.findViewById(R.id.textViewListItem);
        listName.setText(element.toString());

        return super.getView(position, convertView, parent);
    }
}
