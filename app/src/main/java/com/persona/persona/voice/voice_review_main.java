package com.persona.persona.voice;

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

public class voice_review_main extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference();
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(10);
    private String matchingID, buyerID, code, matchingDate, actorUID, accept, contents;
    private Float rating;
    private Handler mHandler = new Handler();
    private String uid = FirebaseAuth.getInstance().getUid();
    private ArrayList<String> matchingIDS = new ArrayList<>();
    private ArrayList<String> voiceCodeS = new ArrayList<>();
    private ArrayList<String> buyerIdS = new ArrayList<>();
    private ArrayList<String> matchingDateS = new ArrayList<>();
    private ArrayList<String> contentS = new ArrayList<>();
    private ArrayList<Float> ratingS = new ArrayList<>();
    private LinearLayout motherLinear, norRsult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_review_main);

        motherLinear = (LinearLayout)findViewById(R.id.voice_review_mother_linear);
        norRsult = (LinearLayout)findViewById(R.id.voice_review_no_result_linear);

        recyclerView = (RecyclerView) findViewById(R.id.voice_review_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(decoration_height);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(matchingIDS.size() == 0){
                    motherLinear.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    norRsult.setVisibility(View.VISIBLE);
                }else{
                    motherLinear.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    norRsult.setVisibility(View.GONE);
                    adapter = new voice_review_recycler(matchingIDS, voiceCodeS, buyerIdS, matchingDateS, contentS, ratingS);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, 500);
    }

    void loadData() {
        userDB.child("MATCHING").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot fileSnapshot : snapshot.getChildren()) {
                    matchingID = fileSnapshot.getKey();
                    userDB.child("REVIEW").child(uid).child(matchingID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            accept = fileSnapshot.child("ACCEPT").getValue(String.class);
                            actorUID = fileSnapshot.child("ACTOR_UID").getValue(String.class);
                            buyerID = fileSnapshot.child("BUYER_ID").getValue(String.class);
                            code = fileSnapshot.child("CODE").getValue(String.class);
                            matchingDate = fileSnapshot.child("DATE").getValue(String.class);

                            Boolean flag = uid.equals(actorUID) && accept.equals("Y");
                            contents = snapshot.child("CONTENTS").getValue(String.class);
                            rating = snapshot.child("RATING").getValue(Float.class);
                            if(flag){
                                if(rating != null){
                                    matchingIDS.add(matchingID);
                                    buyerIdS.add(buyerID);
                                    voiceCodeS.add(code);
                                    matchingDateS.add(matchingDate);

                                    if(!(contents == null)){
                                        contentS.add(contents);
                                        ratingS.add(rating);
                                    }else{
                                        contents = " ";
                                        contentS.add(contents);
                                        ratingS.add(rating);
                                    }
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