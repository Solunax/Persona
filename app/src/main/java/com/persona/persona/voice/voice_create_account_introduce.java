package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.persona.persona.R;
import com.persona.persona.login_screen;

public class voice_create_account_introduce extends Activity {
    private String uid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private EditText intro, brief;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_create_account_introduce);

        intro = (EditText) findViewById(R.id.voice_create_introduce_text);
        brief = (EditText) findViewById(R.id.voice_create_brief_history_text);

        btn = (Button) findViewById(R.id.voice_create_introduce_complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDB.child("ACTOR_USER").child(uid).child("INTRODUCE").setValue(intro.getText().toString());
                userDB.child("ACTOR_USER").child(uid).child("BRIEF_HISTORY").setValue(brief.getText().toString());

                Intent intent = new Intent(voice_create_account_introduce.this, login_screen.class);
                startActivity(intent);
                ActivityCompat.finishAffinity(voice_create_account_introduce.this);
            }
        });
    }
}
