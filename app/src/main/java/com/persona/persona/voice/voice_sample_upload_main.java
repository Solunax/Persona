package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.persona.persona.R;

public class voice_sample_upload_main extends AppCompatActivity {
    private Button uploadSample, editSample, checkSample, deleteSample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_upload_main);

        uploadSample = (Button) findViewById(R.id.voice_upload_sample);
        editSample = (Button)findViewById(R.id.voice_edit_sample);
        checkSample = (Button)findViewById(R.id.voice_check_sample);
        deleteSample = (Button)findViewById(R.id.voice_delete_sample);

        uploadSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_sample_upload_main.this, voice_sample_upload_progress1.class);
                startActivity(intent);
            }
        });

        editSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_sample_upload_main.this, voice_sample_edit.class);
                startActivity(intent);
            }
        });

        checkSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_sample_upload_main.this, voice_sample_check.class);
                startActivity(intent);
            }
        });

        deleteSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_sample_upload_main.this, voice_sample_delete.class);
                startActivity(intent);
            }
        });
    }
}
