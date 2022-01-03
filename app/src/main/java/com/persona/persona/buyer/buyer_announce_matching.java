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
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.persona.persona.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class buyer_announce_matching extends Activity {
    private TextView voiceName, voicePhone, voiceEmail;
    private ImageView voiceProfileImage;
    private Button matchingRequest;
    private String uid, name, actorID, buyerID;
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private String buyerUid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_announce_matching);

        Intent beforeData = getIntent();

        uid = beforeData.getStringExtra("uid");
        name = beforeData.getStringExtra("name");


        voiceName = (TextView)findViewById(R.id.buyer_voice_matching_name);
        voicePhone = (TextView)findViewById(R.id.buyer_announce_voice_matching_phone_number);
        voiceEmail = (TextView)findViewById(R.id.buyer_announce_voice_matching_email);
        matchingRequest = (Button)findViewById(R.id.buyer_announce_voice_matching_request_done);

        voiceName.setText(name);

        voiceProfileImage = (ImageView)findViewById(R.id.buyer_announce_voice_matching_profile_image);
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

        userDB.child("ACTOR_USER").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emailS = snapshot.child("EMAIL").getValue(String.class);
                String phoneS = snapshot.child("PHONE_NUMBER").getValue(String.class);
                voiceEmail.setText("" + emailS);
                voicePhone.setText("" + phoneS);
                actorID = snapshot.child("ID").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userDB.child("BUYER_USER").child(buyerUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                buyerID = snapshot.child("ID").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        matchingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
                String now = format.format(currentTime);

                DatabaseReference pushedPostRef = userDB.push();
                String postId = pushedPostRef.getKey();
                userDB.child("MATCHING").child(postId).child("BUYER_UID").setValue(buyerUid);
                userDB.child("MATCHING").child(postId).child("BUYER_ID").setValue(buyerID);
                userDB.child("MATCHING").child(postId).child("ACTOR_UID").setValue(uid);
                userDB.child("MATCHING").child(postId).child("ACTOR_ID").setValue(actorID);
                userDB.child("MATCHING").child(postId).child("ACCEPT").setValue("Y");
                userDB.child("MATCHING").child(postId).child("DATE").setValue(now);
                Intent intent = new Intent(buyer_announce_matching.this, buyer_announce_main.class);
                startActivity(intent);
                ActivityCompat.finishAffinity(buyer_announce_matching.this);

            }
        });
    }
}
