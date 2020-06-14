package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.firebase.numbookmark;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bookmark_num extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();
    Button nbm6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_num);
        listView = (ListView)findViewById(R.id.listnbm);
        initDatabase();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,new ArrayList<String>());
        listView.setAdapter(adapter);
        mReference = mDatabase.getReference("NumBookMark").child("sl");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                String myKey = dataSnapshot.getKey();
                adapter.clear();

                for (DataSnapshot dbmData : dataSnapshot.getChildren()){
                    String dbmdata2 = dbmData.getValue().toString();
                    Array.add(dbmdata2);
                    adapter.add(dbmdata2);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
        ActionBar ab = getSupportActionBar() ;
        ab.setIcon(R.drawable.pocketpolice_icon) ;
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        Button button1=(Button)findViewById(R.id.btn_complete);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(), setting.class);
                startActivity(intent1);
            }
        });

        nbm6 = (Button) findViewById(R.id.nbm6);
        nbm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pg2 = new Intent(bookmark_num.this, nbmupdate.class);
                startActivity(pg2);
            }
        });
    }
    private void initDatabase(){

        mDatabase =FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference("NumBookMark").child("sl");
        //mReference.child(usrid);
        //mReference.child("log").setValue("check");
        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }
}