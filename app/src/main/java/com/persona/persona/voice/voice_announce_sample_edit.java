package com.persona.persona.voice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.persona.persona.R;

import java.io.IOException;

public class voice_announce_sample_edit extends Activity {
    private Button sampleUpload, showScript, nextStep;
    private ImageButton play, stop;
    private String announceID, script;
    private String uid = FirebaseAuth.getInstance().getUid();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private Uri file;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mediaPlayer.stop();
            storageRef.child(uid).child(announceID + "_edit.mp3").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            });
        } catch (Exception e) {
            finish();
        } finally {
            mediaPlayer.release();
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_announce_sample_edit);

        Intent beforeData = getIntent();
        announceID = beforeData.getStringExtra("announceID");
        script = beforeData.getStringExtra("script");

        showScript = (Button)findViewById(R.id.voice_announce_edit_show_script) ;
        showScript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder scriptDialog = new AlertDialog.Builder(voice_announce_sample_edit.this);
                scriptDialog.setTitle("대사");
                scriptDialog.setMessage(script);
                scriptDialog.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                scriptDialog.show();
            }
        });

        nextStep = (Button) findViewById(R.id.voice_announce_edit_next);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    storageRef.child(uid).child(announceID + "_edit.mp3").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });

                    storageRef.child(uid).child(announceID + ".mp3").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    });

                    mediaPlayer.release();
                    mediaPlayer.stop();
                } catch (Exception e) {

                } finally {
                    StorageReference riverRef = storageRef.child(uid).child(announceID + ".mp3");
                    UploadTask uploadTask = riverRef.putFile(file);
                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                storageRef.child(uid).child(announceID + ".mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        userDB.child("ANNOUNCE").child(announceID).child("ACT_SAMPLE").child(uid).child("SAMPLE_PATH").setValue(uri.toString());
                                        Intent intent = new Intent(voice_announce_sample_edit.this, voice_announce_search_main.class);
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

        sampleUpload = (Button) findViewById(R.id.voice_announce_sample_edit_choice);
        sampleUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent, "Select Audio"), 1);
            }
        });

        play = (ImageButton) findViewById(R.id.voice_announce_sample_edit_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageRef.child(uid).child(announceID + "_edit.mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            String path = uri.toString();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(path);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IOException e) {

                        } catch (NullPointerException e) {
                            Toast.makeText(voice_announce_sample_edit.this, "업로드된 연기샘플이 없습니다", Toast.LENGTH_SHORT).show();
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

        stop = (ImageButton) findViewById(R.id.voice_announce_sample_edit_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                mediaPlayer.reset();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            try {
                file = data.getData();
                StorageReference riverRef = storageRef.child(uid).child(announceID + "_edit.mp3");
                UploadTask uploadTask = riverRef.putFile(file);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    //업로드 실패시
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(voice_announce_sample_edit.this, "연기 샘플이 정상적으로 업로드되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(voice_announce_sample_edit.this, "연기 샘플이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                        nextStep.setEnabled(true);
                    }
                });
            } catch (NullPointerException e) {
                Intent intent = new Intent(voice_announce_sample_edit.this, voice_announce_upload_sample.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(voice_announce_sample_edit.this, voice_announce_upload_sample.class);
            startActivity(intent);
            finish();
        }
    }
}