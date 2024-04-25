package comman1;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sj_demo.R;

import java.util.ArrayList;


public class Profile extends Fragment {


    TextView donorName,donorMob,donorAdd,donorEmail;
    DBHelper DB;
    Activity context;
    ImageView logout;
    private  final int REQUEST_GALLERY_CODE=3;
    private  final int REQUEST_CAMERA_CODE=4;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile2, container, false);
        return view;
    }
    public void onStart(){
        super.onStart();
        DB=new DBHelper(context);
        donorName=context.findViewById(R.id.username);
        donorEmail=context.findViewById(R.id.userEmail);
        donorAdd=context.findViewById(R.id.userAddress);
        donorMob=context.findViewById(R.id.userMobile);
        logout=context.findViewById(R.id.logoutBtn);
        image=context.findViewById(R.id.image);
        Bundle args=getArguments();
        String username=args.getString("key");
        ArrayList<UserDetails> al=DB.getLoggedInUserDetails(username);
        UserDetails userDetails=al.get(0);
        donorName.setText(userDetails.getDName());
        donorEmail.setText(userDetails.getDEmail());
        donorAdd.setText(userDetails.getDAdd());
        donorMob.setText(userDetails.getDMob());
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery(v);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(v);
            }
        });
    }
    public void opengallery(View view)
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),REQUEST_GALLERY_CODE);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==REQUEST_GALLERY_CODE){
            Uri uri=data.getData();
            if(uri!=null){
                image.setImageURI(uri);
            }
        }
        if(resultCode==RESULT_OK && requestCode==REQUEST_CAMERA_CODE){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
            //boolean img=DB.insertDonorImg(bitmap);
        }
    }
    /*public void openCamera(View view){
      if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
          Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(intent,REQUEST_CAMERA_CODE);
      }
      else{
          ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_CODE);
      }
  }*/
    public void logOut(View view){
        startActivity(new Intent(getActivity(), Login.class));
    }
}

