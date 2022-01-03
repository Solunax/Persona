package com.persona.persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class change_password extends Activity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private String currentPW;
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private EditText beforePw, newPw, newPwCheck;
    private Button change;
    private String division;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Intent beforeScreenData = getIntent();
        division = beforeScreenData.getStringExtra("division");

        beforePw = (EditText)findViewById(R.id.before_pw);
        newPw = (EditText)findViewById(R.id.new_pw);
        newPwCheck = (EditText)findViewById(R.id.new_pw_check);
        change = (Button)findViewById(R.id.change_password_apply);

        DatabaseReference type = userDB.child(division).child(uid).child("PW");
        type.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s = snapshot.getValue(String.class);
                currentPW = s;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String befPW = beforePw.getText().toString().trim();
                String nePW = newPw.getText().toString().trim();
                String nePWCheck = newPwCheck.getText().toString().trim();

                if(befPW.length() == 0){
                    Toast.makeText(change_password.this, "기존 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    beforePw.requestFocus();
                    return;
                }
                if(nePW.length() == 0){
                    Toast.makeText(change_password.this, "새로운 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    newPw.requestFocus();
                    return;
                }
                if(nePWCheck.length() == 0){
                    Toast.makeText(change_password.this, "비밀번호 확인에 새로운 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    newPwCheck.requestFocus();
                    return;
                }
                if(!(befPW.equals(currentPW))){
                    Toast.makeText(change_password.this, "기존 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    beforePw.requestFocus();
                    return;
                }
                if(!(nePW.equals(nePWCheck))){
                    Toast.makeText(change_password.this, "입력하신 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    newPwCheck.requestFocus();
                    return;
                }
                if(befPW.equals(nePW)){
                    Toast.makeText(change_password.this, "기존에 사용하던 비밀번호로 변경할 수 없습니다", Toast.LENGTH_SHORT).show();
                    newPwCheck.requestFocus();
                    return;
                }

                user.updatePassword(nePW).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            userDB.child(division).child(uid).child("PW").setValue(nePW);
                            Toast.makeText(change_password.this, "비밀번호를 수정하였습니다. 다시 로그인해주세요", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(change_password.this, login_screen.class);
                            startActivity(intent);
                            ActivityCompat.finishAffinity(change_password.this);
                        }
                    }
                });
            }
        });
    }
}
