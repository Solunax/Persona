package com.persona.persona.voice;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
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
import com.persona.persona.R;
import java.io.IOException;
import java.util.ArrayList;

public class voice_edit_recycler extends RecyclerView.Adapter<voice_edit_recycler.ViewHolder>{
    int count = 0;
    private ArrayList<String> data;
    private ArrayList<String> path;
    private ArrayList<String> numberCode;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code, type;
        ImageButton play, stop;
        LinearLayout motherGroup, group;
        Button editCharacter, editFile;
        boolean flag = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.voice_edit_code_name);
            this.play = itemView.findViewById(R.id.voice_edit_check_sample_play);
            this.stop = itemView.findViewById(R.id.voice_edit_check_sample_stop);
            this.motherGroup = itemView.findViewById(R.id.voice_sample_mother_linear);
            this.group = itemView.findViewById(R.id.voice_sample_edit_button_group);
            this.editCharacter = itemView.findViewById(R.id.voice_edit_character);
            this.editFile = itemView.findViewById(R.id.voice_sample_edit_sample);

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


    voice_edit_recycler(ArrayList<String> data, ArrayList<String> path, ArrayList<String> numberCode){
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
    public voice_edit_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_edit_recycler, parent, false);
        voice_edit_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_edit_recycler.ViewHolder holder, int position) {
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

        holder.editFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, voice_sample_edit_file.class);
                intent.putExtra("code", numberCode.get(holder.getAdapterPosition()));
                Log.d("ABC", numberCode.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        holder.editCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, voice_sample_edit_character.class);
                intent.putExtra("code", numberCode.get(holder.getAdapterPosition()));
                Log.d("ABC", numberCode.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
