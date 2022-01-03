package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.persona.persona.R;
import com.persona.persona.matching_state;

public class voice_main extends Activity {
    Button sampleUpload, announceSearch, matching, myInfo;
    public String voiceUserOnlyId = "AAA";
    private long backKeyPressedTime = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(voice_main.this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_main);

        sampleUpload = (Button)findViewById(R.id.voice_sample_apply);
        announceSearch = (Button)findViewById(R.id.voice_announce_search);
        matching = (Button)findViewById(R.id.voice_matching_state);
        myInfo = (Button)findViewById(R.id.voice_my_info);

        sampleUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_main.this, voice_sample_upload_main.class);
                startActivity(intent);
            }
        });

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_main.this, matching_state.class);
                intent.putExtra("division", "ACTOR");
                startActivity(intent);
            }
        });

        announceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_main.this, voice_announce_search_main.class);
                startActivity(intent);
            }
        });

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_main.this, voice_info.class);
                startActivity(intent);
            }
        });
    }
}
