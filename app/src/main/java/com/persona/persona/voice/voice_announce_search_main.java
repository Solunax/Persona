package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.persona.persona.buyer.buyer_announce_recycler;

import java.util.ArrayList;

public class voice_announce_search_main extends Activity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference();
    private String announceID, title, explainCharacter, script, pay, character, role, startDate, endDate;
    private ArrayList<String> announceIDS  = new ArrayList<>();
    private ArrayList<String> titleS  = new ArrayList<>();
    private ArrayList<String> explainCharacterS  = new ArrayList<>();
    private ArrayList<String> scriptS  = new ArrayList<>();
    private ArrayList<String> payS  = new ArrayList<>();
    private ArrayList<String> characterS  = new ArrayList<>();
    private ArrayList<String> roleS  = new ArrayList<>();
    private ArrayList<String> startDateS  = new ArrayList<>();
    private ArrayList<String> endDateS  = new ArrayList<>();
    private ArrayList<Integer> numberS  = new ArrayList<>();
    private Handler mHandler = new Handler();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(10);
    private Button applyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_announce_search_main);

        recyclerView = (RecyclerView)findViewById(R.id.voice_announce_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(decoration_height);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        applyList = (Button)findViewById(R.id.voice_announce_apply_list);
        applyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_announce_search_main.this, voice_announce_apply_list.class);
                startActivity(intent);
            }
        });

        loadData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new voice_announce_search_recycler(announceIDS, titleS, explainCharacterS, scriptS, roleS, startDateS, endDateS, characterS, payS, numberS);
                recyclerView.setAdapter(adapter);
            }
        },500);

    }

    void loadData(){
        userDB.child("ANNOUNCE").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                    announceID = fileSnapshot.getKey();
                    title = fileSnapshot.child("TITLE").getValue(String.class);
                    explainCharacter = fileSnapshot.child("EXPLAIN").getValue(String.class);
                    script = fileSnapshot.child("SCRIPT").getValue(String.class);
                    role = fileSnapshot.child("ROLE").getValue(String.class);
                    startDate = fileSnapshot.child("START_DATE").getValue(String.class);
                    endDate = fileSnapshot.child("END_DATE").getValue(String.class);
                    character = fileSnapshot.child("CHARACTER").getValue(String.class);
                    pay = fileSnapshot.child("PAY").getValue(String.class);
                    int numbers = (int)fileSnapshot.child("ACT_SAMPLE").getChildrenCount();

                    announceIDS.add(announceID);
                    titleS.add(title);
                    explainCharacterS.add(explainCharacter);
                    scriptS.add(script);
                    roleS.add(role);
                    startDateS.add(startDate);
                    endDateS.add(endDate);
                    characterS.add(character);
                    payS.add(pay);
                    numberS.add(numbers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
