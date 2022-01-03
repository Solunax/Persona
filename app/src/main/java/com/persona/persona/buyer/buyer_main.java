package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.persona.persona.R;
import com.persona.persona.matching_state;

public class buyer_main extends Activity {
    Button sampleSearch, makeAnnounce, matching, myInfo;
    private long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(buyer_main.this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_main);

        sampleSearch = (Button)findViewById(R.id.buyer_sample_search);
        makeAnnounce = (Button)findViewById(R.id.buyer_make_announce);
        matching = (Button)findViewById(R.id.buyer_matching_state);
        myInfo = (Button)findViewById(R.id.buyer_my_info);

        sampleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_main.this, buyer_search_voice.class);
                startActivity(intent);
            }
        });

        makeAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_main.this, buyer_announce_main.class);
                startActivity(intent);
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_main.this, matching_state.class);
                intent.putExtra("division", "BUYER");
                startActivity(intent);
            }
        });

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_main.this, buyer_info.class);
                startActivity(intent);
            }
        });
    }
}
