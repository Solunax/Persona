package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;
import com.persona.persona.RecyclerDecoration_Height;

import java.util.ArrayList;

public class buyer_announce_show_actor extends Activity {
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Handler mHandler = new Handler();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> uidList = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();
    private String announceID;
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(20);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_announce_show_actor);

        recyclerView = (RecyclerView)findViewById(R.id.buyer_announce_actor_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration_height);

        Intent beforeData = getIntent();
        announceID = beforeData.getStringExtra("announceID");

        DatabaseReference userDB = database.getReference("ANNOUNCE").child(announceID).child("ACT_SAMPLE");
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                    String name = fileSnapshot.child("NAME").getValue(String.class);
                    String path = fileSnapshot.child("SAMPLE_PATH").getValue(String.class);
                    String uid = fileSnapshot.getKey();
                    nameList.add(name);
                    pathList.add(path);
                    uidList.add(uid);
                    Log.d("ABC", path);
                    Log.d("ABC", uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new buyer_announce_actor_list_recycler(nameList, pathList, uidList);
                recyclerView.setAdapter(adapter);
            }
        },500);
    }
}
