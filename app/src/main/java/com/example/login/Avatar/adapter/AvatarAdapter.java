package com.example.login.Avatar.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageButton;

import com.example.login.R;

public class AvatarAdapter extends FragmentStatePagerAdapter {

    public static final int ID1 = 1;
    public static final int ID2 = 2;
    public static final int ID3 = 3;
    public static final int ID4 = 4;
    public static final int ID5 = 5;
    public static final int ID6 = 6;
    public static final int ID7 = 7;
    public static final int ID8 = 8;
    public static final int ID9 = 9;

    private Context context;
    ImageButton imageButton;

    public AvatarAdapter(Context context, FragmentManager fm) {
        super(fm);

        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case ID1:

                break;
            case ID2:

                break;
            case ID3:

                break;
            case ID4:

                break;
            case ID5:

                break;
            case ID6:

                break;
            case ID7:

                break;
            case ID8:

                break;
            case ID9:

                break;
        }
        return AvatarFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 9;
    }
}