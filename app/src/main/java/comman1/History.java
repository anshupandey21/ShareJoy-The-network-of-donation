package comman1;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sj_demo.R;

import java.util.ArrayList;


public class History extends Fragment {



    RecyclerView recyclerView;
    ArrayList<String> food,time,quantity,address,status;
    DBHelper DB;
    MyAdapter adapter;
    Activity context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        View view=inflater.inflate(R.layout.fragment_history2, container, false);
        return view;

    }
    public void onStart(){
        super.onStart();
        DB=new DBHelper(context);
        food=new ArrayList<>();
        time=new ArrayList<>();
        quantity=new ArrayList<>();
        status=new ArrayList<>();
        address=new ArrayList<>();
        recyclerView=context.findViewById(R.id.recyclerView);
        adapter=new MyAdapter(context,food,time,quantity,address,status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
       // Bundle args=getArguments();
        //String username=args.getString("key");
       // int id=DB.getDonorId(username);
        //Cursor cursor=DB.getHistoryData(id); Bundle args = getArguments();
        //        String username = args.getString("key");
        //        int id=DB.getDonorId(username);
        //        Cursor cursor=DB.getFoodData(String.valueOf(id));
        Bundle args = getArguments();
        String username = args.getString("key");
        int id=DB.getDonorId(username);
        Cursor cursor=DB.getFoodData(String.valueOf(id));
        if(cursor.getCount()==0)
        {
            Toast.makeText(context,"No post is created",Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext()){
                food.add(cursor.getString(1));
                time.add(cursor.getString(2));
                quantity.add(cursor.getString(3));
                address.add(cursor.getString(4));
                status.add(cursor.getString(6));
            }
        }

    }


}