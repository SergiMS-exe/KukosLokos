package com.example.kukoslokos.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kukoslokos.R;

import java.util.Random;

public class RuletaFragment extends AppCompatActivity implements Animation.AnimationListener{

    boolean blnButtonRotation = true;
    int intNumber = 11;
    long lngDregrees = 0;
    SharedPreferences sharedPreferences;

    ImageView f_Ruleta, b_Ruleta;
    Button b_start;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        getWindow().addFlags(1024);
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ruleta);

        b_start = (Button)findViewById(R.id.buttonGirar);

        b_Ruleta = (ImageView) findViewById(R.id.BaseRuleta);
        f_Ruleta = (ImageView) findViewById(R.id.FlechaRuleta);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.intNumber = this.sharedPreferences.getInt("INT_NUMBER", 11);
        b_Ruleta.setImageDrawable(getResources().getDrawable(R.drawable.ic_base_ruleta));
    }
    private void girarRuleta(View view){

        if(this.blnButtonRotation){
            int run = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDregrees,
                    (float) (this.lngDregrees + ((long)run)),
                    1, 0.5f, 1, 0.5f);
            this.lngDregrees = (this.lngDregrees + ((long)run)) % 360;
            rotateAnimation.setDuration((long)run);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setAnimationListener(this);
            b_Ruleta.startAnimation(rotateAnimation);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        this.blnButtonRotation = false;
        b_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast toast = Toast.makeText(this, ""+ String.valueOf((int)(((double)this.intNumber)
                - Math.floor(((double)this.lngDregrees)/(360.0d/((double)this.intNumber))))) + " ", 0);
        toast.setGravity(49, 0, 0);
        toast.show();
        this.blnButtonRotation = true;
        b_start.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
