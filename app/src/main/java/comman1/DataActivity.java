package comman1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj_demo.R;

import java.util.Random;

public class DataActivity extends AppCompatActivity {
    TextView txtDetail,txtQuantity,txtTime,txtAdd,txtUser,txtmob;
    DBHelper DB;
    ImageView cut;
    AppCompatButton accept;
    String mob1,ngo,user1,id1,Dname,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        DB=new DBHelper(this);
        txtAdd=findViewById(R.id.addressData);
        txtQuantity=findViewById(R.id.quantityData);
        accept=findViewById(R.id.accept);
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
        //String userid=DB.checkIdDonorForFood(Detail,time,Quantity,Add);
        //txtUser.setText(Integer.parseInt(userid));
        txtQuantity.setText(Quantity);
        txtAdd.setText(Add);
        txtUser.setText(user);
        txtmob.setText(mob);
        // adapter=new MyAdapter(context,food,time,quantity,address);
        txtDetail.setText(Detail);
        txtTime.setText(time);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status="Pending";
                boolean checkStatus=DB.updateStatus(Detail,time,Quantity,Add,status);

                if(checkStatus==true){
                    Toast.makeText(DataActivity.this, "Request is accepted", Toast.LENGTH_SHORT).show();
                    sendSmsDonor(mob);
                    sendOtpViaSmsNgo(mob1);
                    Intent intent = new Intent(DataActivity.this,Dashboard.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(DataActivity.this, "Request is not accepted", Toast.LENGTH_SHORT).show();
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

    // Generate a random 6-digit OTP (for demonstration purposes)
    private String generateRandomOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }// Send OTP through SMS
    private void sendSmsDonor(String mobileNumber) {
        String otp=generateRandomOtp();
        id1=otp;
        SmsManager smsManager = SmsManager.getDefault();
        String smsMessage = "Your Donation request is accepted by "+user1+".\n" +
                otp+" is your delivery code . Don't share this with anyone." ;
        smsManager.sendTextMessage(mobileNumber, null, smsMessage, null, null);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        // Log.d("SendSMS", "SMS sent successfully to: " + mobileNumber);
    }


    private void sendOtpViaSmsNgo(String mobileNumber) {
        SmsManager smsManager = SmsManager.getDefault();
        String smsMessage =" You have accepted the donation from "+Dname+".\n "+id1+" is your delivery code";
        smsManager.sendTextMessage(mobileNumber, null, smsMessage, null, null);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        // Log.d("SendSMS", "SMS sent successfully to: " + mobileNumber);
    }
}