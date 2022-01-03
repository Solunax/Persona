package com.persona.persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.persona.persona.R;

public class withdrawal extends Activity {
    TextView title, script;
    Button yes, no, toLogin;
    EditText passwordCheck ;
    LinearLayout buttonGroup;
    String pw, division;
    ImageView face, warning;

    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal);

        buttonGroup = (LinearLayout)findViewById(R.id.withdrawal_button_group);
        face = (ImageView)findViewById(R.id.withdrawal_face);
        warning = (ImageView)findViewById(R.id.withdrawal_warning);
        title = (TextView)findViewById(R.id.withdrawal_text);
        script = (TextView)findViewById(R.id.withdrawal_script);

        Intent beforeData = getIntent();
        division = beforeData.getStringExtra("division");

        DatabaseReference userInfo = userDB.child(division).child(uid);
        userInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pw = snapshot.child("PW").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        passwordCheck = (EditText) findViewById(R.id.withdrawal_pw) ;

        no = (Button) findViewById(R.id.withdrawal_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toLogin = (Button)findViewById(R.id.withdrawal_to_login);
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(withdrawal.this, login_screen.class);
                startActivity(intent);

            }
        });

        yes = (Button) findViewById(R.id.withdrawal_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = passwordCheck.getText().toString().trim();
                if(input.equals(pw)){
                    if(division.equals("ACTOR_USER")){
                        userDB.child("ACTOR_USER").child(uid).setValue(null);

                        StorageReference storageRef = mStorage.getReference();
                        storageRef.child(uid).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                          @Override
                          public void onSuccess(ListResult listResult) {
                              for (StorageReference prefix : listResult.getItems()) {
                                  StorageReference deleteRef = storageRef.child(uid + "/" + prefix.getName());
                                  deleteRef.delete();
                              }
                          }
                        });

                        DatabaseReference deleteSample = userDB.child("ACT_SAMPLE");
                        deleteSample.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot fileSnapshot : snapshot.getChildren()) {
                                    String codes = fileSnapshot.getKey();
                                    try {
                                        deleteSample.child(codes).child(uid).setValue(null);

                                    }catch (Exception e){

                                    }
                                }
                                user.delete();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else if(division.equals("BUYER_USER")) {
                        userDB.child("BUYER_USER").child(uid).setValue(null);
                        DatabaseReference deleteAnnounce = userDB.child("ANNOUNCE");
                        deleteAnnounce.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                                    String announceID = fileSnapshot.getKey();
                                    String ownerID = fileSnapshot.child("UID").getValue(String.class);

                                    if (uid.equals(ownerID)) {
                                        deleteAnnounce.child(announceID).setValue(null);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        user.delete();
                    }
                    deleteFinish();
                }else{
                    Toast.makeText(withdrawal.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void deleteFinish(){
        passwordCheck.setVisibility(View.GONE);
        buttonGroup.setVisibility(View.GONE);
        toLogin.setVisibility(View.VISIBLE);
        face.setImageResource(R.drawable.withdrawal_done);
        warning.setVisibility(View.GONE);
        title.setText("회원탈퇴 되었습니다.");
        script.setText("다음에도 이용해주세요!");
    }
}
