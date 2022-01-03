package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.persona.persona.R;

public class buyer_review_delete_complete extends Activity {
    Button toMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_review_delete_complete);

        toMain = (Button)findViewById(R.id.buyer_review_delete_main);
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(buyer_review_delete_complete.this, buyer_review_main.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
