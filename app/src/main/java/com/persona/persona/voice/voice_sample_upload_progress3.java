package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.Nullable;

import com.persona.persona.R;

public class voice_sample_upload_progress3 extends Activity {
    Spinner purposeSpin, genderSpin, ageSpin, languageSpin, characterSpin;
    Button nextStep;
    String purpose, gender, age, language, character, code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_upload_progress3);

        purposeSpin = (Spinner)findViewById(R.id.voice_purpose_spinner);
        genderSpin = (Spinner)findViewById(R.id.voice_gender_spinner);
        ageSpin = (Spinner)findViewById(R.id.voice_age_spinner);
        languageSpin = (Spinner)findViewById(R.id.voice_language_spinner);
        characterSpin = (Spinner)findViewById(R.id.voice_character_spinner);
        nextStep = (Button)findViewById(R.id.voice_upload_progress3_next);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beforeData = getIntent();
                String path = beforeData.getStringExtra("filepath");

                code = purpose + gender + language + age + character;
                Intent intent = new Intent(voice_sample_upload_progress3.this, voice_sample_upload_progress4.class);
                intent.putExtra("code", code);
                intent.putExtra("filepath", path);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> purposeSpinner = ArrayAdapter.createFromResource(voice_sample_upload_progress3.this, R.array.voice_purpose, android.R.layout.simple_spinner_item);
        purposeSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purposeSpin.setAdapter(purposeSpinner);

        purposeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "전부":
                        purpose = "0";
                        break;
                    case "나레이션":
                        purpose = "1";
                        break;
                    case "더빙":
                        purpose = "2";
                        break;
                    case "ARS":
                        purpose = "3";
                        break;
                    case "도서 녹음":
                        purpose = "4";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> genderSpinner = ArrayAdapter.createFromResource(voice_sample_upload_progress3.this, R.array.voice_gender, android.R.layout.simple_spinner_item);
        genderSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpin.setAdapter(genderSpinner);

        genderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "남성":
                        gender = "0";
                        break;
                    case "여성":
                        gender = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> ageSpinner = ArrayAdapter.createFromResource(voice_sample_upload_progress3.this, R.array.voice_age, android.R.layout.simple_spinner_item);
        ageSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpin.setAdapter(ageSpinner);

        ageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "어린이":
                        age = "0";
                        break;
                    case "청소년":
                        age = "1";
                        break;
                    case "청년":
                        age = "2";
                        break;
                    case "중장년":
                        age = "3";
                        break;
                    case "노인":
                        age = "4";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> languageSpinner = ArrayAdapter.createFromResource(voice_sample_upload_progress3.this, R.array.voice_language, android.R.layout.simple_spinner_item);
        languageSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpin.setAdapter(languageSpinner);

        languageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "한국어":
                        language = "0";
                        break;
                    case "영어":
                        language = "1";
                        break;
                    case "일본어":
                        language = "2";
                        break;
                    case "중국어":
                        language = "3";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> characterSpinner = ArrayAdapter.createFromResource(voice_sample_upload_progress3.this, R.array.voice_character, android.R.layout.simple_spinner_item);
        characterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        characterSpin.setAdapter(characterSpinner);

        characterSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "기쁜":
                        character = "0";
                        break;
                    case "슬픈":
                        character = "1";
                        break;
                    case "우울한":
                        character = "2";
                        break;
                    case "겁에질린":
                        character = "3";
                        break;
                    case "나른한":
                        character = "4";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
