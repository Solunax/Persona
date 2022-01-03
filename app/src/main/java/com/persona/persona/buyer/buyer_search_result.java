package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

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

public class buyer_search_result extends Activity {
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Handler mHandler = new Handler();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> characterList = new ArrayList<>();
    private ArrayList<String> uidList = new ArrayList<>();
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayList<String> codeList = new ArrayList<>();
    private String code, character;
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(20);
    private TextView title, noResult;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_search_result);

        title = findViewById(R.id.buyer_search_result_title);
        noResult = findViewById(R.id.buyer_search_no_result);

        recyclerView = (RecyclerView)findViewById(R.id.buyer_sample_recycle);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration_height);

        Intent beforeData = getIntent();
        code = beforeData.getStringExtra("code");

        DatabaseReference userDB = database.getReference("ACT_SAMPLE").child(code);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] codes = code.split("");
                switch (codes[4]) {
                    case "0":
                        character = "기쁜";
                        break;
                    case "1":
                        character = "슬픈";
                        break;
                    case "2":
                        character = "우울한";
                        break;
                    case "3":
                        character = "겁에질린";
                        break;
                    case "4":
                        character = "나른한";
                        break;
                }

                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                    String id = fileSnapshot.child("NAME").getValue(String.class);
                    String path = fileSnapshot.child("PATH").getValue(String.class);
                    String uid = fileSnapshot.getKey();
                    nameList.add(id);
                    pathList.add(path);
                    characterList.add(character);
                    uidList.add(uid);
                    codeList.add(code);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(nameList.size() == 0){
                    recyclerView.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    noResult.setVisibility(View.VISIBLE);
                }else{
                    title.setVisibility(View.VISIBLE);
                    adapter = new buyer_recycler(nameList, pathList, characterList, uidList, codeList);
                    recyclerView.setAdapter(adapter);
                }
            }
        },500);

    }
}
