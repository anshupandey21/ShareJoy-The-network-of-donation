package comman1;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.Manifest;
//import com.example.sj_demo.Manifest;
import com.example.sj_demo.R;
import com.ramotion.fluidslider.FluidSlider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;


    public class FoodForm extends AppCompatActivity implements LocationListener {


        private final int max = 200;
        private final int min = 2;
        private final int total = max - min;
        int t1Hour,t1Minute;
        ArrayList<String> valuesArray;
        LocationManager locationManager;
        EditText delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_food_form );
        final EditText details=findViewById(R.id.food_description);
        final TextView pin=findViewById(R.id.addressText);
        Button postBtn=findViewById(R.id.Post_btn);
        TextView name=findViewById(R.id.DonorName);
        delivery=findViewById(R.id.delievery_description);
        FluidSlider slider = findViewById(R.id.slider_food);
        final TextView tvTimer= findViewById(R.id.select_time);
        final ImageView cancel=findViewById( R.id.imageView2 );
        final TextView quantity =  findViewById(R.id.quantity_number);
        DBHelper DB;
        DB=new DBHelper(this);
        String id=getIntent().getStringExtra("key");
        int ID= DB.getDonorId(id);

        name.setText(id);
        slider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                Log.d("D", "setBeginTrackingListener");
                return Unit.INSTANCE;
            }
        });
        slider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                Log.d("D", "setEndTrackingListener");
                return Unit.INSTANCE;
            }
        });
        slider.setStartText(Integer.toString(min));
        slider.setEndText(Integer.toString(max));
        slider.setPositionListener(pos -> {
            final String value = String.valueOf((int) ((min + (total * pos))));
            slider.setBubbleText(value);
            quantity.setText(value);
            return Unit.INSTANCE;
        });
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(
                        FoodForm.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t1Hour=hourOfDay;
                                t1Minute=minute;
                                Calendar calendar=Calendar.getInstance();
                                calendar.set(0,0,0,t1Hour,t1Minute);
                                tvTimer.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();
            }
        });

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
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), DashboardDonors.class);
                startActivity(intent);
            }
        } );
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  final int ID=Integerid);
                final int i=ID;
                final String food_details = details.getText().toString();
                final  String delivery_description = delivery.getText().toString();
                final  String time= tvTimer.getText().toString();
                final String status="New";
                final String quantity_no=quantity.getText().toString();
                if(food_details=="" || delivery_description=="" || time=="Select Time" || quantity_no=="")
                    Toast.makeText(FoodForm.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean post = DB.insertFoodData(i,food_details, quantity_no, time, delivery_description,status);
                    DB.close();
                    if(post==true){
                        Toast.makeText(FoodForm.this, "Food Request Submitted", Toast.LENGTH_SHORT).show();
                        sendSMS();
                        Intent intent  = new Intent(getApplicationContext(), DashboardDonors.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(FoodForm.this, "Food request is not submitted ", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }

        @SuppressLint("MissingPermission")
        private void getLocation() {
            try{
                locationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,FoodForm.this);
            }catch(Exception e){
                e.printStackTrace();
            }



        }
        private void sendSMS() {
            SmsManager smsManager = SmsManager.getDefault();
            String message = "New Donation Request is Submitted";
            for (String phone : valuesArray) {
                smsManager.sendTextMessage(phone, null, message, null, null);
            }
            Toast.makeText(getApplicationContext(), "Messages sent!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
            try {
                Geocoder geocoder = new Geocoder(FoodForm.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                String address = addresses.get(0).getAddressLine(0);
                delivery.setText(address);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            //LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            //LocationListener.super.onProviderDisabled(provider);
        }
    }













