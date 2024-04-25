package comman1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sj_demo.R;


public class DetailNgo extends Fragment {

    String[] item = {"Private Sector Companies", "Registered Societies(Non-Govt)", "Trust(Non-Govt)", "Other Registered Entities(Non-Govt)", "Academic Institution(Private)", "Academic Institution(Govt)"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    DBHelper DB;
    Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail_ngo, container, false);
        final EditText ngoName=view.findViewById(R.id.NgoET);
        final ImageView detailImg=view.findViewById(R.id.detailImg);
        final ImageView contactImg=view.findViewById(R.id.contactImg);
        final ImageView accountImg=view.findViewById(R.id.accountImg);
        final EditText uId=view.findViewById(R.id.uidET);
        DB=new DBHelper(context);
        final Button next1=view.findViewById(R.id.next1);
        final  Button back1=view.findViewById(R.id.back1);
        accountImg.setImageResource(R.drawable.editbtn);
        contactImg.setImageResource(R.drawable.editbtn);
        detailImg.setImageResource(R.drawable.editbtncolor);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngoNaming = ngoName.getText().toString();
                String uniqueId = uId.getText().toString();
                if(ngoNaming.equals("") || uniqueId.equals("")) {
                    Toast.makeText(getActivity(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Fragment contact=new ContactNgo();
                    Bundle bundle=new Bundle();
                    bundle.putString("name",ngoNaming);
                    bundle.putString("uid",uniqueId);
                    contact.setArguments(bundle);
                    FragmentTransaction transaction= getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout3,contact,null).commit();
                }
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });


        return view;
    }


    public void onStart() {
        super.onStart();


       /*autoCompleteTextView = context.findViewById(R.id.auto_complete_text);
       adapterItems = new ArrayAdapter<String>(context, R.layout.listngotype, item);
       autoCompleteTextView.setAdapter(adapterItems);
       autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
               String item = adapterView.getItemAtPosition(i).toString();
               Toast.makeText(context, "Item: " + item, Toast.LENGTH_SHORT).show();
          }
       });*/

    }
}