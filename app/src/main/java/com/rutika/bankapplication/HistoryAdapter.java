package com.rutika.bankapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private ArrayList<HistoryModal> ArrayList;
    private Context context;

    public HistoryAdapter(ArrayList<HistoryModal> ArrayList, Context context) {
        this.ArrayList =ArrayList;
        this.context = context;
    }
    public void filterList(ArrayList<HistoryModal> filterllist) {

        ArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        HistoryModal modal = ArrayList.get(position);
        holder.senderName.setText(modal.getSender());
        holder.receiverName.setText(modal.getReceiver());
        holder.AmtTrans.setText("Transferred Amount: "+String.valueOf(modal.getAmount()));
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return ArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView senderName,receiverName,AmtTrans;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            senderName = itemView.findViewById(R.id.idSender);
            receiverName = itemView.findViewById(R.id.idReceiver);
            AmtTrans = itemView.findViewById(R.id.Amt_Transfered);
        }
    }
}

