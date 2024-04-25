package comman1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import comman1.UserDetails;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Donor.db";
    byte[] imageInBytes;
    private  Object Context;
    Context context;
    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table donors(donor_id Integer primary key autoincrement ,donor_name Text ,username TEXT ,password TEXT,mobile TEXT,email Text,address Text,image Blob)");
        MyDB.execSQL("create Table foodList(list_id  Integer primary key autoincrement,details Text,quantity String,cooking_time Text,address String,foodDonorId Integer references donors(donor_id),status Text)");
        MyDB.execSQL("create Table ngo(ngo_id Integer primary key autoincrement,username_ngo Text,password_ngo Text,ngo_name Text,uid Integer not null,ngo_type Text,email_ngo Text,phone Text,address_ngo Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists donors");
        MyDB.execSQL("drop Table if exists foodList");
        MyDB.execSQL("drop Table if exists ngo");
    }
    public boolean insertData(String username,String password,String mobile,String name){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("mobile",mobile);
        contentValues.put("donor_name",name);
        long result = MyDB.insert("donors", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public boolean insertNgoData(String username,String password,String mobile,String ngoName,int unique,String nt,String email,String add){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username_ngo", username);
        contentValues.put("password_ngo", password);
        contentValues.put("phone",mobile);
        contentValues.put("ngo_name",ngoName);
        contentValues.put("uid",unique);
        contentValues.put("ngo_type",nt);
        contentValues.put("email_ngo",email);
        contentValues.put("address_ngo",add);
        long result = MyDB.insert("ngo", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public int getDonorId(String user)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select donor_id from  donors where username = ? ", new String[] {user});
        if(cursor.moveToFirst()){
            return  Integer.valueOf(cursor.getString(0));
        }
        else{
            return -1;
        }
    }
    public Boolean updateStatus(String details,String quantity,String cooking_time,String address,String s){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("status", s);
        MyDB.update("foodList",contentValues,"details= ? ",new String[]{details} );
        return true;
    }

    public Boolean updateData(String id, String username,String password,String mobile,String name){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("mobile",mobile);
        contentValues.put("donor_name",name);
        MyDB.update("donors",contentValues,"id= ?",new String[]{id} );
        return true;
    }
    public Boolean insertFoodData(int id,String details,String quantity,String cooking_time,String address,String s){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("foodDonorId",id);
        contentValues.put("details", details);
        contentValues.put("quantity", quantity);
        contentValues.put("cooking_time",cooking_time);
        contentValues.put("address",address);
        contentValues.put("status",s);
        // contentValues.put("size",size);
        long result = MyDB.insert("foodList", null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }


    public String getMobileNumber(String user)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select mobile from  donors where username = ? ", new String[] {user});
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }
        else{
            return null;
        }
    }


    public int getNewCount(String status)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select *  from foodList WHERE status=?",new String[]{status});
        return cursor.getCount();
    }
    public  Boolean insertPersonalData(String Address, String Email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("email", Email);
        contentValues.put("address", Address);
        long result = MyDB.insert("donors", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public  Boolean insertDonorImg(Bitmap img){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        ByteArrayOutputStream objectByOutputStream=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,100,objectByOutputStream);
        imageInBytes=objectByOutputStream.toByteArray();
        ContentValues contentValues= new ContentValues();
        contentValues.put("img", imageInBytes);
        long result = MyDB.insert("donors", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from donors where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Cursor checkIdDonorForFood(String desc,String t,String q,String Add) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select foodDonorId from foodList where details = ? and quantity=? and cooking_time=? and address=? ", new String[]{desc,q,t,Add});
        return cursor;
    }
    public Boolean checkNgoUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from ngo where username_ngo = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from donors where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkUsernamePasswordNGO(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from ngo where username_ngo = ? and password_ngo = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getData()
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select * from foodList  ORDER BY list_id DESC",null);
        return cursor;
    }
    public Cursor getNgoData(String user)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select * from ngo  where username_ngo=?",new String[]{user});
        return cursor;
    }
    public Cursor getFoodData(String user)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select * from foodList WHERE foodDonorId=? ORDER BY list_id DESC",new String[]{user});
        return cursor;
    }

    public Boolean updatePasswd(String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        //contentValues.put("username", username);
        contentValues.put("password", password);
        //contentValues.put("mobile",mobile);
        //contentValues.put("donor_name",name);
        MyDB.update("donors",contentValues,"username= ?",new String[]{username} );
        return true;
    }
    public Cursor getMobileNameNGO(String user)
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select phone,ngo_name from ngo where username_ngo=?  ",new String[]{user});
        return cursor;
    }

    public Cursor getDonorData(String id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select username,mobile,donor_name from donors where donor_id=?", new String[]{id});
        return  cursor;
    }
    public Cursor getMobileNGO()
    {
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor cursor=DB.rawQuery("Select phone from ngo  ",null);
        return cursor;
    }
    public ArrayList<UserDetails> getLoggedInUserDetails(String username1)
    {
        ArrayList<UserDetails> al=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from  donors where username =?",new String[]{username1});
        if(cursor.moveToFirst()){
            String dName=cursor.getString(1);
            int dId=cursor.getInt(0);
            String dAdd=cursor.getString(5);
            String dMob =cursor.getString(4);
            String dEmail=cursor.getString(6);
            UserDetails userDetails1=new UserDetails();
            userDetails1.setDName(dName);
            userDetails1.setDId(dId);
            userDetails1.setDAdd(dAdd);
            userDetails1.setDEmail(dEmail);
            userDetails1.setDMob(dMob);
            al.add(userDetails1);
        }
        return al;
    }
    public Boolean insertAcceptance(int value,String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("accept",value);
        MyDB.update("foodList",contentValues,"id= ?",new String[]{username} );
        return true;
    }
 /*   public ArrayList<DonationList> getDonationDetails( )
    {
        ArrayList<DonationList> al=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from  foodList ",null);
        if(cursor.moveToNext()){
            String details=cursor.getString(1);
            int dId=cursor.getInt(0);
            String quantity=cursor.getString(2);
            String time =cursor.getString(3);
            String address=cursor.getString(4);
            int donorId=cursor.getInt(5);
            String status=cursor.getString(6);
            String size=cursor.getString(7);
            DonationList userDetails1=new DonationList();
            userDetails1.setList_id(dId);
            userDetails1.setDetails(details);
            userDetails1.setAdd(address);
            userDetails1.setQuantity(quantity);
            userDetails1.setCookTime(time);
            userDetails1.setFDId(donorId);
            userDetails1.setStatus(status);
            userDetails1.setSize(size);
            al.add(userDetails1);
        }
        return al;
    }*/
}
