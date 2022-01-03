package com.persona.persona.voice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.persona.persona.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class voice_matching_progress_recycler extends RecyclerView.Adapter<voice_matching_progress_recycler.ViewHolder>{
    int count = 0;
    private ArrayList<String> matchingID;
    private ArrayList<String> voiceCode;
    private ArrayList<String> buyerId;
    private ArrayList<String> matchingDate;
    Context context;
    private Dialog dialog1, dialog2;
    private int pos;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code, id, date;
        Button yes, no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.code = itemView.findViewById(R.id.voice_matching_progress_codename);
            this.id = itemView.findViewById(R.id.voice_matching_progress_buyer_id);
            this.date = itemView.findViewById(R.id.voice_matching_progress_date);
            this.yes = itemView.findViewById(R.id.voice_matching_progress_yes);
            this.no = itemView.findViewById(R.id.voice_matching_progress_no);
        }
    }


    voice_matching_progress_recycler(ArrayList<String> matchingID, ArrayList<String> voiceCode, ArrayList<String> buyerId, ArrayList<String> matchingDate){
        if(count == 0){
            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.buyerId = buyerId;
            this.matchingDate = matchingDate;
            count ++;
        }else{
            this.matchingID.clear();
            this.voiceCode.clear();
            this.buyerId.clear();
            this.matchingDate.clear();

            this.matchingID = matchingID;
            this.voiceCode = voiceCode;
            this.buyerId = buyerId;
            this.matchingDate = matchingDate;
        }
    }

    @Override
    public voice_matching_progress_recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voice_matching_progress_recycle, parent, false);
        voice_matching_progress_recycler.ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull voice_matching_progress_recycler.ViewHolder holder, int position) {
        dialog1 = new Dialog(context);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.voice_matching_deny_overlay);

        dialog2 = new Dialog(context);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.voice_matching_deny_overlay_complete);

        pos = holder.getAdapterPosition();

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

        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
                String now = format.format(currentTime);
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference userDB = database.getReference("MATCHING").child(matchingID.get(holder.getAdapterPosition()));
                userDB.child("ACCEPT").setValue("Y");
                userDB.child("DATE").setValue(now);

                Intent intent = new Intent(context, voice_matching_state_progress.class);
                ((Activity)context).finish();
                context.startActivity(intent);
            }
        });

        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog1();
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchingID.size();
    }

    void showDialog1(){
        dialog1.setContentView(R.layout.voice_matching_deny_overlay);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();

        Button deny = dialog1.findViewById(R.id.voice_matching_deny);
        Button cancel = dialog1.findViewById(R.id.voice_matching_deny_cancel);

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userDB = database.getReference("MATCHING").child(matchingID.get(pos));
                userDB.setValue(null);

                showDialog2();
                dialog1.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
    }

    void showDialog2(){
        dialog2.setContentView(R.layout.voice_matching_deny_overlay_complete);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();

        Button home = dialog2.findViewById(R.id.voice_matching_deny_to_main);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();

                Intent intent = new Intent(context, voice_matching_state_progress.class);
                ((Activity)context).finish();
                context.startActivity(intent);
            }
        });
    }
}
