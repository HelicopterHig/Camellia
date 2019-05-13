/*package com.example.login;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
/*public class BlankFragmentTab2 extends Fragment {

    private int mPage;
    public static final String ARG_PAGE = "ARG_PAGE";
    View vie;
    ImageButton floatButton;
    EditText editText;

/*    public BlankFragmentTab2 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        BlankFragmentTab2 fragment = new BlankFragmentTab2();
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
        View rootView = inflater.inflate(R.layout.fragment_blank_fragment_tab2, container, false);
        floatButton = (ImageButton) rootView.findViewById(R.id.imageButtonTaskTb2);
        floatButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                final View mView = getLayoutInflater().inflate(R.layout.add_user, null);
                editText = (EditText) mView.findViewById(R.id.AddIdUser);
                Button mLogin = (Button) mView.findViewById(R.id.AddUser);
                Button nLogin = (Button) mView.findViewById(R.id.CancelAddUser);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),
                                R.string.add_user_greetings,
                                Toast.LENGTH_SHORT).show();
                    }
                });
                nLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });


            }

        });
        return rootView;
    }

//}*/
