package com.cs407.badgerbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class BuildingFloorListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, Integer> listDataChild;
    private HashMap<Integer, Boolean> isZoomed = new HashMap<>();

    public BuildingFloorListAdapter(Context context, List<String> listDataHeader,
                                    HashMap<String, Integer> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(headerTitle);

        ImageView groupIndicatorImageView = convertView.findViewById(R.id.groupIndicator);

        listTitleTextView.setText(headerTitle);



        // for the upper/down-arrow to be on the right
        groupIndicatorImageView.setImageResource(isExpanded ? R.drawable.baseline_expand_less_black : R.drawable.baseline_expand_more_black);





        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final int imageResource = (Integer) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.build_floor_list_item, null);
        }

        ImageView expandedListImageView = convertView.findViewById(R.id.expandedListItem);
        expandedListImageView.setImageResource(imageResource);

        // Set the click listener to open the image in full-screen
        expandedListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start FullscreenImageActivity
                Intent intent = new Intent(context, FullscreenImageActivity.class);
                // Pass the image resource ID to the activity
                intent.putExtra("imageResId", imageResource);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
