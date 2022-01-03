package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.persona.persona.R;

public class voice_announce_upload_sample_done extends Activity {
    private Button done;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_announce_upload_sample_done);

        done = (Button)findViewById(R.id.voice_announce_upload_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_announce_upload_sample_done.this, voice_announce_search_main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
