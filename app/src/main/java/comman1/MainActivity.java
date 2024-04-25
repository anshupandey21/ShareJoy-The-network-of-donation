package comman1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sj_demo.R;

public class MainActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image1;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        image1=findViewById(R.id.imageView1);
        name=findViewById(R.id.textView);

        image1.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        Intent intent=new Intent(MainActivity.this, OnBoarding.class);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                startActivity(intent);
            }
        },2000);



    }
}