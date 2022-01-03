package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.persona.persona.R;

public class voice_sample_upload_progress2 extends Activity {
    Button analyze, self;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_upload_progress2);

        analyze = (Button)findViewById(R.id.voice_sample_analyze);
        self = (Button)findViewById(R.id.voice_self_edit);

        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beforeData = getIntent();
                String path = beforeData.getStringExtra("filepath");

                Intent intent = new Intent(voice_sample_upload_progress2.this, voice_sample_upload_progress3.class);
                intent.putExtra("filepath", path);
                startActivity(intent);
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beforeData = getIntent();
                String path = beforeData.getStringExtra("filepath");

                Intent intent = new Intent(voice_sample_upload_progress2.this, voice_sample_upload_progress3.class);
                intent.putExtra("filepath", path);
                startActivity(intent);
            }
        });

    }
}
