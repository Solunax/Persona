package com.persona.persona.voice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.persona.persona.R;
import java.util.ArrayList;

public class voice_review_recycler extends RecyclerView.Adapter<voice_review_recycler.ViewHolder>{
    private int count = 0;
    private int flag = 0;
    private ArrayList<String> matchingID;
    private ArrayList<String> voiceCode;
    private ArrayList<String> buyerId;
    private ArrayList<String> matchingDate;
    private ArrayList<String> contentS;
    private ArrayList<Float> ratingS;
    private
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code, id, date, contents;
        LinearLayout motherLinear, reviewLinear;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.voice_review_codename);
            this.id = itemView.findViewById(R.id.voice_review_buyer_id);
            this.date = itemView.findViewById(R.id.voice_review_date);
            this.contents = itemView.findViewById(R.id.voice_review_contents);

            this.motherLinear = itemView.findViewById(R.id.voice_review_recycle_mother_linear);
            this.reviewLinear = itemView.findViewById(R.id.voice_review_buyer_review_linear);

            this.ratingBar = itemView.findViewById(R.id.voice_review_rating);
        }
    }

    voice_review_recycler(ArrayList<String> matchingID, ArrayList<String> voiceCode, ArrayList<String> buyerId, ArrayList<String> matchingDate, ArrayList<String> contentS, ArrayList<Float> ratingS){
        if(count == 0){
            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.buyerId = buyerId;
            this.matchingDate = matchingDate;
            this.contentS = contentS;
            this.ratingS = ratingS;
            count ++;
        }else{
            this.matchingID.clear();
            this.voiceCode.clear();
            this.buyerId.clear();
            this.matchingDate.clear();
            this.contentS.clear();
            this.ratingS.clear();

            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.buyerId = buyerId;
            this.matchingDate = matchingDate;
            this.contentS = contentS;
            this.ratingS = ratingS;
        }
    }

    @Override
    public voice_review_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_review_recycler, parent, false);
        voice_review_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_review_recycler.ViewHolder holder, int position) {
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
        holder.id.setText(buyerId.get(holder.getAdapterPosition()));
        holder.date.setText(matchingDate.get(holder.getAdapterPosition()));

        holder.contents.setText(contentS.get(holder.getAdapterPosition()));
        holder.ratingBar.setRating(ratingS.get(holder.getAdapterPosition()));

        holder.motherLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0){
                    holder.reviewLinear.setVisibility(View.VISIBLE);
                    flag = 1;
                }else {
                    holder.reviewLinear.setVisibility(View.GONE);
                    flag = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchingID.size();
    }
}
