package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;

public class buyer_review_edit extends Activity {
    private Button edit;
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private String actorUID, matchingID;
    private EditText text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_review_edit);

        Intent beforeData = getIntent();
        actorUID = beforeData.getStringExtra("actorUID");
        matchingID = beforeData.getStringExtra("matchingID");

        text = (EditText)findViewById(R.id.buyer_review_edit_contents);
        userDB.child("REVIEW").child(actorUID).child(matchingID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ABC", snapshot.toString());
                text.setText(snapshot.child("CONTENTS").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit = (Button)findViewById(R.id.buyer_review_edit_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDB.child("REVIEW").child(actorUID).child(matchingID).child("CONTENTS").setValue(text.getText().toString());
                Intent intent = new Intent(buyer_review_edit.this, buyer_review_edit_complete.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
