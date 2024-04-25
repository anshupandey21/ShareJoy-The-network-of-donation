package comman1;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj_demo.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Login extends AppCompatActivity {


    private boolean passwordShowing=false;
    Dialog dialog;
    Button btnDonor,btnNgo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final  TextView forgot=findViewById(R.id.forgotPasswd);
        final EditText usernameET =findViewById(R.id.UsernameEt);
        final EditText passwordsET =findViewById(R.id.passwdEt);
        final ImageView passwordIcon=findViewById(R.id.passwdIcon);
        final TextView signUpBtn=findViewById(R.id.SignUpBtn);
        final Button signInBtn=findViewById(R.id.signInBtn);
        dialog=new Dialog(Login.this);
        dialog.setContentView(R.layout.custom_dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        btnDonor=dialog.findViewById(R.id.btnDonorDialog);
        btnNgo=dialog.findViewById(R.id.btnNgoDialog);
        btnDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                dialog.dismiss();
            }
        });
        btnNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterNgo.class));
                dialog.dismiss();
            }
        });


        DBHelper DB;
        DB = new DBHelper(this);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is visible or not
                if(passwordShowing){
                    passwordShowing=false;
                    passwordsET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide_passwd);
                }
                else{
                    passwordShowing=true;
                    passwordsET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show_password);
                }
                //move the cursor at last of the text
                passwordsET.setSelection(passwordsET.length());
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), UserResetPasswordActivity.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String User = usernameET.getText().toString();
                String pass = passwordsET.getText().toString();
                if(User==""||pass=="")
                    Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(DB.checkUsernamePassword(User,pass)==true){
                        Toast.makeText(Login.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), DashboardDonors.class);
                        intent.putExtra("key",User);
                        startActivity(intent);}
                    else{
                        if(DB.checkUsernamePasswordNGO(User,pass)==true){
                            SharedPreferences sharedPreferences=getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("username",User);
                            editor.apply();
                            Toast.makeText(Login.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(getApplicationContext(), Dashboard.class);
                            intent.putExtra("key",User);
                            startActivity(intent);}
                        else{
                            Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                    /*Boolean checkUserpass = DB.checkUsernamePassword(User, pass);
                    if(checkUserpass==true){
                        Toast.makeText(Login.this, "Sign in Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(getApplicationContext(), DashBoard.class);
                        intent.putExtra("key",User);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }*/
            }
        });
    }
}
