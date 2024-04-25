package comman1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sj_demo.R;

public class ResetActivity extends AppCompatActivity {

    private EditText otpEt1,otpEt2,otpEt3,otpEt4;
    private int selectedETPosition=0;
    KeyEvent keyEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        String user = getIntent().getStringExtra("username");
        String otp=getIntent().getStringExtra("otp");
        String pass=getIntent().getStringExtra("password");
        Button confirmBtn=findViewById(R.id.confirmBtn);
        otpEt1=findViewById(R.id.otpET1);
        otpEt2=findViewById(R.id.otpET2);
        otpEt3=findViewById(R.id.otpET3);
        otpEt4=findViewById(R.id.otpET4);
        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);
        //by default open keyboard at otpEt
        showKeyboard(otpEt1);
        DBHelper DB;
        DB = new DBHelper(this);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String generateOtp=otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();
                if(generateOtp.length()==4){
                    //handle otp verification
                    if(otp.equals(generateOtp)){
                        Boolean update=DB.updatePasswd(user,pass);
                        if(update){
                            Toast.makeText(ResetActivity.this, "Password reset Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ResetActivity.this, "Internal Error", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ResetActivity.this, "Wrong Otp", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ResetActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void showKeyboard(EditText otpEt){
        otpEt.requestFocus();
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpEt, InputMethodManager.SHOW_IMPLICIT);
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
    }
