package comman1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sj_demo.R;

public class DashboardDonors extends AppCompatActivity {
    private int selectedTab=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_donors);
        final LinearLayout homeLayout=findViewById(R.id.homeLayout1);
        final LinearLayout historyLayout=findViewById(R.id.historyLayout1);
        final LinearLayout profileLayout=findViewById(R.id.profileLayout1);


        final ImageView homeImg=findViewById(R.id.homeImg1);
        final ImageView historyImg=findViewById(R.id.historyImg);
        final ImageView profileImg=findViewById(R.id.profileImg1);


        final TextView homeTxt=findViewById(R.id.homeTxt1);
        final TextView profileTxt=findViewById(R.id.profileTxt);
        final TextView historyTxt=findViewById(R.id.historyTxt1);


       Intent intent=getIntent();
           String username=intent.getStringExtra("key");
               Bundle bundle=new Bundle();
               bundle.putString("key",username);
               getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, Home_Fragment_D.class,bundle).commit();
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab!=1)
                {
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer,Home_Fragment_D.class,bundle).commit();

                    historyTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);

                    historyImg.setImageResource(R.drawable.history);
                    profileImg.setImageResource(R.drawable.profile);

                    profileLayout.setBackgroundResource(android.R.color.transparent);
                    historyLayout.setBackgroundResource(android.R.color.transparent);
                    homeTxt.setVisibility(View.VISIBLE);
                    homeImg.setImageResource(R.drawable.home_selected);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home1);
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);
                    selectedTab=1;
                }
            }
        });





        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab!=3)
                {
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer,History.class,bundle).commit();
                    homeTxt.setVisibility(View.GONE);

                    profileTxt.setVisibility(View.GONE);
                    homeImg.setImageResource(R.drawable.home);

                    profileImg.setImageResource(R.drawable.profile);


                    homeLayout.setBackgroundResource(android.R.color.transparent);
                    profileLayout.setBackgroundResource(android.R.color.transparent);

                    historyTxt.setVisibility(View.VISIBLE);
                    historyImg.setImageResource(R.drawable.history_selected);
                    historyLayout.setBackgroundResource(R.drawable.round_back_post1);
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    historyLayout.startAnimation(scaleAnimation);
                    selectedTab=3;
                }
            }
        });


        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab!=4)
                {
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainer,Profile.class,bundle).commit();
                    homeTxt.setVisibility(View.GONE);

                    historyTxt.setVisibility(View.GONE);
                    homeImg.setImageResource(R.drawable.home);

                    historyImg.setImageResource(R.drawable.history);


                    homeLayout.setBackgroundResource(android.R.color.transparent);
                    historyLayout.setBackgroundResource(android.R.color.transparent);

                    profileTxt.setVisibility(View.VISIBLE);
                    profileImg.setImageResource(R.drawable.profile_selected);
                    profileLayout.setBackgroundResource(R.drawable.round_back_profile1);
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);
                    selectedTab=4;
                }
            }
        });












    }
}




