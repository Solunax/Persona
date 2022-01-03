package com.persona.persona.buyer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.persona.persona.R;
import java.util.ArrayList;

public class buyer_matching_recycler extends RecyclerView.Adapter<buyer_matching_recycler.ViewHolder>{
    int count = 0;
    private ArrayList<String> matchingID;
    private ArrayList<String> voiceCode;
    private ArrayList<String> actorId;
    private ArrayList<String> matchingDate;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code, id, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.buyer_matching_codename);
            this.id = itemView.findViewById(R.id.buyer_matching_actor_id);
            this.date = itemView.findViewById(R.id.buyer_matching_date);
        }
    }

    buyer_matching_recycler(ArrayList<String> matchingID, ArrayList<String> voiceCode, ArrayList<String> actorId, ArrayList<String> matchingDate){
        if(count == 0){
            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.actorId = actorId;
            this.matchingDate = matchingDate;
            count ++;
        }else{
            this.matchingID.clear();
            this.voiceCode.clear();
            this.actorId.clear();
            this.matchingDate.clear();

            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.actorId = actorId;
            this.matchingDate = matchingDate;
        }
    }

    @Override
    public buyer_matching_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_matching_recycler, parent, false);
        buyer_matching_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull buyer_matching_recycler.ViewHolder holder, int position) {
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

        holder.code.setText(character);
        holder.id.setText(actorId.get(holder.getAdapterPosition()));
        holder.date.setText(matchingDate.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return matchingID.size();
    }
}
