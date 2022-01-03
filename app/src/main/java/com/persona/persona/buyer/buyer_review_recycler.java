package com.persona.persona.buyer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.persona.persona.R;
import java.util.ArrayList;

public class buyer_review_recycler extends RecyclerView.Adapter<buyer_review_recycler.ViewHolder>{
    private int count = 0;
    private ArrayList<String> matchingID;
    private ArrayList<String> voiceCode;
    private ArrayList<String> actorId;
    private ArrayList<String> matchingDate;
    private ArrayList<String> actorUID;
    private ArrayList<String> compare;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code, id, date;
        Button write, edit, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.buyer_review_codename);
            this.id = itemView.findViewById(R.id.buyer_review_actor_id);
            this.date = itemView.findViewById(R.id.buyer_review_date);
            this.write = itemView.findViewById(R.id.buyer_review_write);
            this.edit = itemView.findViewById(R.id.buyer_review_edit);
            this.delete = itemView.findViewById(R.id.buyer_review_delete);
        }
    }

    buyer_review_recycler(ArrayList<String> matchingID, ArrayList<String> voiceCode, ArrayList<String> actorId, ArrayList<String> matchingDate, ArrayList<String> actorUID, ArrayList<String> compare){
        if(count == 0){
            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.actorId = actorId;
            this.matchingDate = matchingDate;
            this.actorUID = actorUID;
            this.compare = compare;
            count ++;
        }else{
            this.matchingID.clear();
            this.voiceCode.clear();
            this.actorId.clear();
            this.matchingDate.clear();
            this.compare.clear();

            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.actorId = actorId;
            this.matchingDate = matchingDate;
            this.compare = compare;
        }
    }

    @Override
    public buyer_review_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_review_recycler, parent, false);
        buyer_review_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull buyer_review_recycler.ViewHolder holder, int position) {
        String s = voiceCode.get(holder.getAdapterPosition());
        String[] separateCode = s.split("");
        String character = "";

        switch(separateCode[4]){
            case "0":
                character = "기쁜";
                break;
            case "1":
                character = "슬픈";
                break;
            case "2":
                character = "우울한";
                break;
            case "3":
                character = "겁에질린";
                break;
            case "4":
                character = "나른한";
                break;
        }

        if(!compare.get(holder.getAdapterPosition()).equals("N")){
            holder.write.setVisibility(View.GONE);
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }

        holder.code.setText(character);
        holder.id.setText(actorId.get(holder.getAdapterPosition()));
        holder.date.setText(matchingDate.get(holder.getAdapterPosition()));

        holder.write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, buyer_review_write.class);
                intent.putExtra("matchingID", matchingID.get(holder.getAdapterPosition()));
                intent.putExtra("actorUID", actorUID.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, buyer_review_edit.class);
                intent.putExtra("matchingID", matchingID.get(holder.getAdapterPosition()));
                intent.putExtra("actorUID", actorUID.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, buyer_review_delete.class);
                intent.putExtra("matchingID", matchingID.get(holder.getAdapterPosition()));
                intent.putExtra("actorUID", actorUID.get(holder.getAdapterPosition()));
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchingID.size();
    }
}
