package com.blockent.papagoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.papagoapp.R;
import com.blockent.papagoapp.model.History;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<History> historyList;

    public HistoryAdapter(Context context, ArrayList<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);
        return new HistoryAdapter.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.txtText.setText(history.text);
        holder.txtResult.setText(history.result);
        holder.txtTarget.setText(history.target);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtText;
        TextView txtResult;
        TextView txtTarget;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtText);
            txtResult = itemView.findViewById(R.id.txtResult);
            txtTarget = itemView.findViewById(R.id.txtTarget);

        }
    }
}
