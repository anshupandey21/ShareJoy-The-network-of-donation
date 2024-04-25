package comman1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.Manifest;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;
//import com.example.sj_demo.Manifest;
import com.example.sj_demo.R;
import android.util.Patterns;
import java.util.ArrayList;
import java.util.Random;

public class Register extends AppCompatActivity {
    public  EditText uname,name,mobile,password,conPassword;
    private boolean passwordShowing=false;
    private boolean conPasswordShowing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DBHelper DB;
        DB = new DBHelper(this);
        final EditText mobile = findViewById(R.id.mobET);
        final EditText uname = findViewById(R.id.fullnameEt);
        final EditText name = findViewById(R.id.nameEt);
        final AppCompatButton signUpBtn = findViewById(R.id.SignUpBtn);
        final EditText password = findViewById(R.id.passwdEt);
        final EditText conPassword = findViewById(R.id.cpasswdEt);
        final ImageView passwordIcon = findViewById(R.id.passwdIcon);
        final ImageView conPasswordIcon = findViewById(R.id.cpasswdIcon);
        final TextView signInBtn = findViewById(R.id.signInBtn);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is visible or not
                if (passwordShowing) {
                    passwordShowing = false;
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide_passwd);
                } else {
                    passwordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show_password);
                }
                //move the cursor at last of the text
                password.setSelection(password.length());
            }
        });


        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is visible or not
                if (conPasswordShowing) {
                    passwordShowing = false;
                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.show_password);
                } else {
                    passwordShowing = true;
                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.hide_passwd);
                }
                //move the cursor at last of the text
                conPassword.setSelection(conPassword.length());
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening OTPVerification activity along with email
                final String user = uname.getText().toString();
                final String pass = password.getText().toString();
                final String repass = conPassword.getText().toString();
                final String mob = mobile.getText().toString();
                final String fullName = name.getText().toString();
                if (user.equals("") || pass.equals("") || repass.equals("") || mob.equals(""))
                    Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if(!user.matches("[a-zA-Z ]{1,20}")){
                        if(mob.length() != 11){
                            if(pass.length() < 8 || !pass.matches(".*\\d.*") || !pass.matches(".*[A-Z].*")){
                                if (pass.equals(repass)) {
                                    Boolean checkuser = DB.checkUsername(user);
                                    if (checkuser == false) {
                                        boolean insert = DB.insertData(user, pass, mob, fullName);
                                        if (insert == true) {
                                            if (isValidMobileNumber(mob)) {
                                                String otp = generateRandomOtp();
                                                sendOtpViaSms(mob, otp);
                                                Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
                                                intent.putExtra("MOBILE_NUMBER", mob);
                                                intent.putExtra("OTP",otp);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(Register.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
                                            }
                                            // Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(Register.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(Register.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Register.this, "Password should be at least 8 characters long, contain at least one digit and one uppercase letter",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Register.this, "Contact should be 10 digits", Toast.LENGTH_SHORT).show();
                        }}
                    else{
                        Toast.makeText(Register.this, "Username should contain letters and digits", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
    // Validate the mobile number using a simple pattern
    private boolean isValidMobileNumber(String mob) {
        return Patterns.PHONE.matcher(mob).matches();
    }

    // Generate a random 6-digit OTP (for demonstration purposes)
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
