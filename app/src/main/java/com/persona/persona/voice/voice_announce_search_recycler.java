package com.persona.persona.voice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.persona.persona.R;
import java.util.ArrayList;

public class voice_announce_search_recycler extends RecyclerView.Adapter<voice_announce_search_recycler.ViewHolder>{
    int count = 0;
    String uid = FirebaseAuth.getInstance().getUid();
    ArrayList<String> announceIDS = new ArrayList<>();
    ArrayList<String> titleS = new ArrayList<>();
    ArrayList<String> explainCharacterS = new ArrayList<>();
    ArrayList<String> scriptS = new ArrayList<>();
    ArrayList<String> payS = new ArrayList<>();
    ArrayList<String> characterS = new ArrayList<>();
    ArrayList<String> roleS = new ArrayList<>();
    ArrayList<String> startDateS = new ArrayList<>();
    ArrayList<String> endDateS = new ArrayList<>();
    ArrayList<Integer> numberS = new ArrayList<>();
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, startDate, endDate, nowActor, pay, role, hDetail, hScript, hStart, hEnd, hCharacter, hPay;
        Button moreInfo, apply;
        LinearLayout dateLinear, moreDetailLinear, detailLinear, motherLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.voice_announce_title);
            this.startDate = itemView.findViewById(R.id.voice_announce_start);
            this.endDate = itemView.findViewById(R.id.voice_announce_end);
            this.pay = itemView.findViewById(R.id.voice_announce_pay);
            this.nowActor = itemView.findViewById(R.id.voice_announce_now_actor);
            this.role = itemView.findViewById(R.id.voice_announce_role);
            this.hDetail = itemView.findViewById(R.id.vhidden_character_detail);
            this.hScript = itemView.findViewById(R.id.vhidden_script);
            this.hStart = itemView.findViewById(R.id.vhidden_start);
            this.hEnd = itemView.findViewById(R.id.vhidden_end);
            this.hCharacter = itemView.findViewById(R.id.vhidden_character);
            this.hPay = itemView.findViewById(R.id.vhidden_pay);

            this.moreInfo = itemView.findViewById(R.id.voice_announce_more_info);
            this.apply = itemView.findViewById(R.id.vhidden_apply);

            this.dateLinear = itemView.findViewById(R.id.voice_announce_date_linear);
            this.moreDetailLinear = itemView.findViewById(R.id.voice_announce_more_detail_linear);
            this.detailLinear = itemView.findViewById(R.id.voice_announce_detail_linear);
            this.motherLinear = itemView.findViewById(R.id.voice_announce_mother_linear);
        }
    }

    voice_announce_search_recycler(ArrayList<String> announceIDS, ArrayList<String> titleS, ArrayList<String> explainCharacterS, ArrayList<String> scriptS, ArrayList<String> roleS, ArrayList<String> startDateS, ArrayList<String> endDateS, ArrayList<String> characterS, ArrayList<String> payS, ArrayList<Integer> numberS){
        if(count == 0){
            this.announceIDS = announceIDS;
            this.titleS = titleS;
            this.explainCharacterS = explainCharacterS;
            this.scriptS = scriptS;
            this.roleS = roleS;
            this.startDateS = startDateS;
            this.endDateS = endDateS;
            this.characterS = characterS;
            this.payS = payS;
            this.numberS = numberS;
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
            this.numberS.clear();

            this.announceIDS = announceIDS;
            this.titleS = titleS;
            this.explainCharacterS = explainCharacterS;
            this.scriptS = scriptS;
            this.roleS = roleS;
            this.startDateS = startDateS;
            this.endDateS = endDateS;
            this.characterS = characterS;
            this.payS = payS;
            this.numberS = numberS;
        }
    }

    @Override
    public voice_announce_search_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_announce_main_recycler, parent, false);
        voice_announce_search_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_announce_search_recycler.ViewHolder holder, int position) {
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();
        userDB.child("ANNOUNCE").child(announceIDS.get(holder.getAdapterPosition())).child("ACT_SAMPLE").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String voice = snapshot.child("UID").getValue(String.class);
                    if(voice != null)
                        holder.apply.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.title.setText(titleS.get(holder.getAdapterPosition()));
        holder.startDate.setText(startDateS.get(holder.getAdapterPosition()));
        holder.pay.setText("보수 : " + payS.get(holder.getAdapterPosition()));
        holder.endDate.setText(endDateS.get(holder.getAdapterPosition()));
        holder.role.setText(roleS.get(holder.getAdapterPosition()));
        holder.hDetail.setText("캐릭터 특징 " + "(" + roleS.get(holder.getAdapterPosition()) + ")\n" +explainCharacterS.get(holder.getAdapterPosition()));
        holder.hScript.setText("대사\n" + scriptS.get(holder.getAdapterPosition()));
        holder.hStart.setText("기간 : " + startDateS.get(holder.getAdapterPosition()));
        holder.hEnd.setText(endDateS.get(holder.getAdapterPosition()));
        holder.hCharacter.setText("특징 : " + characterS.get(holder.getAdapterPosition()));
        holder.hPay.setText("보수 : " + payS.get(holder.getAdapterPosition()));
        holder.nowActor.setText("현재 지원자 : " + numberS.get(holder.getAdapterPosition()) + "명");

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dateLinear.setVisibility(View.GONE);
                holder.pay.setVisibility(View.GONE);
                holder.nowActor.setVisibility(View.GONE);
                holder.moreDetailLinear.setVisibility(View.GONE);
                holder.detailLinear.setVisibility(View.VISIBLE);
            }
        });

        holder.motherLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dateLinear.setVisibility(View.VISIBLE);
                holder.role.setVisibility(View.VISIBLE);
                holder.moreDetailLinear.setVisibility(View.VISIBLE);
                holder.pay.setVisibility(View.VISIBLE);
                holder.nowActor.setVisibility(View.VISIBLE);
                holder.detailLinear.setVisibility(View.GONE);
            }
        });

        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, voice_announce_upload_sample.class);
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
