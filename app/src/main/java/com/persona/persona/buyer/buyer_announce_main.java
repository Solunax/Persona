package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class buyer_announce_main extends Activity {
    private Button makeAnnounce;
    private String uid = FirebaseAuth.getInstance().getUid();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference();
    private String announceID, title, explainCharacter, script, pay, character, role, startDate, endDate, ownerID;
    private Handler mHandler = new Handler();
    private ArrayList<String> announceIDS  = new ArrayList<>();
    private ArrayList<String> titleS  = new ArrayList<>();
    private ArrayList<String> explainCharacterS  = new ArrayList<>();
    private ArrayList<String> scriptS  = new ArrayList<>();
    private ArrayList<String> payS  = new ArrayList<>();
    private ArrayList<String> characterS  = new ArrayList<>();
    private ArrayList<String> roleS  = new ArrayList<>();
    private ArrayList<String> startDateS  = new ArrayList<>();
    private ArrayList<String> endDateS  = new ArrayList<>();
    private ArrayList<Integer> numberS = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(10);
    private TextView noResult;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_announce_main);

        noResult = (TextView)findViewById(R.id.buyer_announce_no_result);

        recyclerView = (RecyclerView)findViewById(R.id.buyer_announce_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(decoration_height);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new buyer_announce_recycler(announceIDS, titleS, explainCharacterS, scriptS, roleS, startDateS, endDateS, characterS, payS, numberS);
                recyclerView.setAdapter(adapter);

                if(announceIDS.size() == 0){
                    recyclerView.setVisibility(View.GONE);
                    noResult.setVisibility(View.VISIBLE);
                }
            }
        },500);

        makeAnnounce = (Button)findViewById(R.id.buyer_make_announce_button);
        makeAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_announce_main.this, buyer_make_announce.class);
                startActivity(intent);
            }
        });
    }

    void loadData(){
        DatabaseReference announceInfo = userDB.child("ANNOUNCE");
        announceInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                    announceID = fileSnapshot.getKey();
                    ownerID = fileSnapshot.child("UID").getValue(String.class);
                    title = fileSnapshot.child("TITLE").getValue(String.class);
                    explainCharacter = fileSnapshot.child("EXPLAIN").getValue(String.class);
                    script = fileSnapshot.child("SCRIPT").getValue(String.class);
                    role = fileSnapshot.child("ROLE").getValue(String.class);
                    startDate = fileSnapshot.child("START_DATE").getValue(String.class);
                    endDate = fileSnapshot.child("END_DATE").getValue(String.class);
                    character = fileSnapshot.child("CHARACTER").getValue(String.class);
                    pay = fileSnapshot.child("PAY").getValue(String.class);
                    int numbers = (int)fileSnapshot.child("ACT_SAMPLE").getChildrenCount();

                    if (uid.equals(ownerID)) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
