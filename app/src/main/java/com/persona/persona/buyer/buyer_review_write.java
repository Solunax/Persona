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

public class buyer_review_write extends Activity {
    private Button write;
    private RatingBar dateRating, actRating, contractRating;
    private int dateValue = 2, actValue = 2, contractValue = 1;
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private String actorUID, matchingID;
    private String uid = FirebaseAuth.getInstance().getUid();
    private Boolean existRating;
    private EditText text;
    private int count = 0, sum = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_review_write);

        Intent beforeData = getIntent();
        actorUID = beforeData.getStringExtra("actorUID");
        matchingID = beforeData.getStringExtra("matchingID");

        text = (EditText)findViewById(R.id.buyer_review_contents);

        write = (Button)findViewById(R.id.buyer_review_write_button);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ratingSum = dateValue + actValue + contractValue;
                userDB.child("REVIEW").child(actorUID).child(matchingID).child("ACTOR_UID").setValue(actorUID);
                userDB.child("REVIEW").child(actorUID).child(matchingID).child("BUYER_UID").setValue(uid);
                userDB.child("REVIEW").child(actorUID).child(matchingID).child("CONTENTS").setValue(text.getText().toString());

                if(existRating == false)
                    userDB.child("REVIEW").child(actorUID).child(matchingID).child("RATING").setValue(ratingSum);

                userDB.child("REVIEW").child(actorUID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot fileSnapshot : snapshot.getChildren()){
                            if(fileSnapshot.child("RATING") != null){
                                count++;
                                sum += fileSnapshot.child("RATING").getValue(Integer.class);
                            }
                        }
                        double s = sum;
                        double value = s/count;
                        Log.d("ABC", String.valueOf(count));
                        Log.d("ABC", String.valueOf(s));
                        Log.d("ABC", String.valueOf(value));
                        userDB.child("ACTOR_USER").child(actorUID).child("RATING_AVERAGE").setValue(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(buyer_review_write.this, buyer_review_write_complete.class);
                startActivity(intent);
                finish();
            }
        });

        dateRating = (RatingBar)findViewById(R.id.buyer_review_date_rating);
        dateRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                dateValue = (int)v;
            }
        });

        actRating = (RatingBar)findViewById(R.id.buyer_review_act_rating);
        actRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                actValue = (int)v;
            }
        });

        contractRating =(RatingBar)findViewById(R.id.buyer_review_contract_rating);
        contractRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                contractValue = (int)v;
            }
        });

        userDB.child("REVIEW").child(actorUID).child(matchingID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                existRating = snapshot.child("RATING").exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
