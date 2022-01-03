package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class buyer_matching_state_complete extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference();
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(10);
    private String matchingID, actorID, code, matchingDate, accept, buyerUID;
    private String uid = FirebaseAuth.getInstance().getUid();
    private Handler mHandler = new Handler();
    private ArrayList<String> matchingIDS = new ArrayList<>();
    private ArrayList<String> voiceCodeS = new ArrayList<>();
    private ArrayList<String> buyerIdS = new ArrayList<>();
    private ArrayList<String> matchingDateS = new ArrayList<>();
    private TextView noResult;
    private LinearLayout motherLinear;
    private Button review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_matching_state_complete);

        uid = FirebaseAuth.getInstance().getUid();

        noResult = (TextView)findViewById(R.id.buyer_matching_complete_no_result);
        motherLinear = (LinearLayout)findViewById(R.id.buyer_matching_complete_mother_linear);

        recyclerView = (RecyclerView)findViewById(R.id.buyer_matching_complete_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(decoration_height);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(matchingIDS.size() == 0){
                    recyclerView.setVisibility(View.GONE);
                    noResult.setVisibility(View.VISIBLE);
                }else{
                    motherLinear.setVisibility(View.VISIBLE);
                    adapter = new buyer_matching_recycler(matchingIDS, voiceCodeS, buyerIdS, matchingDateS);
                    recyclerView.setAdapter(adapter);
                }
            }
        },500);

        review = (Button)findViewById(R.id.voice_matching_review_button);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_matching_state_complete.this, buyer_review_main.class);
                startActivity(intent);
            }
        });
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
                    code = fileSnapshot.child("CODE").getValue(String.class);
                    matchingDate = fileSnapshot.child("DATE").getValue(String.class);

                    if (uid.equals(buyerUID) && accept.equals("Y")) {
                        matchingIDS.add(matchingID);
                        buyerIdS.add(actorID);
                        voiceCodeS.add(code);
                        matchingDateS.add(matchingDate);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}