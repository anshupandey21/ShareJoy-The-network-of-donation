package comman1;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sj_demo.R;


public class ContactNgo extends Fragment {
    EditText emailNgo,phoneNgo,pinNgo,addNgo;
    Activity context;
    DBHelper DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_contact_ngo, container, false);
        emailNgo=view.findViewById(R.id.NgoET);
        final ImageView detailImg=view.findViewById(R.id.detailImg);
        DB=new DBHelper(context);
        final ImageView contactImg=view.findViewById(R.id.contactImg);
        final ImageView accountImg=view.findViewById(R.id.accountImg);
        phoneNgo=view.findViewById(R.id.phoneET);
        pinNgo=view.findViewById(R.id.pinNgoET);
        addNgo=view.findViewById(R.id.addET);
        detailImg.setImageResource(R.drawable.editbtncolor);
        accountImg.setImageResource(R.drawable.editbtn);
        contactImg.setImageResource(R.drawable.editbtncolor);
        final Button next2=view.findViewById(R.id.next2);
        final Button back2=view.findViewById(R.id.back2);
        Bundle args = getArguments();
        String user = args.getString("name");
        String uniqId = args.getString("uid");
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailNgo.getText().toString();
                String phone = phoneNgo.getText().toString();
                String add = addNgo.getText().toString();
                String pin = pinNgo.getText().toString();
                if(email.equals("") || phone.equals("")||add.equals("") || pin.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Fragment account=new AccountNgo();
                    Bundle bundle=new Bundle();
                    bundle.putString("name",user);
                    bundle.putString("uid",uniqId);
                    bundle.putString("eNgo",email);
                    bundle.putString("phoneNgo",phone);
                    bundle.putString("addNgo",add);
                    account.setArguments(bundle);
                    FragmentTransaction transaction= getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout3,account, null).commit();
                }
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment contact=new DetailNgo();
                FragmentTransaction transaction= getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout3,contact,null).commit();
            }
        });

        return view;
    }
}