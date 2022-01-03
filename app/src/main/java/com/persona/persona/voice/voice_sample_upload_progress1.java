package com.persona.persona.voice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.persona.persona.R;
import java.io.IOException;

public class voice_sample_upload_progress1 extends Activity {
    private Button sampleUpload, showScript, nextStep;
    private ImageButton play, stop;
    private String path;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mediaPlayer.stop();
            storageRef.child(uid).child("sample.mp3").delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
        setContentView(R.layout.voice_sample_upload_progress1);

        nextStep = (Button) findViewById(R.id.voice_upload_progress1_next);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.release();
                    mediaPlayer.stop();
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(voice_sample_upload_progress1.this, voice_sample_upload_progress2.class);
                    String putPath = path;
                    intent.putExtra("filepath", putPath);
                    startActivity(intent);
                }
            }
        });

        sampleUpload = (Button) findViewById(R.id.voice_sample_choice);
        sampleUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(Intent.createChooser(intent, "Select Audio"), 1);
            }
        });

        showScript = (Button)findViewById(R.id.voice_show_script);
        showScript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder script = new AlertDialog.Builder(voice_sample_upload_progress1.this);
                script.setTitle("대사");
                script.setMessage("안녕하세요. 잘 부탁드립니다.");
                script.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                script.show();
            }
        });

        play = (ImageButton) findViewById(R.id.voice_upload_progress_sample_play);
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

                        } catch (NullPointerException e) {
                            Toast.makeText(voice_sample_upload_progress1.this, "업로드된 연기샘플이 없습니다", Toast.LENGTH_SHORT).show();
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

        stop = (ImageButton) findViewById(R.id.voice_upload_progress_sample_stop);
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
                Uri file = data.getData();
                path = file.toString();
                StorageReference riverRef = storageRef.child(uid).child("sample.mp3");
                UploadTask uploadTask = riverRef.putFile(file);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    //업로드 실패시
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(voice_sample_upload_progress1.this, "연기 샘플이 정상적으로 업로드되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(voice_sample_upload_progress1.this, "연기 샘플이 정상적으로 업로드 되었습니다.", Toast.LENGTH_SHORT).show();
                        nextStep.setEnabled(true);
                    }
                });
            } catch (NullPointerException e) {
                Intent intent = new Intent(voice_sample_upload_progress1.this, voice_sample_upload_progress1.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(voice_sample_upload_progress1.this, voice_sample_upload_progress1.class);
            startActivity(intent);
            finish();
        }
    }
}