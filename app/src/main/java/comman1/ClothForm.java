package comman1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj_demo.R;

import java.util.ArrayList;

public class ClothForm extends AppCompatActivity {

    ArrayList<String> valuesArray;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_form);
        final EditText cloth_detail=findViewById(R.id.cloth_description);
        Button postBtn=findViewById(R.id.Post_btn1);
        TextView name=findViewById(R.id.textView10);
        DB=new DBHelper(this);
        String id=getIntent().getStringExtra("key");
        int ID= DB.getDonorId(id);
        final EditText delivery=findViewById(R.id.delivery_address);
        final EditText cloth_size=findViewById(R.id.cloth_size);
        final ImageView cancel=findViewById( R.id.imageView2);
        name.setText(id);
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), DashboardDonors.class);
                startActivity(intent);
            }
        } );
        //ArrayList<String> valuesArray = new ArrayList<>();
        Cursor cursor=DB.getMobileNGO();
        valuesArray=new ArrayList<>();
        // Assuming you have a Cursor named "cursor"
        if (cursor != null) {
            cursor.moveToFirst(); // Move to the first row
            while (!cursor.isAfterLast()) { // Loop until the end of the cursor
                String value = cursor.getString(0); // Assuming the column index is 0
                valuesArray.add(value); // Add the value to the array
                cursor.moveToNext(); // Move to the next row
            }
            cursor.close(); // Close the cursor after use
        }
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cloth_details = cloth_detail.getText().toString();
                final String delivery_add = delivery.getText().toString();
                final String size = cloth_size.getText().toString();
                final String status="New";
                if (cloth_details == "" || delivery_add == "" )
                    Toast.makeText(ClothForm.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean post = DB.insertFoodData(ID, cloth_details,"- -","- -",delivery_add,status);
                    if (post == true) {
                        Toast.makeText(ClothForm.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                        sendSMS();
                        Intent intent = new Intent(getApplicationContext(), DashboardDonors.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ClothForm.this, "Request is not submitted ", Toast.LENGTH_SHORT).show();
                    }
                }


            }


        });
    }
    private void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        String message = "New Donation Request is Submitted";
        for (String phone : valuesArray) {
            smsManager.sendTextMessage(phone, null, message, null, null);
        }
        Toast.makeText(getApplicationContext(), "Messages sent!", Toast.LENGTH_SHORT).show();
    }
    }
