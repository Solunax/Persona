package com.persona.persona.voice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.persona.persona.R;
import java.io.IOException;
import java.util.ArrayList;

public class voice_delete_recycler extends RecyclerView.Adapter<voice_delete_recycler.ViewHolder>{
    int count = 0;
    private ArrayList<String> data;
    private ArrayList<String> path;
    private ArrayList<String> numberCode;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code;
        ImageButton play, stop;
        LinearLayout motherGroup, group;
        Button delete;
        boolean flag = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.voice_delete_code_name);
            this.play = itemView.findViewById(R.id.voice_delete_sample_play);
            this.stop = itemView.findViewById(R.id.voice_delete_sample_stop);
            this.motherGroup = itemView.findViewById(R.id.voice_sample_delete_mother_linear);
            this.group = itemView.findViewById(R.id.voice_sample_delete_button_group);
            this.delete = itemView.findViewById(R.id.voice_sample_delete);

            motherGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flag){
                        group.setVisibility(View.VISIBLE);
                        flag = !flag;
                    }else{
                        group.setVisibility(View.GONE);
                        flag = !flag;
                    }

                }
            });
        }
    }


    voice_delete_recycler(ArrayList<String> data, ArrayList<String> path, ArrayList<String> numberCode){
        if(count == 0){
            this.data = data;
            this.path = path;
            this.numberCode = numberCode;
            count ++;
        }else{
            this.data.clear();
            this.path.clear();
            this.numberCode.clear();
            this.data = data;
            this.path = path;
            this.numberCode = numberCode;
        }
    }

    @Override
    public voice_delete_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_delete_recycler, parent, false);
        voice_delete_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_delete_recycler.ViewHolder holder, int position) {
        MediaPlayer mediaPlayer = new MediaPlayer();

        holder.code.setText(data.get(holder.getAdapterPosition()));
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(path.get( holder.getAdapterPosition()));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {

                } catch (NullPointerException e) {
                    Toast.makeText(view.getContext(), "실패", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                mediaPlayer.reset();
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseStorage mStorage = FirebaseStorage.getInstance();
                String code = numberCode.get(holder.getAdapterPosition());
                DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
                userDB.child("ACT_SAMPLE").child(code).child(uid).setValue(null);
                StorageReference deleteFile = mStorage.getReference().child(uid).child(code + ".mp3");
                deleteFile.delete();

                Intent intent = new Intent(context, voice_sample_delete.class);
                ((Activity)context).finish();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
