package com.persona.persona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.persona.persona.buyer.buyer_create_account;
import com.persona.persona.voice.voice_create_account;

public class user_class extends Activity {
    Button voice, buyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_class);

        voice = (Button)findViewById(R.id.voice_actor);
        buyer = (Button)findViewById(R.id.buyer);

        //성우 회원일 경우 성우 회원가입 페이지로
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_class.this, voice_create_account.class);
                startActivity(intent);
            }
        });

        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_class.this, buyer_create_account.class);
                startActivity(intent);
            }
        });
    }
}
