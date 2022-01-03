package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.persona.persona.R;

public class buyer_announce_voice_info extends Activity {
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private ImageView voiceProfileImage;
    private String uid, name;
    private TextView voiceName, voiceIntroduce, voiceBriefHistory;
    private Button matchingRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_announce_voice_info);

        Intent beforeData = getIntent();
        uid = beforeData.getStringExtra("uid");
        name = beforeData.getStringExtra("name");

        loadData();

        voiceName = (TextView)findViewById(R.id.buyer_announce_voice_name);
        voiceName.setText(name);

        voiceIntroduce = (TextView)findViewById(R.id.buyer_announce_introduce);
        voiceBriefHistory = (TextView)findViewById(R.id.buyer_announce_brief_history);

        voiceProfileImage = (ImageView)findViewById(R.id.buyer_announce_voice_profile_image);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //서버 저장소에서 해당 경로의 프로필 사진파일을 찾아 앱에 이미지 삽입
        storageRef.child(uid).child("profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Glide.with(getApplicationContext()).load(uri).transform(new CenterCrop(), new RoundedCorners(20)).into(profile_btn);
                Glide.with(getApplicationContext()).load(uri).circleCrop().into(voiceProfileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Glide.with(getApplicationContext()).load(R.drawable.no_profile).circleCrop().into(voiceProfileImage);
            }
        });

        matchingRequest = (Button)findViewById(R.id.buyer_announce_voice_info_matching_request);
        matchingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_announce_voice_info.this, buyer_announce_matching.class);
                intent.putExtra("name", name);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        userDB.child("ACTOR_USER").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String introduce = snapshot.child("INTRODUCE").getValue(String.class);
                String brief = snapshot.child("BRIEF_HISTORY").getValue(String.class);
                voiceIntroduce.setText(introduce);
                voiceBriefHistory.setText(brief);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
