package comman1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sj_demo.R;


public class Home_Fragment_D extends Fragment {

    Activity context;
    String user;
    DBHelper DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context=getActivity();
        View view= inflater.inflate(R.layout.fragment_home_d, container, false);

        return view;
    }
    public void onStart() {
        super.onStart();
        RelativeLayout foodLayout = (RelativeLayout) context.findViewById(R.id.foodLayout);
         DB = new DBHelper(context);
        Bundle args = getArguments();
            String username = args.getString("key");
            foodLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), FoodForm.class);
                    i.putExtra("key", username);
                    startActivity(i);
                }
            });
        super.onStart();
        RelativeLayout eduLayout = (RelativeLayout) context.findViewById(R.id.eduLayout);
        DB = new DBHelper(context);
        Bundle args1 = getArguments();
        String username1 = args.getString("key");
        eduLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EducationForm.class);
                i.putExtra("key", username);
                startActivity(i);
            }
        });
        super.onStart();
        RelativeLayout clothLayout = (RelativeLayout) context.findViewById(R.id.clothLayout);
        DB = new DBHelper(context);
        Bundle args2 = getArguments();
        String username2 = args.getString("key");
        clothLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ClothForm.class);
                i.putExtra("key", username);
                startActivity(i);
            }
        });

        super.onStart();
        RelativeLayout paymentLayout = (RelativeLayout) context.findViewById(R.id.paymentLayout);
        DB = new DBHelper(context);
        Bundle args3 = getArguments();
        String username3 = args.getString("key");
        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Payment.class);
                i.putExtra("key", username);
                startActivity(i);
            }
        });
        }

    }