package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;

public class buyer_review_delete extends Activity {
    private Button delete;
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private String actorUID, matchingID;
    private EditText text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_review_delete);

        Intent beforeData = getIntent();
        actorUID = beforeData.getStringExtra("actorUID");
        matchingID = beforeData.getStringExtra("matchingID");

        text = (EditText)findViewById(R.id.buyer_review_delete_contents);
        userDB.child("REVIEW").child(actorUID).child(matchingID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                text.setText(snapshot.child("CONTENTS").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        delete = (Button)findViewById(R.id.buyer_review_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDB.child("REVIEW").child(actorUID).child(matchingID).child("BUYER_UID").setValue(null);
                userDB.child("REVIEW").child(actorUID).child(matchingID).child("CONTENTS").setValue(null);
                Intent intent = new Intent(buyer_review_delete.this, buyer_review_delete_complete.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
