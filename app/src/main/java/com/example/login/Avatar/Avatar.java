package com.example.login.Avatar;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.login.R;
import com.example.login.Avatar.adapter.AvatarFragment;
import com.example.login.Avatar.adapter.AvatarAdapter;

import me.relex.circleindicator.CircleIndicator;

public class Avatar extends AppCompatActivity
        implements AvatarFragment.OnActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new AvatarAdapter(this, getSupportFragmentManager()));
        pager.setPageMargin((int) getResources().getDimension(R.dimen.card_padding) );
        pager.setOffscreenPageLimit(1);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

    }

    @Override
    public void onAction(int id) {
        /*switch (id) {
            case AvatarAdapter.ID_DEFAULT:
                DefaultDialogsActivity.open(this);
                break;
            case AvatarAdapter.ID_STYLED:
                StyledDialogsActivity.open(this);
                break;
            case AvatarAdapter.ID_CUSTOM_LAYOUT:
                CustomLayoutDialogsActivity.open(this);
                break;
            case AvatarAdapter.ID_CUSTOM_VIEW_HOLDER:
                CustomHolderDialogsActivity.open(this);
                break;
            case AvatarAdapter.ID_CUSTOM_CONTENT:
                CustomMediaMessagesActivity.open(this);
                break;
        }*/
    }
}
