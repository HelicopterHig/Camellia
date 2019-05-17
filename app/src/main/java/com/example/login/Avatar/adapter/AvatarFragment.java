package com.example.login.Avatar.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.login.Avatar.Avatar;
import com.example.login.R;

public class AvatarFragment extends Fragment
        implements View.OnClickListener {

    private static final String ARG_ID = "id";

    private int id;
    private OnActionListener actionListener;
    Avatar avatar;

    public AvatarFragment() {

    }

    public static AvatarFragment newInstance(int id) {
        AvatarFragment fragment = new AvatarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_avatar, container, false);

        ImageButton imageButton = (ImageButton) v.findViewById(R.id.imageButton4);
        imageButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                onAction();
                break;
        }
    }

    public void onAction() {
        if (actionListener != null) {
            actionListener.onAction(this.id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionListener) {
            actionListener = (OnActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        actionListener = null;
    }

    public interface OnActionListener {
        void onAction(int id);
    }
}