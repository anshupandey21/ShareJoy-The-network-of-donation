package comman1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sj_demo.R;

import java.util.Random;

public class UserResetPasswordActivity extends AppCompatActivity {
    private boolean passwordShowing=false;
    EditText etName,etPasswd,etConPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password);
        etName=findViewById(R.id.UsernameEt);
        etPasswd=findViewById(R.id.passwdEt);
        etConPasswd=findViewById(R.id.passwdEet);
        AppCompatButton reset=findViewById(R.id.reset);
        final ImageView passwordIcon=findViewById(R.id.passwdIcon);
        final ImageView conPasswordIcon=findViewById(R.id.conPasswdIcon);
        DBHelper DB;
        DB = new DBHelper(this);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is visible or not
                if(passwordShowing){
                    passwordShowing=false;
                    etPasswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide_passwd);
                }
                else{
                    passwordShowing=true;
                    etPasswd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show_password);
                }
                //move the cursor at last of the text
                etPasswd.setSelection(etPasswd.length());
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=etName.getText().toString();
                String passwd=etPasswd.getText().toString();
                String conPasswd=etConPasswd.getText().toString();

                Boolean checkUser=DB.checkUsername(user);
                if(checkUser==true){
                    if(passwd.equals(conPasswd)){
                        String mob=DB.getMobileNumber(user);
                        String otp = generateRandomOtp();
                        sendOtpViaSms(mob, otp);
                        Intent intent=new Intent(getApplicationContext(),ResetActivity.class);
                        intent.putExtra("otp",otp);
                        intent.putExtra("username",user);
                        intent.putExtra("password",passwd);
                        startActivity(intent);
                    }else{
                        Toast.makeText(UserResetPasswordActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserResetPasswordActivity.this, "User does not Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is visible or not
                if(passwordShowing){
                    passwordShowing=false;
                    etConPasswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.hide_passwd);
                }
                else{
                    passwordShowing=true;
                    etConPasswd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.show_password);
                }
                //move the cursor at last of the text
                etConPasswd.setSelection(etConPasswd.length());
            }
        });

    }
    private String generateRandomOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }// Send OTP through SMS
    private void sendOtpViaSms(String mobileNumber, String otp) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String smsMessage = "Your OTP is: " + otp;
            smsManager.sendTextMessage(mobileNumber, null, smsMessage, null, null);
            Toast.makeText(this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
            Log.d("SendSMS", "SMS sent successfully to: " + mobileNumber);
        } catch (SecurityException securityException) {
            // Handle permission-related errors
            Toast.makeText(this, "Permission denied for sending SMS", Toast.LENGTH_SHORT).show();
            Log.e("SendSMS", "Permission denied for sending SMS");
            securityException.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            Toast.makeText(this, "Failed to send OTP", Toast.LENGTH_SHORT).show();
            Log.e("SendSMS", "Failed to send OTP", e);
            e.printStackTrace();
        }
    }
    }
