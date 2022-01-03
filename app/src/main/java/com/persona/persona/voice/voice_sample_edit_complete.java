package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.persona.persona.R;

public class voice_sample_edit_complete extends Activity {
    Button done;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_edit_complete);

        done = (Button)findViewById(R.id.voice_edit_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_sample_edit_complete.this, voice_sample_upload_main.class);
                startActivity(intent);
                ActivityCompat.finishAffinity(voice_sample_edit_complete.this);
            }
        });
    }
}
