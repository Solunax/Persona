package com.persona.persona.buyer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.persona.persona.R;
import com.persona.persona.voice.voice_sample_upload_progress3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class buyer_make_announce extends Activity {
    private EditText title, explainCharacter, script, date, pay;
    private Button apply;
    private RadioGroup roleGroup;
    private Spinner characterSpin;
    private String character;
    private String role = "0";
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private String uid = FirebaseAuth.getInstance().getUid();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
    Date nowComp, pickDate;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    void updateLabel(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format.toPattern(), Locale.KOREA);
        date.setText(dateFormat.format(myCalendar.getTime()));
        Log.d("ABC", date.getText().toString());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_make_announce);

        title = (EditText)findViewById(R.id.buyer_make_announce_title);
        explainCharacter = (EditText)findViewById(R.id.buyer_make_announce_character);
        script = (EditText)findViewById(R.id.buyer_make_announce_script);
        pay = (EditText)findViewById(R.id.buyer_make_announce_pay);
        roleGroup = (RadioGroup)findViewById(R.id.buyer_make_announce_radio_group);
        characterSpin = (Spinner)findViewById(R.id.buyer_make_announce_character_spinner);

        apply = (Button)findViewById(R.id.buyer_make_announce_button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentTime = Calendar.getInstance().getTime();
                String now = format.format(currentTime);

                try {
                    nowComp = format.parse(now);
                    pickDate = format.parse(date.getText().toString());
                }catch (Exception e){

                }
                int compare = pickDate.compareTo(nowComp);

                if(title.getText().toString().length() == 0){
                    Toast.makeText(buyer_make_announce.this, "공고 제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(explainCharacter.getText().toString().length() == 0){
                    Toast.makeText(buyer_make_announce.this, "캐릭터 설명을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(date.getText().toString().length() == 0){
                    Toast.makeText(buyer_make_announce.this, "종료 기간을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(compare == 0 || compare < 0){
                    Toast.makeText(buyer_make_announce.this, "공고 종료 기간을 잘못 기입했습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(script.getText().toString().length() == 0){
                    Toast.makeText(buyer_make_announce.this, "대사를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pay.getText().toString().length() == 0){
                    Toast.makeText(buyer_make_announce.this, "보수를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(role.equals("0")){
                    Toast.makeText(buyer_make_announce.this, "주연 조연 구분을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                String dbTitle = title.getText().toString().trim();
                String dbExplainCharacter = explainCharacter.getText().toString();
                String dbScript = script.getText().toString();
                String dbPay = pay.getText().toString().trim();
                String dbCharacter = character;
                String dbRole = role;
                String dbEndDate = date.getText().toString();

                DatabaseReference pushedPostRef = userDB.push();
                String postId = pushedPostRef.getKey();
                userDB.child("ANNOUNCE").child(postId).child("UID").setValue(uid);
                userDB.child("ANNOUNCE").child(postId).child("TITLE").setValue(dbTitle);
                userDB.child("ANNOUNCE").child(postId).child("EXPLAIN").setValue(dbExplainCharacter);
                userDB.child("ANNOUNCE").child(postId).child("SCRIPT").setValue(dbScript);
                userDB.child("ANNOUNCE").child(postId).child("PAY").setValue(dbPay);
                userDB.child("ANNOUNCE").child(postId).child("ROLE").setValue(dbRole);
                userDB.child("ANNOUNCE").child(postId).child("CHARACTER").setValue(dbCharacter);
                userDB.child("ANNOUNCE").child(postId).child("START_DATE").setValue(now);
                userDB.child("ANNOUNCE").child(postId).child("END_DATE").setValue(dbEndDate);

                Intent intent = new Intent(buyer_make_announce.this, buyer_announce_main.class);
                startActivity(intent);
                finish();
            }
        });


        date = (EditText)findViewById(R.id.buyer_make_announce_end);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(buyer_make_announce.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        roleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.buyer_make_announce_main_character) {
                    role = "주연";
                }
                else if (i == R.id.buyer_make_announce_sub_character)
                    role = "조연";
            }
        });


        ArrayAdapter<CharSequence> characterSpinner = ArrayAdapter.createFromResource(buyer_make_announce.this, R.array.voice_character, android.R.layout.simple_spinner_item);
        characterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        characterSpin.setAdapter(characterSpinner);

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
