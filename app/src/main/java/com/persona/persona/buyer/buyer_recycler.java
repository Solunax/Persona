package com.persona.persona.buyer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.persona.persona.R;
import java.io.IOException;
import java.util.ArrayList;

public class buyer_recycler extends RecyclerView.Adapter<com.persona.persona.buyer.buyer_recycler.ViewHolder>{
    int count = 0;
    ArrayList<String> name;
    ArrayList<String> character;
    ArrayList<String> path;
    ArrayList<String> uid;
    ArrayList<String> code;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, character;
        LinearLayout motherGroup;
        ImageButton play, stop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.buyer_actor_name);
            this.character = itemView.findViewById(R.id.buyer_actor_character);
            this.motherGroup = itemView.findViewById(R.id.buyer_voice_mother_linear);
            this.play = itemView.findViewById(R.id.buyer_upload_check_sample_play);
            this.stop = itemView.findViewById(R.id.buyer_upload_check_sample_stop);
        }
    }

    buyer_recycler(ArrayList<String> data, ArrayList<String> path, ArrayList<String> character, ArrayList<String> uid, ArrayList<String> code){
        if(count == 0){
            this.name = data;
            this.character = character;
            this.path = path;
            this.uid = uid;
            this.code = code;
            count ++;
        }else{
            this.name.clear();
            this.character.clear();
            this.path.clear();
            this.uid.clear();
            this.code.clear();
        }
    }

    @Override
    public com.persona.persona.buyer.buyer_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_listitem, parent, false);
        com.persona.persona.buyer.buyer_recycler.ViewHolder viewHolder = new com.persona.persona.buyer.buyer_recycler.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.persona.persona.buyer.buyer_recycler.ViewHolder holder, int position) {
        holder.name.setText(name.get(holder.getAdapterPosition()));
        holder.character.setText(character.get(holder.getAdapterPosition()));

        holder.motherGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, buyer_voice_info.class);
                intent.putExtra("name", name.get(holder.getAdapterPosition()));
                intent.putExtra("uid", uid.get(holder.getAdapterPosition()));
                intent.putExtra("code", code.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });

        MediaPlayer mediaPlayer = new MediaPlayer();

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
                    Toast.makeText(view.getContext(), "샘플을 불러올 수 없습니다", Toast.LENGTH_SHORT).show();
                } catch(Exception e){
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(path.get( holder.getAdapterPosition()));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }catch (Exception a){

                    }
                }
            }
        });

        holder.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}
