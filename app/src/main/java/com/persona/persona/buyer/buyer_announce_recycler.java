package com.persona.persona.buyer;

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

public class buyer_announce_recycler extends RecyclerView.Adapter<buyer_announce_recycler.ViewHolder>{
    int count = 0;
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
        TextView title, startDate, endDate, role, hDetail, hScript, hStart, hEnd, hCharacter, hPay, nowActor;
        Button moreInfo, seeActor, edit;
        LinearLayout dateLinear, moreDetailLinear, detailLinear, motherLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.buyer_announce_title);
            this.startDate = itemView.findViewById(R.id.buyer_announce_start);
            this.endDate = itemView.findViewById(R.id.buyer_announce_end);
            this.role = itemView.findViewById(R.id.buyer_announce_act);
            this.hDetail = itemView.findViewById(R.id.hidden_character_detail);
            this.hScript = itemView.findViewById(R.id.hidden_script);
            this.hStart = itemView.findViewById(R.id.hidden_start);
            this.hEnd = itemView.findViewById(R.id.hidden_end);
            this.hCharacter = itemView.findViewById(R.id.hidden_character);
            this.hPay = itemView.findViewById(R.id.hidden_pay);
            this.nowActor = itemView.findViewById(R.id.buyer_announce_now_actor);

            this.moreInfo = itemView.findViewById(R.id.buyer_announce_more_info);
            this.seeActor = itemView.findViewById(R.id.hidden_see_actor);
            this.edit = itemView.findViewById(R.id.hidden_edit);

            this.dateLinear = itemView.findViewById(R.id.buyer_announce_date_linear);
            this.moreDetailLinear = itemView.findViewById(R.id.buyer_announce_more_detail_linear);
            this.detailLinear = itemView.findViewById(R.id.buyer_announce_detail_linear);
            this.motherLinear = itemView.findViewById(R.id.buyer_announce_mother_linear);
        }
    }

    buyer_announce_recycler(ArrayList<String> announceIDS, ArrayList<String> titleS, ArrayList<String> explainCharacterS, ArrayList<String> scriptS, ArrayList<String> roleS, ArrayList<String> startDateS, ArrayList<String> endDateS, ArrayList<String> characterS, ArrayList<String> payS, ArrayList<Integer> numberS){
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
    public buyer_announce_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_announce_list_recycler, parent, false);
        buyer_announce_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull buyer_announce_recycler.ViewHolder holder, int position) {
        holder.title.setText(titleS.get(holder.getAdapterPosition()));
        holder.startDate.setText(startDateS.get(holder.getAdapterPosition()));
        holder.endDate.setText(endDateS.get(holder.getAdapterPosition()));
        holder.role.setText(roleS.get(holder.getAdapterPosition()));
        holder.hDetail.setText("캐릭터 특징 " + "(" + roleS.get(holder.getAdapterPosition()) + ")\n" +explainCharacterS.get(holder.getAdapterPosition()));
        holder.hScript.setText("대사\n" + scriptS.get(holder.getAdapterPosition()));
        holder.hStart.setText("기간 : " + startDateS.get(holder.getAdapterPosition()));
        holder.hEnd.setText(endDateS.get(holder.getAdapterPosition()));
        holder.nowActor.setText("현재 지원자 : " + numberS.get(holder.getAdapterPosition()) + "명");
        holder.hCharacter.setText("특징 : " + characterS.get(holder.getAdapterPosition()));
        holder.hPay.setText("보수 : " + payS.get(holder.getAdapterPosition()));

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dateLinear.setVisibility(View.GONE);
                holder.role.setVisibility(View.GONE);
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
                holder.detailLinear.setVisibility(View.GONE);
            }
        });

        holder.seeActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, buyer_announce_show_actor.class);
                intent.putExtra("announceID", announceIDS.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, buyer_announce_edit.class);
                intent.putExtra("announceID", announceIDS.get(holder.getAdapterPosition()));
                intent.putExtra("title", titleS.get(holder.getAdapterPosition()));
                intent.putExtra("explain", explainCharacterS.get(holder.getAdapterPosition()));
                intent.putExtra("script", scriptS.get(holder.getAdapterPosition()));
                intent.putExtra("role", roleS.get(holder.getAdapterPosition()));
                intent.putExtra("end", endDateS.get(holder.getAdapterPosition()));
                intent.putExtra("character", characterS.get(holder.getAdapterPosition()));
                intent.putExtra("pay", payS.get(holder.getAdapterPosition()));
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
