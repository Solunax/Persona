package com.persona.persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import com.persona.persona.buyer.buyer_matching_state_complete;
import com.persona.persona.buyer.buyer_matching_state_progress;
import com.persona.persona.voice.voice_matching_state_complete;
import com.persona.persona.voice.voice_matching_state_progress;

public class matching_state extends Activity {
    private String division;
    private ImageButton progress, complete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_state);

        Intent beforeData = getIntent();
        division = beforeData.getStringExtra("division");

        progress = (ImageButton) findViewById(R.id.matching_state_progress);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(division.equals("ACTOR")){
                    Intent intent = new Intent(matching_state.this, voice_matching_state_progress.class);
                    startActivity(intent);
                }else if(division.equals("BUYER")){
                    Intent intent = new Intent(matching_state.this, buyer_matching_state_progress.class);
                    startActivity(intent);
                }
            }
        });

        complete = (ImageButton)findViewById(R.id.matching_state_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(division.equals("ACTOR")){
                    Intent intent = new Intent(matching_state.this, voice_matching_state_complete.class);
                    startActivity(intent);
                }else if(division.equals("BUYER")){
                    Intent intent = new Intent(matching_state.this, buyer_matching_state_complete.class);
                    startActivity(intent);
                }
            }
        });
    }
}