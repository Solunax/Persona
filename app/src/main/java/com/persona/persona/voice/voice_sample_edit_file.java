package com.persona.persona.voice;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.persona.persona.R;

import java.io.IOException;

public class voice_sample_edit_file extends Activity {
    private String numberCode;
    private Button sampleUpload, nextStep;
    private ImageButton play, stop;
    private String path;
    private String uid = FirebaseAuth.getInstance().getUid();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            storageRef.child(uid).child("sample.mp3").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            });
        }catch (Exception e){
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_sample_edit_file);

        Intent beforeScreenData = getIntent();
        numberCode = beforeScreenData.getStringExtra("code");
        Log.d("ASD", numberCode);

        nextStep = (Button)findViewById(R.id.voice_sample_edit_file_next);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.stop();
                } catch (Exception e) {

                } finally {
                    storageRef.child(uid).child("sample.mp3").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });
                    Uri uri = Uri.parse(path);
                    Uri file = uri;
                    StorageReference delFile = mStorage.getReference().child(uid).child("sample.mp3");
                    delFile.delete();

                    StorageReference storageRef = mStorage.getReference();
                    StorageReference riverRef = storageRef.child(uid).child(numberCode + ".mp3");
                    UploadTask uploadTask = riverRef.putFile(file);
                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                storageRef.child(uid).child(numberCode + ".mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        userDB.child("ACT_SAMPLE").child(numberCode).child(uid).child("PATH").setValue(uri.toString());
                                        Intent intent = new Intent(voice_sample_edit_file.this, voice_sample_edit_complete.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        sampleUpload = (Button) findViewById(R.id.voice_sample_edit_file_choice);
        sampleUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent, "Select Audio"), 1);
            }
        });

        play = (ImageButton)findViewById(R.id.voice_sample_edit_file_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageRef.child(uid).child("sample.mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            String path = uri.toString();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(path);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {

                        } catch (NullPointerException e){
                            Toast.makeText(voice_sample_edit_file.this, "업로드된 연기샘플이 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "업로드된 연기샘플이 없습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        stop = (ImageButton)findViewById(R.id.voice_sample_edit_file_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                Uri file = data.getData();
                path = file.toString();
                StorageReference riverRef = storageRef.child(uid).child("sample.mp3");
                UploadTask uploadTask = riverRef.putFile(file);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    //업로드 실패시
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(voice_sample_edit_file.this, "연기 샘플이 정상적으로 업로드되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(voice_sample_edit_file.this, "연기 샘플이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                        nextStep.setEnabled(true);
                    }
                });
            } catch(NullPointerException e){
                Intent intent = new Intent(voice_sample_edit_file.this, voice_sample_edit_file.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(voice_sample_edit_file.this, voice_sample_edit_file.class);
            startActivity(intent);
            finish();
        }
    }
}
