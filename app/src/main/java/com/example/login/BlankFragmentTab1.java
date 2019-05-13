/*package com.example.login;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.AlertDialog;
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
/*public class BlankFragmentTab1 extends Fragment {

    protected String name_note;
    View vie;
    ImageButton floatButton;
    EditText editText;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;


/*    public static BlankFragmentTab1 newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BlankFragmentTab1 fragment = new BlankFragmentTab1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }


    }*/


 /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_blank_fragment_tab1, container, false);

        floatButton = (ImageButton) rootView.findViewById(R.id.imageButtonTaskTb1);
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
                    @Override
                    public void onClick(View view) {


                        name_note = String.valueOf(editText.getText().toString());

 /*                      try{
                        new CreateGroup().execute();
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/

       /*                     dialog.dismiss();
                            openEditNoteActivity();

//                        insertItem(position, name_group);
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


    /*private void configureImageButton() {
        floatButton = (ImageButton) v.findViewById(R.id.imageButtonTask);
        floatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.create_note, null);
                editText = (EditText) mView.findViewById(R.id.NameNote);
                Button mLogin = (Button) mView.findViewById(R.id.NoteCreate);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int position = 0;

                        name_note = String.valueOf(editText.getText().toString());

    *//*                    try{
                            new CreateGroup().execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }*//*

                        dialog.dismiss();

//                        insertItem(position, name_group);
                    }
                });
            }

        });

        }*/
    //}
   /* floatButton = (ImageButton) findViewById(R.id.imageButton);
        floatButton.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(TskActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.create_note, null);
            editText = (EditText) mView.findViewById(R.id.NameNote);
            Button mLogin = (Button) mView.findViewById(R.id.NoteCreate);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = 0;

                    name_note = String.valueOf(editText.getText().toString());

  *//*                      try{
                            new CreateGroup().execute();
                        }catch (Exception e){
                            e.printStackTrace();
                        }*//*

                    dialog.dismiss();

//                        insertItem(position, name_group);
                }
            });
        }

        });*/




