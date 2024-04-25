package comman1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sj_demo.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    // no. of selected tab ,we have 4 tabs so value must lie between 1-4. default value is 1 because first tab is selected by default.
    private int selectedTab=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final LinearLayout homeLayout1=findViewById(R.id.homeLayout1);

        final LinearLayout profileLayout1=findViewById(R.id.profileLayout1);

        final ImageView homeImg1=findViewById(R.id.homeImg1);
        final ImageView profileImg1=findViewById(R.id.profileImg1);

        final TextView homeTxt1=findViewById(R.id.homeTxt1);
        final TextView profileTxt1=findViewById(R.id.profileTxt1);

        /*Intent intent=getIntent();
        String username=intent.getStringExtra("key");
        Bundle bundle=new Bundle();
        bundle.putString("key",username);*/
        Intent intent=getIntent();
        String username=intent.getStringExtra("key");
        Bundle bundle=new Bundle();
        bundle.putString("key",username);
        Intent intent1=getIntent();
        int accept=intent1.getIntExtra("key1",0);
        Bundle bundle1=new Bundle();
        bundle1.putString("key1",String.valueOf(accept));


        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayout1,HomeFragment.class,null).commit();
        homeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab!=1)
                {




                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayout1,HomeFragment.class,bundle).commit();

                    profileTxt1.setVisibility(View.GONE);

                    profileImg1.setImageResource(R.drawable.profile);





                    profileLayout1.setBackgroundResource(android.R.color.transparent);
                    homeTxt1.setVisibility(View.VISIBLE);
                    homeImg1.setImageResource(R.drawable.home_selected);
                    homeLayout1.setBackgroundResource(R.drawable.round_back_home1);
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout1.startAnimation(scaleAnimation);
                    selectedTab=1;
                }
            }
        });










        profileLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab!=4)
                {
                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.frameLayout1,ProfileFragment.class,bundle).commit();
                    homeTxt1.setVisibility(View.GONE);

                    homeImg1.setImageResource(R.drawable.home);





                    homeLayout1.setBackgroundResource(android.R.color.transparent);

                    profileTxt1.setVisibility(View.VISIBLE);
                    profileImg1.setImageResource(R.drawable.profile_selected);
                    profileLayout1.setBackgroundResource(R.drawable.round_back_profile1);
                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout1.startAnimation(scaleAnimation);
                    selectedTab=4;
                }
            }
        });
    }
}





