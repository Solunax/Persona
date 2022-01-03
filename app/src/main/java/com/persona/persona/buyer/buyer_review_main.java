package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;
import com.persona.persona.RecyclerDecoration_Height;

import java.util.ArrayList;

public class buyer_review_main extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference();
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(10);
    private String matchingID, actorID, code, matchingDate, accept, buyerUID, actorUID, compare, compareUID;
    private String uid = FirebaseAuth.getInstance().getUid();
    private Handler mHandler = new Handler();
    private ArrayList<String> matchingIDS = new ArrayList<>();
    private ArrayList<String> voiceCodeS = new ArrayList<>();
    private ArrayList<String> buyerIdS = new ArrayList<>();
    private ArrayList<String> matchingDateS = new ArrayList<>();
    private ArrayList<String> actorUIDS = new ArrayList<>();
    private ArrayList<String> compareS = new ArrayList<>();
    private LinearLayout motherLinear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_review_main);

        uid = FirebaseAuth.getInstance().getUid();

        motherLinear = (LinearLayout)findViewById(R.id.buyer_review_mother_linear);

        recyclerView = (RecyclerView)findViewById(R.id.buyer_review_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(decoration_height);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                motherLinear.setVisibility(View.VISIBLE);
                adapter = new buyer_review_recycler(matchingIDS, voiceCodeS, buyerIdS, matchingDateS, actorUIDS, compareS);
                recyclerView.setAdapter(adapter);
            }
        },500);
    }

    void loadData(){
        userDB.child("MATCHING").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot fileSnapshot : snapshot.getChildren()) {
                    matchingID = fileSnapshot.getKey();
                    accept = fileSnapshot.child("ACCEPT").getValue(String.class);
                    buyerUID = fileSnapshot.child("BUYER_UID").getValue(String.class);
                    actorID = fileSnapshot.child("ACTOR_ID").getValue(String.class);
                    actorUID = fileSnapshot.child("ACTOR_UID").getValue(String.class);
                    code = fileSnapshot.child("CODE").getValue(String.class);
                    matchingDate = fileSnapshot.child("DATE").getValue(String.class);

                    Boolean flag = uid.equals(buyerUID) && accept.equals("Y");

                    if(flag){
                        matchingIDS.add(matchingID);
                        buyerIdS.add(actorID);
                        voiceCodeS.add(code);
                        matchingDateS.add(matchingDate);
                        actorUIDS.add(actorUID);
                    }


                    userDB.child("REVIEW").child(actorUID).child(matchingID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            compare = snapshot.child("BUYER_UID").getValue(String.class);
                            compareUID = snapshot.child("ACTOR_UID").getValue(String.class);

                            if(flag){
                                if(compare != null){
                                    compareS.add(compare);
                                }else{
                                    compare = "N";
                                    compareS.add(compare);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}