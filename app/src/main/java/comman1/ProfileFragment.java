package comman1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.database.Cursor;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sj_demo.R;

public class ProfileFragment extends Fragment {
    TextView ngoName,ngoEmail,ngoPhone,ngoAdd,ngoUsername,logOut;
    DBHelper DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_profile, container, false);
        DB=new DBHelper(getContext());
        ngoName=view.findViewById(R.id.ngoName);
        logOut=view.findViewById(R.id.logoutNgo);
        ngoEmail=view.findViewById(R.id.ngoEmail);
        ngoPhone=view.findViewById(R.id.ngoPhone);
        ngoAdd=view.findViewById(R.id.ngoAdd);
        ngoUsername=view.findViewById(R.id.ngoUserName);
        Bundle args = getArguments();
        String username = args.getString("key");
        Cursor cursor=DB.getNgoData(username);
        if(cursor.getCount()==0)
        {


        }
        else {
            while (cursor.moveToNext()){
                ngoName.setText(cursor.getString(3));
                ngoEmail.setText(cursor.getString(6));
                ngoPhone.setText(cursor.getString(7));
                ngoAdd.setText(cursor.getString(8));
                ngoUsername.setText(cursor.getString(2));
            }
        }
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
        return view;
    }
}


