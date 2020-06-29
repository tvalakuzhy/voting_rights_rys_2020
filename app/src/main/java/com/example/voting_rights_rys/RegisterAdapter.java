package com.example.voting_rights_rys;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {
    String [] reqList;

    public RegisterAdapter (String [] requirements) {
        reqList = requirements;
    }

    @NonNull
    @Override
    public RegisterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.register_list_item, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterAdapter.ViewHolder holder, int position) {
        holder.reqLine.setText(reqList[position]);
    }

    @Override
    public int getItemCount() {
        return reqList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reqLine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reqLine = (TextView)itemView.findViewById(R.id.item_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                reqLine.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            }
        }
    }
}
