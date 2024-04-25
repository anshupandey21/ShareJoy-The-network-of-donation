package comman1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sj_demo.R;

public class AccountNgo extends Fragment {

    EditText userNgo, paswdNgo;
    private boolean passwordShowing = false;
    Activity context;
    DBHelper DB;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_ngo, container, false);
        final ImageView detailImg = view.findViewById(R.id.detailImg);
        final ImageView contactImg = view.findViewById(R.id.contactImg);
        final ImageView accountImg = view.findViewById(R.id.accountImg);
        final ImageView passwordIcon = view.findViewById(R.id.passwdIcon);
        detailImg.setImageResource(R.drawable.editbtncolor);
        accountImg.setImageResource(R.drawable.editbtncolor);
        final Button finish = view.findViewById(R.id.finishBtn);
        final Button back3 = view.findViewById(R.id.back3);
        contactImg.setImageResource(R.drawable.editbtncolor);
        userNgo = view.findViewById(R.id.unameNgo);
        paswdNgo = view.findViewById(R.id.passwdNgo);
        DB = new DBHelper(getContext());
        Bundle args = getArguments();
        String user = args.getString("name");
        String uniqId = args.getString("uid");
        String email = args.getString("eNgo");
        String mob = args.getString("phoneNgo");
        String address = args.getString("addNgo");
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if password is visible or not
                if (passwordShowing) {
                    passwordShowing = false;
                    paswdNgo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.hide_passwd);
                } else {
                    passwordShowing = true;
                    paswdNgo.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.show_password);
                }
                //move the cursor at last of the text
                paswdNgo.setSelection(paswdNgo.length());
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = userNgo.getText().toString();
                String pass = paswdNgo.getText().toString();
                if (uname.equals("") || pass.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkuser = DB.checkNgoUsername(uname);
                    if (checkuser == false) {
                        boolean insert = DB.insertNgoData(uname, pass, mob, user, Integer.parseInt(uniqId), "", email, address);
                        if (insert == true) {
                            Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment contact = new ContactNgo();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout3, contact, null).commit();
            }
        });

        return  view;
    }
}