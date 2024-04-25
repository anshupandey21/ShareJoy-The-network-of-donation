package comman1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.sj_demo.R;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    Context mContext;
    List<ScreenItem> mListScreen;

    public IntroViewPagerAdapter(Context mContex,List<ScreenItem> mListScreen) {
        this.mContext=mContex;
        this.mListScreen=mListScreen;

    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflator=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen=inflator.inflate(R.layout.layout_screen, null);
        ImageView imgSlide=layoutScreen.findViewById(R.id.imageView);
        TextView title=layoutScreen.findViewById(R.id.textView2);
        TextView description=layoutScreen.findViewById(R.id.textView4);
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());
        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        container.addView(layoutScreen);
        return layoutScreen;








    }


    @Override
    public int getCount() {
        return mListScreen.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }



}
