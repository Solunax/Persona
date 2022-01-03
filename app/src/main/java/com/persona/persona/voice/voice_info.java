package com.persona.persona.voice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.persona.persona.R;
import com.persona.persona.change_password;
import com.persona.persona.withdrawal;
import java.io.InputStream;

public class voice_info extends Activity {
    private final int GALLERY_CODE = 10;
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = user.getUid();
    private String donate;
    private DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
    private TextView id, name;
    private EditText phoneNumber, email;
    private Button changePhonenumber, changeEmail, changePassword, changeInfo, withDrawal, infomationEdit, donationButton;
    private ImageButton profile_btn;
    private Dialog dialog1, dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_info);

        id = (TextView)findViewById(R.id.voice_checkID);
        name = (TextView)findViewById(R.id.voice_checkName);
        email = (EditText) findViewById(R.id.voice_check_email);
        phoneNumber = (EditText)findViewById(R.id.voice_check_phone);

        dialog1 = new Dialog(voice_info.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.voice_donation_activate);

        dialog2 = new Dialog(voice_info.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.voice_donation_deactivate);

        loadData();

        infomationEdit = (Button)findViewById(R.id.voice_information_edit);
        infomationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_info.this, voice_introduce_edit.class);
                startActivity(intent);
            }
        });

        donationButton = (Button)findViewById(R.id.voice_donate_apply);
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(donate.equals("N"))
                    showDialog1();
                else
                    showDialog2();

            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });

        withDrawal =(Button)findViewById(R.id.voice_withdrawal);
        withDrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_info.this, withdrawal.class);
                intent.putExtra("division", "ACTOR_USER");
                startActivity(intent);
            }
        });

        changeInfo = (Button)findViewById(R.id.voice_change_info);
        changeInfo.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if(count == 0){
                    count++;
                    changeEmail.setVisibility(View.VISIBLE);
                    changePhonenumber.setVisibility(View.VISIBLE);
                    changePassword.setVisibility(View.VISIBLE);
                    infomationEdit.setVisibility(View.VISIBLE);
                    withDrawal.setVisibility(View.GONE);
                    changeEmail.setEnabled(true);
                    changePhonenumber.setEnabled(true);
                    changePassword.setEnabled(true);
                    changeInfo.setText("수정완료");

                }else if(count == 1){
                    count = 0;
                    changeEmail.setVisibility(View.INVISIBLE);
                    changePhonenumber.setVisibility(View.INVISIBLE);
                    changePassword.setVisibility(View.INVISIBLE);
                    infomationEdit.setVisibility(View.INVISIBLE);
                    withDrawal.setVisibility(View.VISIBLE);
                    changeEmail.setEnabled(false);
                    changePhonenumber.setEnabled(false);
                    changePassword.setEnabled(false);
                    email.setEnabled(false);
                    phoneNumber.setEnabled(false);
                    changeInfo.setText("수정");
                }
            }
        });


        changeEmail = (Button)findViewById(R.id.voice_change_email);
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
                    userDB.child("ACTOR_USER").child(uid).child("EMAIL").setValue(emailC);
                    changeEmail.setText("이메일 주소 변경");
                    email.setEnabled(false);
                }
            }
        });

        changePhonenumber = (Button)findViewById(R.id.voice_change_phonenumber);
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
                    userDB.child("ACTOR_USER").child(uid).child("PHONE_NUMBER").setValue(tel);
                    changePhonenumber.setText("전화번호 변경");
                    phoneNumber.setEnabled(false);
                }
            }
        });

        changePassword = (Button)findViewById(R.id.voice_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(voice_info.this, change_password.class);
                intent.putExtra("division", "ACTOR_USER");
                startActivity(intent);
            }
        });
    }

    //회원 프로필사진 등록, 수정
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE) {

            try {
                Uri file = data.getData();
                StorageReference storageRef = mStorage.getReference();
                StorageReference riverRef = storageRef.child(uid).child("profile.png");
                UploadTask uploadTask = riverRef.putFile(file);
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    //업로드 실패시
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(voice_info.this, "사진이 정상적으로 업로드되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    //업로드 성공시
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.child(uid).child("profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                                Glide.with(getApplicationContext()).load(uri).transform(new CenterCrop(), new RoundedCorners(20)).into(profile_btn);
                                Glide.with(getApplicationContext()).load(uri).circleCrop().into(profile_btn);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                        // 회원 DB에 프로필사진 경로 주소 추가
                        String path = "gs://persona-5dc0e.appspot.com/"+ uid + "/profile.png";
                        userDB.child("ACTOR_USER").child(uid).child("PROFILE_PATH").setValue(path);
                        Toast.makeText(voice_info.this, "사진이 정상적으로 업로드 되었습니다..", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
            }
        }else{
            Intent intent = new Intent(voice_info.this, voice_info.class);
            startActivity(intent);
        }
    }

    void showDialog1(){
        dialog1.setContentView(R.layout.voice_donation_activate);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();

        Button yes = dialog1.findViewById(R.id.voice_donation_activate);
        Button no = dialog1.findViewById(R.id.voice_donation_activate_cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDB.child("ACTOR_USER").child(uid).child("DONATION").setValue("Y");
                donate = "Y";
                dialog1.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }

    void showDialog2(){
        dialog2.setContentView(R.layout.voice_donation_deactivate);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();

        Button yes = dialog2.findViewById(R.id.voice_donation_deactivate);
        Button no = dialog2.findViewById(R.id.voice_donation_deactivate_cancel);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDB.child("ACTOR_USER").child(uid).child("DONATION").setValue("N");
                donate = "N";
                dialog2.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
    }

    void loadData(){
        //서버 저장소에서 회원 프로필 사진 불러오기
        profile_btn = (ImageButton)findViewById(R.id.profile_image);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //서버 저장소에서 해당 경로의 프로필 사진파일을 찾아 앱에 이미지 삽입
        storageRef.child(uid).child("profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).circleCrop().into(profile_btn);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Glide.with(getApplicationContext()).load(R.drawable.no_profile).circleCrop().into(profile_btn);
            }
        });

        DatabaseReference userinfoDB = userDB.child("ACTOR_USER").child(uid);
        userinfoDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String idS = snapshot.child("ID").getValue(String.class);
                String emailS = snapshot.child("EMAIL").getValue(String.class);
                String nameS = snapshot.child("NAME").getValue(String.class);
                String genderS = snapshot.child("GENDER").getValue(String.class);
                String phoneS = snapshot.child("PHONE_NUMBER").getValue(String.class);
                id.setText("" + idS);
                email.setText("" + emailS);
                name.setText("" + nameS + "(" + genderS + ")");
                phoneNumber.setText("" + phoneS);
                donate = snapshot.child("DONATION").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
