package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.persona.persona.R;

public class buyer_announce_edit extends Activity {
    private String uid = FirebaseAuth.getInstance().getUid();
    private Spinner characterSpin;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userDB = database.getReference();
    private String announceID, title, explain, script, role, end, character, pay;
    private EditText ti, ch, sc, en, pa;
    private RadioButton main, sub;
    private Button edit, cancel;
    private int prePosition;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(buyer_announce_edit.this, buyer_announce_main.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_announce_edit);

        Intent beforeData = getIntent();

        announceID = beforeData.getStringExtra("announceID");
        title = beforeData.getStringExtra("title");
        explain = beforeData.getStringExtra("explain");
        script = beforeData.getStringExtra("script");
        role = beforeData.getStringExtra("role");
        end = beforeData.getStringExtra("end");
        character = beforeData.getStringExtra("character");
        pay = beforeData.getStringExtra("pay");

        ti = (EditText)findViewById(R.id.buyer_make_announce_title);
        ch = (EditText)findViewById(R.id.buyer_make_announce_character);
        sc = (EditText)findViewById(R.id.buyer_make_announce_script);
        en = (EditText)findViewById(R.id.buyer_make_announce_end);
        pa = (EditText)findViewById(R.id.buyer_make_announce_pay);

        main = (RadioButton)findViewById(R.id.buyer_edit_announce_main_character);
        sub = (RadioButton)findViewById(R.id.buyer_edit_announce_sub_character);

        cancel = (Button)findViewById(R.id.buyer_edit_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit = (Button)findViewById(R.id.buyer_edit_announce_button);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbTitle = ti.getText().toString().trim();
                String dbExplainCharacter = ch.getText().toString();
                String dbScript = sc.getText().toString();
                String dbCharacter = character;
                String dbPay = pa.getText().toString().trim();

                userDB.child("ANNOUNCE").child(announceID).child("TITLE").setValue(dbTitle);
                userDB.child("ANNOUNCE").child(announceID).child("EXPLAIN").setValue(dbExplainCharacter);
                userDB.child("ANNOUNCE").child(announceID).child("SCRIPT").setValue(dbScript);
                userDB.child("ANNOUNCE").child(announceID).child("CHARACTER").setValue(dbCharacter);
                userDB.child("ANNOUNCE").child(announceID).child("PAY").setValue(dbPay);

                Intent intent = new Intent(buyer_announce_edit.this, buyer_announce_main.class);
                startActivity(intent);
            }
        });

        ti.setText(title);
        ch.setText(explain);
        sc.setText(script);
        en.setText(end);
        pa.setText(pay);

        if(role.equals("주연"))
            main.setChecked(true);
        else
            sub.setChecked(true);

        characterSpin = (Spinner)findViewById(R.id.buyer_make_announce_character_spinner);

        ArrayAdapter<CharSequence> characterSpinner = ArrayAdapter.createFromResource(buyer_announce_edit.this, R.array.voice_character, android.R.layout.simple_spinner_item);
        characterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        characterSpin.setAdapter(characterSpinner);

        switch(character){
            case "기쁜":
                prePosition = 0;
                break;
            case "슬픈":
                prePosition = 1;
                break;
            case "우울한":
                prePosition = 2;
                break;
            case "겁에질린":
                prePosition = 3;
                break;
            case "나른한":
                prePosition = 4;
                break;
        }

        characterSpin.setSelection(prePosition);
        characterSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adapterView.getSelectedItem().toString()){
                    case "기쁜":
                        character = "기쁜";
                        break;
                    case "슬픈":
                        character = "슬픈";
                        break;
                    case "우울한":
                        character = "우울한";
                        break;
                    case "겁에질린":
                        character = "겁에질린";
                        break;
                    case "나른한":
                        character = "나른한";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
