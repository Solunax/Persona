package com.persona.persona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.buyer.buyer_main;
import com.persona.persona.voice.voice_main;

public class login_screen extends AppCompatActivity {
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private Button login, join;
    private EditText id, pw;
    private TextView findIdPw;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Handler mHandler = new Handler();
    private String forCheck, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        login = (Button) findViewById(R.id.login);
        join = (Button)  findViewById(R.id.createaccount);
        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);
        findIdPw = (TextView) findViewById(R.id.findIDPW);

        findIdPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_screen.this, com.persona.persona.findIdPw.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = id.getText().toString().trim();
                String pwd = pw.getText().toString().trim();
                if(email.length() == 0 || pwd.length() == 0){
                    Toast.makeText(login_screen.this, "아이디나 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(login_screen.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        // 성공 = true -> isSuccessful, 실패 = flase -> else
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                uid = FirebaseAuth.getInstance().getUid();

                                DatabaseReference type = userDB.child("BUYER_USER").child(uid).child("TYPE");
                                type.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String check = snapshot.getValue(String.class);
                                        forCheck = check;
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });

                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try{
                                            if(forCheck.equals("I") || forCheck.equals("C")){
                                                Intent intent = new Intent(login_screen.this, buyer_main.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }catch (NullPointerException e){
                                            Intent intent = new Intent(login_screen.this, voice_main.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }, 500);
                            }else{
                                Toast.makeText(login_screen.this, "등록된 아이디가 없거나, 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_screen.this, user_class.class);
                startActivity(intent);
            }
        });

    }
}