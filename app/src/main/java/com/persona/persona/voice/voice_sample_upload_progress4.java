package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.persona.persona.R;

public class voice_sample_upload_progress4 extends Activity {
    private TextView purposeCheck, genderCheck, languageCheck, ageCheck, characterCheck;
    private Button next, back;
    private String purpose, gender, language, age, character, id, name;
    private String uid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_upload_progress4);

        purposeCheck = (TextView)findViewById(R.id.voice_purpose_check);
        genderCheck = (TextView)findViewById(R.id.voice_gender_check);
        languageCheck = (TextView)findViewById(R.id.voice_language_check);
        ageCheck = (TextView)findViewById(R.id.voice_age_check);
        characterCheck = (TextView)findViewById(R.id.voice_character_check);
        next = (Button)findViewById(R.id.voice_sample_upload_progress4_next);
        back = (Button)findViewById(R.id.voice_sample_upload_progress4_back);

        Intent getDataFromBack = getIntent();

        String backCode = getDataFromBack.getStringExtra("code");
        String path = getDataFromBack.getStringExtra("filepath");
        Uri uri = Uri.parse(path);

        String[] code = backCode.split("");

        DatabaseReference userInfo = userDB.child("ACTOR_USER").child(uid);
        userInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String idS = snapshot.child("ID").getValue(String.class);
                String nameS = snapshot.child("NAME").getValue(String.class);
                id = idS;
                name = nameS;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri file = uri;
                StorageReference delFile = mStorage.getReference().child(uid).child("sample.mp3");
                delFile.delete();

                StorageReference storageRef = mStorage.getReference();
                StorageReference riverRef = storageRef.child(uid).child(backCode + ".mp3");
                UploadTask uploadTask = riverRef.putFile(file);
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            storageRef.child(uid).child(backCode + ".mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    userDB.child("ACT_SAMPLE").child(backCode).child(uid).child("ID").setValue(id);
                                    userDB.child("ACT_SAMPLE").child(backCode).child(uid).child("NAME").setValue(name);
                                    userDB.child("ACT_SAMPLE").child(backCode).child(uid).child("UID").setValue(uid);
                                    userDB.child("ACT_SAMPLE").child(backCode).child(uid).child("PATH").setValue(uri.toString());
                                }
                            });
                            Intent intent = new Intent(voice_sample_upload_progress4.this, voice_sample_upload_progress5.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        switch(code[0]){
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

        switch(code[1]){
            case "0":
                gender = "남성";
                break;
            case "1":
                gender = "여성";
                break;
        }

        switch(code[2]){
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

        switch(code[3]){
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

        switch(code[4]){
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

        purposeCheck.setText(purpose);
        genderCheck.setText(gender);
        languageCheck.setText(language);
        ageCheck.setText(age);
        characterCheck.setText(character);
    }
}
