package comman1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj_demo.R;

public class CompletedActivity extends AppCompatActivity {
    TextView txtDetail,txtQuantity,txtTime,txtAdd,txtUser,txtmob;
    DBHelper DB;
    ImageView cut;
    MyAdapter adapter;
    Activity context;
    String mob1,ngo,user1,id1,Dname,id;
    AppCompatButton complete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        DB=new DBHelper(this);
        txtAdd=findViewById(R.id.addressData);
        txtQuantity=findViewById(R.id.quantityData);
        complete=findViewById(R.id.accept);
        cut=findViewById(R.id.backIntent);
        txtUser=findViewById(R.id.user);
        txtDetail=findViewById(R.id.textView16);
        txtTime=findViewById(R.id.cookingData);
        txtmob=findViewById(R.id.mobData);
        Intent intent=getIntent();
        String Detail=intent.getStringExtra("description");
        String time=intent.getStringExtra("time");
        String Add =intent.getStringExtra("address");
        String Quantity=intent.getStringExtra("quantity");
        ngo=intent.getStringExtra("username");

        Cursor cursor2=DB.getMobileNameNGO(ngo);
        cursor2.moveToFirst();
        mob1=cursor2.getString(0);
        user1=cursor2.getString(1);

        //Integer.parseInt(pos);
        Cursor cursor1=DB.checkIdDonorForFood(Detail,time,Quantity,Add);
        if (cursor1 != null) {
            cursor1.moveToFirst();
            id = cursor1.getString(0);
        }
        Cursor cursor = DB.getDonorData(id);
        cursor.moveToFirst();
        String mob=cursor.getString(1);
        String user=cursor.getString(0);
        Dname=cursor.getString(2);
        txtQuantity.setText(Quantity);
        txtAdd.setText(Add);
        txtmob.setText(mob);
        // adapter=new MyAdapter(context,food,time,quantity,address);
        txtDetail.setText(Detail);
        txtTime.setText(time);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Completed";
                boolean checkStatus=DB.updateStatus(Detail,time,Quantity,Add,status);
                if(checkStatus==true){
                    Toast.makeText(CompletedActivity.this, "Donation Request Completed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CompletedActivity.this, Dashboard.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CompletedActivity.this, "Request is not accepted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

    }

}
