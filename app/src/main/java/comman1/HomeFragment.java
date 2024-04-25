package comman1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj_demo.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    Activity context;
    DBHelper DB;
    RecyclerView recyclerView;
    ArrayList<String> food,time,quantity,address,status;
    MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        View view= inflater.inflate(R.layout.fragment_home, container, false);
       return view;
    }
    public void onStart() {
        super.onStart();
        DB = new DBHelper(context);
        food=new ArrayList<>();
        time=new ArrayList<>();
        quantity=new ArrayList<>();
        status=new ArrayList<>();
        address=new ArrayList<>();
        TextView newCount = context.findViewById(R.id.newCount);
        TextView pendingCount = context.findViewById(R.id.pendingCount);
        TextView completedCount = context.findViewById(R.id.completedCount);
        int n=DB.getNewCount("New");
        newCount.setText(String.valueOf(n));
        int p =DB.getNewCount("Pending");
        pendingCount.setText(String.valueOf(p));
        int c=DB.getNewCount("Completed");
        completedCount.setText(String.valueOf(c));
        recyclerView = context.findViewById(R.id.rvHomeNgo);
        adapter=new MyAdapter(context,food,time,quantity,address,status);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Cursor cursor = (Cursor)DB.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No post is created", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                food.add(cursor.getString(1));
                time.add(cursor.getString(3));
                quantity.add(cursor.getString(2));
                address.add(cursor.getString(4));
                status.add(cursor.getString(6));
            }
        }
        cursor.close();
        DB.close();
    }


}
