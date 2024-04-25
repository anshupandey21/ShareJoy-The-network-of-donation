package comman1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sj_demo.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    LinearLayout dotsLayout;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Button btnSkip;
    Animation btnAnim;
    TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        //to check if this activity is opened before or not
        if(restorePrefData()){
            Intent mainActivity=new Intent(getApplicationContext(), Login.class);
            startActivity(mainActivity);
            finish();
        }




        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnSkip = findViewById(R.id.button4);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
        dotsLayout=findViewById(R.id.linearLayout);
        //fill list screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Doorstep pickup", "Volunteer comes at your location to pickup the item/donation", R.drawable.obs11));
        mList.add(new ScreenItem("Save Food", "Donating the remaining food to the needy is equal to saving life of many", R.drawable.obs22));
        mList.add(new ScreenItem("Spread Love", "After donating to the needy ,share your feelings to motivate many people", R.drawable.obs33));


        //setup viewpager
        screenPager = findViewById(R.id.ScreenViewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);
        addDots(0);
        screenPager.addOnPageChangeListener(changeListener);


        //next button click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size() - 1) {
                    //when we reach to the last screen
                    //TODO:show the GETSTARTED Button and hide the indicator and the next button
                    loadLastScreen();
                }
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent loginScreen=new Intent(getApplicationContext(), Login.class);
                startActivity(loginScreen);
                savePrefsData();
                finish();
            }
        });
        btnGetStarted.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent loginScreen=new Intent(getApplicationContext(), Login.class);
                startActivity(loginScreen);
                savePrefsData();
                finish();


            }
        });
    }
    private void addDots(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[position].setTextColor(Color.parseColor("#975DE5"));
        }
    }
    ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {




        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            for(int i=0;i<dots.length-1;i++) {


                if (position == dots.length - 1) {
                    loadLastScreen();
                }
            }
        }


        @Override
        public void onPageSelected(int position) {


            addDots(position);
        }


        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };


    private boolean restorePrefData() {


        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isOnBoardActivityOpenedBefore=pref.getBoolean("isOnBoardOpened",false);
        return isOnBoardActivityOpenedBefore;


    }


    private void savePrefsData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isOnBoardOpened",true);
        editor.commit();
    }
//get started button click listener




    //show the GETSTARTED Button and hide the indicator and the next button
    private void  loadLastScreen()
    {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        //TODO: ADD an animation to the get started button
        btnGetStarted.setAnimation(btnAnim);
    }


}