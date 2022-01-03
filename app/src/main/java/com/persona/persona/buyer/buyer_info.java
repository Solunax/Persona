package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;
import com.persona.persona.change_password;
import com.persona.persona.withdrawal;

public class buyer_info extends Activity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();

    TextView id, name;
    EditText phoneNumber, email;
    Button changePhonenumber, changeEmail, changePassword, changeInfo, withDrawal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_info);

        id = (TextView)findViewById(R.id.buyer_checkID);
        name = (TextView)findViewById(R.id.buyer_checkName);
        email = (EditText) findViewById(R.id.buyer_check_email);
        phoneNumber = (EditText) findViewById(R.id.buyer_check_phone);

        DatabaseReference userinfoDB = userDB.child("BUYER_USER").child(uid);
        userinfoDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String idS = snapshot.child("ID").getValue(String.class);
                String emailS = snapshot.child("EMAIL").getValue(String.class);
                String nameS = snapshot.child("NAME").getValue(String.class);
                String phoneS = snapshot.child("PHONE_NUMBER").getValue(String.class);
                id.setText("" + idS);
                email.setText("" + emailS);
                name.setText("" + nameS);
                phoneNumber.setText("" + phoneS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        withDrawal =(Button)findViewById(R.id.buyer_withdrawal);
        withDrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_info.this, withdrawal.class);
                intent.putExtra("division", "BUYER_USER");
                startActivity(intent);
            }
        });

        changeInfo = (Button)findViewById(R.id.buyer_change_info);
        changeInfo.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if(count == 0){
                    count++;
                    changeEmail.setVisibility(View.VISIBLE);
                    changePhonenumber.setVisibility(View.VISIBLE);
                    changePassword.setVisibility(View.VISIBLE);
                    changeEmail.setEnabled(true);
                    changePhonenumber.setEnabled(true);
                    changePassword.setEnabled(true);
                    changeInfo.setText("수정완료");

                }else if(count == 1){
                    count = 0;
                    changeEmail.setVisibility(View.INVISIBLE);
                    changePhonenumber.setVisibility(View.INVISIBLE);
                    changePassword.setVisibility(View.INVISIBLE);
                    changeEmail.setEnabled(false);
                    changePhonenumber.setEnabled(false);
                    changePassword.setEnabled(false);
                    email.setEnabled(false);
                    phoneNumber.setEnabled(false);
                    changeInfo.setText("수정");
                }
            }
        });

        changeEmail = (Button)findViewById(R.id.buyer_change_email);
        changeEmail.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if(count == 0){
                    email.setEnabled(true);
                    count++;
                    changeEmail.setText("확인");
                    email.requestFocus();
                }else if(count == 1){
                    String emailC = email.getText().toString();
                    count = 0;
                    userDB.child("BUYER_USER").child(uid).child("EMAIL").setValue(emailC);
                    changeEmail.setText("이메일 주소 변경");
                    email.setEnabled(false);
                }
            }
        });

        changePhonenumber = (Button)findViewById(R.id.buyer_change_phonenumber);
        changePhonenumber.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if(count == 0){
                    phoneNumber.setEnabled(true);
                    count++;
                    changePhonenumber.setText("확인");
                    phoneNumber.requestFocus();
                }else if(count == 1){
                    String tel = phoneNumber.getText().toString();
                    count = 0;
                    userDB.child("BUYER_USER").child(uid).child("PHONE_NUMBER").setValue(tel);
                    changePhonenumber.setText("전화번호 변경");
                    phoneNumber.setEnabled(false);
                }
            }
        });

        changePassword = (Button)findViewById(R.id.buyer_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_info.this, change_password.class);
                intent.putExtra("division", "BUYER_USER");
                startActivity(intent);
            }
        });
    }
}

