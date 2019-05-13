/*package com.example.login;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
/*public class BlankFragmentTab3 extends Fragment {


    private int mPage;
    public static final String ARG_PAGE = "ARG_PAGE";
    View vie;
    ImageButton floatButton;
    EditText editText;

    /*public BlankFragmentTab3 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BlankFragmentTab3 fragment = new BlankFragmentTab3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }*/
   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blank_fragment_tab3, container, false);
        floatButton = (ImageButton) rootView.findViewById(R.id.imageButtonTaskTb3);
        floatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.create_note, null);
                editText = (EditText) mView.findViewById(R.id.NameNote);
                Button mLogin = (Button) mView.findViewById(R.id.NoteCreate);
                Button nLogin = (Button) mView.findViewById(R.id.CancelNote);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        int position = 0;
                        dialog.dismiss();
                        openEditNoteActivity();

                    }
                });

                nLogin.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });



            }

        });

        return rootView;
    }
    public void openEditNoteActivity(){
        Intent intent  = new Intent(getActivity(), NoteEditActivity.class);
        startActivity(intent);
    }


}*/
