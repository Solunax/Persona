package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.persona.persona.R;

import java.io.File;
import java.io.IOException;

public class voice_sample_edit_character_check extends Activity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private String beforeCode, newCode;
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private TextView purposeCheck, genderCheck, languageCheck, ageCheck, characterCheck;
    private Button next, back;
    private String purpose, gender, language, age, character, id, name;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_edit_character_check);

        Intent beforeScreenData = getIntent();
        beforeCode = beforeScreenData.getStringExtra("beforeCode");
        newCode = beforeScreenData.getStringExtra("newCode");
        Log.d("NEXT", beforeCode + "/" + newCode);

        String[] beforeCodeSep = beforeCode.split("");
        String[] newCodeSep = newCode.split("");

        purposeCheck = (TextView)findViewById(R.id.voice_edit_purpose_check);
        genderCheck = (TextView)findViewById(R.id.voice_edit_gender_check);
        languageCheck = (TextView)findViewById(R.id.voice_edit_language_check);
        ageCheck = (TextView)findViewById(R.id.voice_edit_age_check);
        characterCheck = (TextView)findViewById(R.id.voice_edit_character_check);
        next = (Button)findViewById(R.id.voice_sample_edit_accept);
        back = (Button)findViewById(R.id.voice_sample_edit_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

                StorageReference beforeFile = mStorage.getReference().child(uid).child(beforeCode + ".mp3");
                File localFlie = null;
                try {
                    localFlie = File.createTempFile(beforeCode, ".mp3");
                } catch (IOException e) {
                }
                beforeFile.getFile(localFlie);
                File finalLocalFlie = localFlie;
                beforeFile.getFile(localFlie).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        File local = new File(finalLocalFlie.getAbsoluteFile().toURI());
                        Uri file = Uri.fromFile(local);
                        beforeFile.delete();
                        StorageReference newFile = mStorage.getReference().child(uid).child(newCode + ".mp3");
                        newFile.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                userDB.child("ACT_SAMPLE").child(beforeCode).child(uid).setValue(null);
                                userDB.child("ACT_SAMPLE").child(newCode).child(uid).child("ID").setValue(id);
                                userDB.child("ACT_SAMPLE").child(newCode).child(uid).child("NAME").setValue(name);
                                userDB.child("ACT_SAMPLE").child(newCode).child(uid).child("UID").setValue(uid);
                                newFile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        userDB.child("ACT_SAMPLE").child(newCode).child(uid).child("PATH").setValue(uri.toString());
                                        Intent intent = new Intent(voice_sample_edit_character_check.this, voice_sample_edit_complete.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

        for(int i = 0; i < newCodeSep.length; i++){
            if(!(newCodeSep[i].equals(beforeCodeSep[i]))){
                switch(i){
                    case 0:
                        purposeCheck.setTextColor(Color.rgb(21, 109, 21));
                        break;
                    case 1:
                        genderCheck.setTextColor(Color.rgb(21, 109, 21));
                        break;
                    case 2:
                        languageCheck.setTextColor(Color.rgb(21, 109, 21));
                        break;
                    case 3:
                        ageCheck.setTextColor(Color.rgb(21, 109, 21));
                        break;
                    case 4:
                        characterCheck.setTextColor(Color.rgb(21, 109, 21));
                        break;
                }
            }
        }

        switch(newCodeSep[0]){
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

        switch(newCodeSep[1]){
            case "0":
                gender = "남성";
                break;
            case "1":
                gender = "여성";
                break;
        }

        switch(newCodeSep[2]){
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

        switch(newCodeSep[3]){
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

        switch(newCodeSep[4]){
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
