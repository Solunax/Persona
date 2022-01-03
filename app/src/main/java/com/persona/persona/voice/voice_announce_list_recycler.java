package com.persona.persona.voice;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.persona.persona.R;
import java.util.ArrayList;

public class voice_announce_list_recycler extends RecyclerView.Adapter<voice_announce_list_recycler.ViewHolder>{
    int count = 0;
    int linear = 0;
    ArrayList<String> announceIDS = new ArrayList<>();
    ArrayList<String> titleS = new ArrayList<>();
    ArrayList<String> explainCharacterS = new ArrayList<>();
    ArrayList<String> scriptS = new ArrayList<>();
    ArrayList<String> payS = new ArrayList<>();
    ArrayList<String> characterS = new ArrayList<>();
    ArrayList<String> roleS = new ArrayList<>();
    ArrayList<String> startDateS = new ArrayList<>();
    ArrayList<String> endDateS = new ArrayList<>();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, hDetail, hScript, hStart, hEnd, hCharacter, hPay;
        Button edit;
        LinearLayout detailLinear, motherLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.voice_announce_list_title);
            this.hDetail = itemView.findViewById(R.id.voice_announce_list_hidden_character_detail);
            this.hScript = itemView.findViewById(R.id.voice_announce_list_hidden_script);
            this.hStart = itemView.findViewById(R.id.voice_announce_list_hidden_start);
            this.hEnd = itemView.findViewById(R.id.voice_announce_list_hidden_end);
            this.hCharacter = itemView.findViewById(R.id.voice_announce_list_hidden_character);
            this.hPay = itemView.findViewById(R.id.voice_announce_list_hidden_pay);

            this.edit = itemView.findViewById(R.id.voice_announce_list_edit);

            this.detailLinear = itemView.findViewById(R.id.voice_announce_list_detail_linear);
            this.motherLinear = itemView.findViewById(R.id.voice_announce_list_mother_linear);
        }
    }

    voice_announce_list_recycler(ArrayList<String> announceIDS, ArrayList<String> titleS, ArrayList<String> explainCharacterS, ArrayList<String> scriptS, ArrayList<String> roleS, ArrayList<String> startDateS, ArrayList<String> endDateS, ArrayList<String> characterS, ArrayList<String> payS){
        if(true){
            this.announceIDS = announceIDS;
            this.titleS = titleS;
            this.explainCharacterS = explainCharacterS;
            this.scriptS = scriptS;
            this.roleS = roleS;
            this.startDateS = startDateS;
            this.endDateS = endDateS;
            this.characterS = characterS;
            this.payS = payS;
            count ++;
        }else{
            this.announceIDS.clear();
            this.titleS.clear();
            this.explainCharacterS.clear();
            this.scriptS.clear();
            this.roleS.clear();
            this.startDateS.clear();
            this.endDateS.clear();
            this.characterS.clear();
            this.payS.clear();

            this.announceIDS = announceIDS;
            this.titleS = titleS;
            this.explainCharacterS = explainCharacterS;
            this.scriptS = scriptS;
            this.roleS = roleS;
            this.startDateS = startDateS;
            this.endDateS = endDateS;
            this.characterS = characterS;
            this.payS = payS;
        }
    }

    @Override
    public voice_announce_list_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_announce_list_recycler, parent, false);
        voice_announce_list_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_announce_list_recycler.ViewHolder holder, int position) {
        holder.title.setText(titleS.get(holder.getAdapterPosition()));
        holder.hDetail.setText("캐릭터 특징 " + "(" + roleS.get(holder.getAdapterPosition()) + ")\n" +explainCharacterS.get(holder.getAdapterPosition()));
        holder.hScript.setText("대사\n" + scriptS.get(holder.getAdapterPosition()));
        holder.hStart.setText("기간 : " + startDateS.get(holder.getAdapterPosition()));
        holder.hEnd.setText(endDateS.get(holder.getAdapterPosition()));
        holder.hCharacter.setText("특징 : " + characterS.get(holder.getAdapterPosition()));
        holder.hPay.setText("보수 : " + payS.get(holder.getAdapterPosition()));

        holder.motherLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linear == 0){
                    holder.detailLinear.setVisibility(View.VISIBLE);
                    linear = 1;
                }else{
                    holder.detailLinear.setVisibility(View.GONE);
                    linear = 0;
                }


            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, voice_announce_sample_edit.class);
                intent.putExtra("announceID", announceIDS.get(holder.getAdapterPosition()));
                intent.putExtra("script", scriptS.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return announceIDS.size();
    }
}
