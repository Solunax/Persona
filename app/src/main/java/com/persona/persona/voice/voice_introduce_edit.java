package com.persona.persona.voice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;

public class voice_introduce_edit extends Activity {
    private String uid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private EditText intro, brief;
    private Button btn;
    private String introduce, briefInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_introduce_edit);

        intro = (EditText) findViewById(R.id.voice_introduce_text);
        brief = (EditText) findViewById(R.id.voice_brief_history_text);

        loadData();

        btn = (Button) findViewById(R.id.voice_introduce_edit_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intro.getText().length() == 0){
                    Toast.makeText(voice_introduce_edit.this, "자기소개 내용이 비어있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(brief.getText().length() == 0){
                    Toast.makeText(voice_introduce_edit.this, "약력이 비어있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                userDB.child("ACTOR_USER").child(uid).child("INTRODUCE").setValue(intro.getText().toString());
                userDB.child("ACTOR_USER").child(uid).child("BRIEF_HISTORY").setValue(brief.getText().toString());

                finish();
            }
        });
    }

    void loadData(){
        userDB.child("ACTOR_USER").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                introduce = snapshot.child("INTRODUCE").getValue(String.class);
                briefInfo = snapshot.child("BRIEF_HISTORY").getValue(String.class);

                intro.setText(introduce);
                brief.setText(briefInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
