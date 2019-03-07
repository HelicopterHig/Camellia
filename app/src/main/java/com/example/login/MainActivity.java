package com.example.login;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {




    @Override

   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                view.startAnimation(animAlpha);
            }
        });

       // int i=0;
       Button create_accaunt = (Button)findViewById(R.id.button1);
      //  int i = 1;
      // while ( i == 1 ){
         create_accaunt.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                setContentView(R.layout.activity_main1);

                Button back_button1=(Button)findViewById(R.id.back_icon);
                back_button1.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                    }
                });

            }
        });
       //}
    }

}
    /*final Animation Animalpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
    Button button = (Button)findViewById(R.id.alpha);
        button.setOnClickListener(new Button.OnClickListener(){
@Override
public void onClick(View view){
        view.startAnimation(animAlpha);
        }
        }*/