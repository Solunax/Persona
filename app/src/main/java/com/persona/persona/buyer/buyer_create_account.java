package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.persona.persona.R;
import com.persona.persona.login_screen;

public class buyer_create_account extends Activity {
    private Handler mHandler = new Handler();
    private Button create, idcheck, sendphonecode;
    private EditText aID, aPW, aName, aPWCheck, aPhoneNumber, aPhoneCode, aEmail, aRegistration;
    private FirebaseAuth auth;
    private RadioGroup userGroup;
    private CheckBox contract, personaldata;
    private String type = "0";
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_create_account);

        aID = (EditText)findViewById(R.id.buyer_id);
        aPW = (EditText)findViewById(R.id.buyer_pw);
        aName = (EditText)findViewById(R.id.buyer_name);
        aPWCheck = (EditText)findViewById(R.id.buyer_check_pw);
        aPhoneNumber = (EditText)findViewById(R.id.buyer_phone_number);
        aPhoneCode = (EditText)findViewById(R.id.buyer_phone_code);
        aEmail = (EditText)findViewById(R.id.buyer_email);
        aRegistration = (EditText)findViewById(R.id.buyer_registration_number);

        create = (Button) findViewById(R.id.buyer_create_account);
        idcheck = (Button) findViewById(R.id.buyer_id_check);
        sendphonecode = (Button) findViewById(R.id.buyer_send_phone_code);

        userGroup = (RadioGroup)findViewById(R.id.userGroup);
        contract = (CheckBox)findViewById(R.id.buyer_my_contract);
        personaldata = (CheckBox)findViewById(R.id.buyer_using_personal_data);

        auth = FirebaseAuth.getInstance();

        //성별 확인
        userGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.buyer_individual) {
                    type = "I";
                    aRegistration.setVisibility(View.GONE);
                }
                else if (i == R.id.buyer_company){
                    type = "C";
                    aRegistration.setVisibility(View.VISIBLE);
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = aID.getText().toString().trim();
                final String pwd = aPW.getText().toString().trim();
                final String pwdCheck = aPW.getText().toString().trim();
                final String name = aName.getText().toString().trim();
                final String phonenumber = aPhoneNumber.getText().toString().trim();
                final String phonecode = aPhoneCode.getText().toString().trim();
                final String email = aEmail.getText().toString().trim();
                final String registrationNumber = aRegistration.getText().toString().trim();

                //기입란에 공백이 있는지 확인
                if(id.length() == 0){
                    Toast.makeText(buyer_create_account.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    aID.requestFocus();
                    return;
                }
                if(pwd.length() == 0){
                    Toast.makeText(buyer_create_account.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    aPW.requestFocus();
                    return;
                }
                if(pwdCheck.length() == 0){
                    Toast.makeText(buyer_create_account.this, "비밀번호 확인에 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    aPWCheck.requestFocus();
                    return;
                }
                if(!aPW.getText().toString().equals(aPWCheck.getText().toString())){
                    Toast.makeText(buyer_create_account.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    aPWCheck.requestFocus();
                    return;
                }
                if(name.length() == 0){
                    Toast.makeText(buyer_create_account.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    aName.requestFocus();
                    return;
                }
                if(phonenumber.length() == 0){
                    Toast.makeText(buyer_create_account.this, "휴대전화 번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    aPhoneNumber.requestFocus();
                    return;
                }
                if(email.length() == 0){
                    Toast.makeText(buyer_create_account.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    aEmail.requestFocus();
                    return;
                }
                if(type.equals("C")&&aRegistration.length() == 0){
                    Toast.makeText(buyer_create_account.this, "사업자 등록번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    aRegistration.requestFocus();
                    return;
                }
                if(type == "0"){
                    Toast.makeText(buyer_create_account.this, "회원 구분을 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!contract.isChecked()){
                    Toast.makeText(buyer_create_account.this, "이용 약관에 동의해주세요", Toast.LENGTH_SHORT).show();
                    contract.requestFocus();
                    return;
                }
                if(!personaldata.isChecked()){
                    Toast.makeText(buyer_create_account.this, "개인정보 취급방침에 동의해주세요", Toast.LENGTH_SHORT).show();
                    aID.requestFocus();
                    return;
                }

                //회원 정보 클래스
                class UserData{
                    private String uid;
                    private String id;
                    private String pw;
                    private String name;
                    private String type;
                    private String phonenumber;
                    private String email;
                    private String traderRegistrationNumber;

                    // 회원 정보 생성자
                    private UserData(String uid, String id, String pw, String name, String type, String phonenumber, String email){
                        this.uid = uid;
                        this.id = id;
                        this.pw = pw;
                        this.name = name;
                        this.type = type;
                        this.phonenumber = phonenumber;
                        this.email = email;
                    }

                    private UserData(String uid, String id, String pw, String name, String type, String phonenumber, String email, String traderRegistrationNumber){
                        this.uid = uid;
                        this.id = id;
                        this.pw = pw;
                        this.name = name;
                        this.type = type;
                        this.phonenumber = phonenumber;
                        this.email = email;
                        this.traderRegistrationNumber = traderRegistrationNumber;
                    }

                    //회원 정보 기입 메서드
                    public void postUserI(){
                        userDB.child("BUYER_USER").child(uid).child("ID").setValue(id);
                        userDB.child("BUYER_USER").child(uid).child("EMAIL").setValue(email);
                        userDB.child("BUYER_USER").child(uid).child("PW").setValue(pw);
                        userDB.child("BUYER_USER").child(uid).child("NAME").setValue(name);
                        userDB.child("BUYER_USER").child(uid).child("TYPE").setValue(type);
                        userDB.child("BUYER_USER").child(uid).child("EMAIL").setValue(email);
                        userDB.child("BUYER_USER").child(uid).child("PHONE_NUMBER").setValue(phonenumber);
                    }

                    public void postUserC(){
                        userDB.child("BUYER_USER").child(uid).child("ID").setValue(id);
                        userDB.child("BUYER_USER").child(uid).child("EMAIL").setValue(email);
                        userDB.child("BUYER_USER").child(uid).child("PW").setValue(pw);
                        userDB.child("BUYER_USER").child(uid).child("NAME").setValue(name);
                        userDB.child("BUYER_USER").child(uid).child("TYPE").setValue(type);
                        userDB.child("BUYER_USER").child(uid).child("EMAIL").setValue(email);
                        userDB.child("BUYER_USER").child(uid).child("PHONE_NUMBER").setValue(phonenumber);
                        userDB.child("BUYER_USER").child(uid).child("TRADER_REGISTRATION_NUMBER").setValue(traderRegistrationNumber);
                    }
                }

                //회원 생성 메서드
                auth.createUserWithEmailAndPassword(id, pwd).addOnCompleteListener(buyer_create_account.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String uid = auth.getInstance().getUid();
                                    if(type.equals("I")) {
                                        UserData user = new UserData(uid, id, pwd, name, type, phonenumber, email);
                                        user.postUserI();
                                    }else if(type.equals("C")){
                                        UserData user = new UserData(uid, id, pwd, name, type, phonenumber, email, registrationNumber);
                                        user.postUserC();
                                    }
                                    Intent intent = new Intent(buyer_create_account.this, login_screen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        } else {
                            Toast.makeText(buyer_create_account.this, "등록 에러", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
            }
        });
    }
}