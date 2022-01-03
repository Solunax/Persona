package com.persona.persona.voice;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;
import com.persona.persona.RecyclerDecoration_Height;

import java.util.ArrayList;

public class voice_sample_edit extends Activity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Handler mHandler = new Handler();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String purpose, gender, language, age, character;
    private String filterCode, filterPurpose, filterGender, filterAge, filterLanguage, filterCharacter;
    private ArrayList fileList = new ArrayList<String>();
    private ArrayList pathList = new ArrayList<String>();
    private ArrayList codeList = new ArrayList<String>();
    private Dialog dialog;
    private Button filter;
    private RecyclerDecoration_Height decoration_height = new RecyclerDecoration_Height(20);
    private TextView title, noResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_edit);

        noResult = (TextView)findViewById(R.id.actor_sample_edit_no_result);
        title = (TextView)findViewById(R.id.voice_sample_edit_choice_type);

        dialog = new Dialog(voice_sample_edit.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.voice_sample_filter);

        recyclerView = (RecyclerView)findViewById(R.id.voice_sample_edit_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(decoration_height);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        DatabaseReference userDB = database.getReference("ACT_SAMPLE");
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot fileSnapshot : snapshot.getChildren()) {
                    String codes = fileSnapshot.getKey();
                    String path = fileSnapshot.child(uid).child("PATH").getValue(String.class);
                    if (path != null) {
                        String[] code = codes.split("");
                        switch (code[0]) {
                            case "0":
                                purpose = "전부";
                                break;
                            case "1":
                                purpose = "나레이션";
                                break;
                            case "2":
                                purpose = "더빙";
                                break;
                            case "3":
                                purpose = "ARS";
                                break;
                            case "4":
                                purpose = "도서 녹음";
                                break;
                        }
                        switch (code[1]) {
                            case "0":
                                gender = "남성";
                                break;
                            case "1":
                                gender = "여성";
                                break;
                        }
                        switch (code[2]) {
                            case "0":
                                language = "한국어";
                                break;
                            case "1":
                                language = "영어";
                                break;
                            case "2":
                                language = "일본어";
                                break;
                            case "3":
                                language = "중국어";
                                break;
                        }
                        switch (code[3]) {
                            case "0":
                                age = "어린이";
                                break;
                            case "1":
                                age = "청소년";
                                break;
                            case "2":
                                age = "청년";
                                break;
                            case "3":
                                age = "중장년";
                                break;
                            case "4":
                                age = "노인";
                                break;
                        }
                        switch (code[4]) {
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

                        String showCode = purpose + ", " + gender + ", " + language + ", " + age + ", " + character;
                            fileList.add(showCode);
                            pathList.add(path);
                            codeList.add(codes);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        filter = (Button)findViewById(R.id.voice_sample_edit_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox();
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fileList.size() == 0){
                    recyclerView.setVisibility(View.GONE);
                    filter.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    noResult.setVisibility(View.VISIBLE);
                }else{
                    filter.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    adapter = new voice_edit_recycler(fileList, pathList, codeList);
                    recyclerView.setAdapter(adapter);
                }
            }
        },500);

    }

        public void showDialogBox(){
            dialog.setContentView(R.layout.voice_sample_filter);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            Spinner purposeSpin = (Spinner)dialog.findViewById(R.id.voice_sample_filter_purpose_spinner);
            ArrayAdapter purposeAdapter = ArrayAdapter.createFromResource(this, R.array.voice_purpose, R.layout.support_simple_spinner_dropdown_item);
            purposeSpin.setAdapter(purposeAdapter);
            purposeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(adapterView.getSelectedItem().toString()){
                        case "전부":
                            filterPurpose = "0";
                            break;
                        case "나레이션":
                            filterPurpose = "1";
                            break;
                        case "더빙":
                            filterPurpose = "2";
                            break;
                        case "ARS":
                            filterPurpose = "3";
                            break;
                        case "도서 녹음":
                            filterPurpose = "4";
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Spinner genderSpin = (Spinner)dialog.findViewById(R.id.voice_sample_filter_gender_spinner);
            ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this, R.array.voice_gender, R.layout.support_simple_spinner_dropdown_item);
            genderSpin.setAdapter(genderAdapter);
            genderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(adapterView.getSelectedItem().toString()){
                        case "남성":
                            filterGender = "0";
                            break;
                        case "여성":
                            filterGender = "1";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Spinner ageSpin = (Spinner)dialog.findViewById(R.id.voice_sample_filter_age_spinner);
            ArrayAdapter ageAdapter = ArrayAdapter.createFromResource(this, R.array.voice_age, R.layout.support_simple_spinner_dropdown_item);
            ageSpin.setAdapter(ageAdapter);
            ageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(adapterView.getSelectedItem().toString()){
                        case "어린이":
                            filterAge = "0";
                            break;
                        case "청소년":
                            filterAge = "1";
                            break;
                        case "청년":
                            filterAge = "2";
                            break;
                        case "중장년":
                            filterAge = "3";
                            break;
                        case "노인":
                            filterAge = "4";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Spinner languageSpin = (Spinner)dialog.findViewById(R.id.voice_sample_filter_language_spinner);
            ArrayAdapter languageAdapter = ArrayAdapter.createFromResource(this, R.array.voice_language, R.layout.support_simple_spinner_dropdown_item);
            languageSpin.setAdapter(languageAdapter);
            languageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(adapterView.getSelectedItem().toString()){
                        case "한국어":
                            filterLanguage = "0";
                            break;
                        case "영어":
                            filterLanguage = "1";
                            break;
                        case "일본어":
                            filterLanguage = "2";
                            break;
                        case "중국어":
                            filterLanguage = "3";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Spinner characterSpin = (Spinner)dialog.findViewById(R.id.voice_sample_filter_character_spinner);
            ArrayAdapter characterAdapter = ArrayAdapter.createFromResource(this, R.array.voice_character, R.layout.support_simple_spinner_dropdown_item);
            characterSpin.setAdapter(characterAdapter);
            characterSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch(adapterView.getSelectedItem().toString()){
                        case "기쁜":
                            filterCharacter = "0";
                            break;
                        case "슬픈":
                            filterCharacter = "1";
                            break;
                        case "우울한":
                            filterCharacter = "2";
                            break;
                        case "겁에질린":
                            filterCharacter = "3";
                            break;
                        case "나른한":
                            filterCharacter = "4";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Button yes = dialog.findViewById(R.id.voice_filter_yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fileList.clear();
                    pathList.clear();
                    codeList.clear();
                    filterCode = filterPurpose + filterGender + filterLanguage + filterAge + filterCharacter;
                    DatabaseReference userDB = database.getReference("ACT_SAMPLE").child(filterCode).child(uid);
                    userDB.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String path = snapshot.child("PATH").getValue(String.class);
                            String[] code = filterCode.split("");
                            switch (code[0]) {
                                case "0":
                                    purpose = "전부";
                                    break;
                                case "1":
                                    purpose = "나레이션";
                                    break;
                                case "2":
                                    purpose = "더빙";
                                    break;
                                case "3":
                                    purpose = "ARS";
                                    break;
                                case "4":
                                    purpose = "도서 녹음";
                                    break;
                            }
                            switch (code[1]) {
                                case "0":
                                    gender = "남성";
                                    break;
                                case "1":
                                    gender = "여성";
                                    break;
                            }
                            switch (code[2]) {
                                case "0":
                                    language = "한국어";
                                    break;
                                case "1":
                                    language = "영어";
                                    break;
                                case "2":
                                    language = "일본어";
                                    break;
                                case "3":
                                    language = "중국어";
                                    break;
                            }
                            switch (code[3]) {
                                case "0":
                                    age = "어린이";
                                    break;
                                case "1":
                                    age = "청소년";
                                    break;
                                case "2":
                                    age = "청년";
                                    break;
                                case "3":
                                    age = "중장년";
                                    break;
                                case "4":
                                    age = "노인";
                                    break;
                            }
                            switch (code[4]) {
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
                            String showCode = purpose + ", " + gender + ", " + language + ", " + age + ", " + character;

                            if (path != null) {
                                fileList.add(showCode);
                                pathList.add(path);
                                codeList.add(filterCode);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new voice_edit_recycler(fileList, pathList, codeList);
                            recyclerView.setAdapter(adapter);
                        }
                    },500);

                    dialog.dismiss();
                }
            });

    }
}