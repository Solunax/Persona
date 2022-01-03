package com.persona.persona.voice;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.persona.persona.R;
import java.io.IOException;
import java.util.ArrayList;

public class voice_sample_recycler extends RecyclerView.Adapter<voice_sample_recycler.ViewHolder>{
    int count = 0;
    ArrayList<String> data;
    ArrayList<String> path;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code;
        ImageButton play, stop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.voice_code_name);
            this.play = itemView.findViewById(R.id.voice_upload_check_sample_play);
            this.stop = itemView.findViewById(R.id.voice_upload_check_sample_stop);
        }
    }

    voice_sample_recycler(ArrayList<String> data, ArrayList<String> path){
        if(count == 0){
            this.data = data;
            this.path = path;
            count ++;
        }else{
            this.data.clear();
            this.path.clear();
        }
    }

    @Override
    public voice_sample_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems, parent, false);
        voice_sample_recycler.ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_sample_recycler.ViewHolder holder, int position) {
        MediaPlayer mediaPlayer = new MediaPlayer();

        holder.code.setText(data.get( holder.getAdapterPosition()));
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
