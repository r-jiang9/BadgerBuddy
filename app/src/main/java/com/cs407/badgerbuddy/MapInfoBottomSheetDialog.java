package com.cs407.badgerbuddy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class MapInfoBottomSheetDialog extends BottomSheetDialogFragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_ADDRESS = "arg_address";

    public static MapInfoBottomSheetDialog newInstance(String title, String address) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_ADDRESS, address);
        MapInfoBottomSheetDialog fragment = new MapInfoBottomSheetDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_bottom_sheet_dialog, null);
        dialog.setContentView(view);

        // set title and address
        TextView titleTextView = view.findViewById(R.id.tv_title);
        TextView addressTextView = view.findViewById(R.id.tv_address);

        if (getArguments() != null) {
            String title = getArguments().getString(ARG_TITLE, "");
            String address = getArguments().getString(ARG_ADDRESS, "");

            titleTextView.setText(title);
            addressTextView.setText(address);
        }

        ImageButton imageButton = view.findViewById(R.id.btn_detail);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BuildingFloorActivity.class);
                intent.putExtra("title", titleTextView.getText().toString());
                intent.putExtra("address", addressTextView.getText().toString());
                startActivity(intent);
                dismiss();
            }

        });

        return dialog;
    }
}