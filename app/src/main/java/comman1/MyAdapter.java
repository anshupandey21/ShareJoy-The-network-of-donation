package comman1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sj_demo.R;

import java.util.ArrayList;import comman1.Dashboard;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    CardView main_card;
    private ArrayList food_description, quantity, time, address,status;

    public MyAdapter( Context context,ArrayList food_description,ArrayList time,ArrayList quantity,ArrayList address,ArrayList status) {
        this.context = context;
        this.food_description = food_description;
        this.quantity = quantity;
        this.time = time;
        this.address = address;
        this.status=status;
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.foodlistentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.food_description.setText(String.valueOf(food_description.get(position)));
        holder.quantity.setText(String.valueOf(quantity.get(position)));
        holder.address.setText(String.valueOf(address.get(position)));
        holder.time.setText(String.valueOf(time.get(position)));
        String user=getLoggedInUser();
        holder.status.setText(String.valueOf(status.get(position)));
        main_card = holder.itemView.findViewById(R.id.main_card);
        holder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status1 = (status.get(position)).toString();
                if (status1.equals("New")) {
                    Intent intent = new Intent(v.getContext(), DataActivity.class);
                    intent.putExtra("time", holder.time.getText().toString());
                    intent.putExtra("description", holder.food_description.getText().toString());
                    intent.putExtra("address", holder.address.getText().toString());
                    intent.putExtra("quantity", holder.quantity.getText().toString());
                    intent.putExtra("username",user);
                    context.startActivity(intent);
                } else if (status1.equals("Pending")) {
                    Intent intent1 = new Intent(v.getContext(), CompletedActivity.class);
                    intent1.putExtra("time", holder.time.getText().toString());
                    intent1.putExtra("description", holder.food_description.getText().toString());
                    intent1.putExtra("address", holder.address.getText().toString());
                    intent1.putExtra("quantity", holder.quantity.getText().toString());
                    intent1.putExtra("pos", String.valueOf(position));
                    intent1.putExtra("username",user);
                    context.startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(v.getContext(), Dashboard.class);
                    context.startActivity(intent1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return food_description.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView food_description, quantity, time, address,status;
        CardView main_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_description = itemView.findViewById(R.id.foodDetail);
            quantity = itemView.findViewById(R.id.quantity);
            time = itemView.findViewById(R.id.cookingTime);
            address = itemView.findViewById(R.id.address);
            main_card=itemView.findViewById(R.id.main_card);
            status=itemView.findViewById(R.id.status);
        }


    }
    private  String getLoggedInUser()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("user_pref",Context.MODE_PRIVATE);
        return  sharedPreferences.getString("username","");
    }
}


