package comman1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj_demo.R;

import java.util.Random;

public class OTPVerification extends AppCompatActivity {


    private EditText otpEt1,otpEt2,otpEt3,otpEt4;
    private TextView resendBtn;
    //turn after every 60 seconds
    private  boolean resendEnable=false;
    KeyEvent keyEvent;
    private AppCompatButton confirmBtn;
    //resend time in second
    private int resendTime=60;
    private int selectedETPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);


        confirmBtn=findViewById(R.id.confirmBtn);
        otpEt1=findViewById(R.id.otpET1);
        otpEt2=findViewById(R.id.otpET2);
        otpEt3=findViewById(R.id.otpET3);
        String mobileNumber = getIntent().getStringExtra("MOBILE_NUMBER");
        String otp=getIntent().getStringExtra("OTP");
        otpEt4=findViewById(R.id.otpET4);
        resendBtn=findViewById(R.id.signInBtn );
        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);


        //by default open keyboard at otpEt
        showKeyboard(otpEt1);
        //start resend count down timer
        startCountDownTimer();
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resendEnable)
                {
                    //startCountDownTimer();
                    String generatedOtp = generateRandomOtp();
                    String otp = generateRandomOtp();
                    sendOtpViaSms(mobileNumber, otp);
                    Toast.makeText(OTPVerification.this, "New OTP sent: " + generatedOtp, Toast.LENGTH_SHORT).show();
                }                }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String generateOtp=otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();
                if(generateOtp.length()==4){
                    //handle otp verification
                    if(otp.equals(generateOtp)){
                        Toast.makeText(OTPVerification.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(OTPVerification.this, "Wrong Otp", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(OTPVerification.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showKeyboard(EditText otpEt){
        otpEt.requestFocus();
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpEt,InputMethodManager.SHOW_IMPLICIT);
    }
    private void startCountDownTimer(){
        resendEnable=false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));


        new CountDownTimer(resendTime*1000,1000){


            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend OTP ("+(millisUntilFinished/1000)+")");
            }


            @Override
            public void onFinish() {


                resendEnable=true;
                resendBtn.setText("Resend OTP" );
                resendBtn.setTextColor(getResources().getColor(R.color.purple));
            }
        }.start();
    }
    private final TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }


        @Override
        public void afterTextChanged(Editable s) {


            if(s.length()>0)
            {
                if(selectedETPosition==0){
                    selectedETPosition=1;
                    showKeyboard(otpEt2);
                }
                else if(selectedETPosition==1){
                    selectedETPosition=2;
                    showKeyboard(otpEt3);
                }
                else if(selectedETPosition==2){
                    selectedETPosition=3;
                    showKeyboard(otpEt4);
                }
            }
        }
    };


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == keyEvent.KEYCODE_DEL){
            if(selectedETPosition==3){
                selectedETPosition=2;
                showKeyboard(otpEt3);
            }
            else if(selectedETPosition==2){
                selectedETPosition=1;
                showKeyboard(otpEt2);
            }
            else if(selectedETPosition==1){
                selectedETPosition=0;
                showKeyboard(otpEt1);
            }
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }


    }
    // Generate a random 6-digit OTP (for demonstration purposes)
    private String generateRandomOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }
    // Send OTP through SMS
    private void sendOtpViaSms(String mobileNumber, String otp) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String smsMessage = "Your OTP is: " + otp;
            smsManager.sendTextMessage(mobileNumber, null, smsMessage, null, null);
            Toast.makeText(this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
