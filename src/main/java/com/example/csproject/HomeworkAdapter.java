package com.example.csproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import javax.annotation.Nonnull;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class HomeworkAdapter extends RealmRecyclerViewAdapter<Homework, HomeworkAdapter.ViewHolder> {

    private MainActivity context;

    public HomeworkAdapter(@Nullable RealmResults<Homework> data, MainActivity context) {
        super(data, true);
        this.context = context;
    }

    @NonNull
    @Override
    public HomeworkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = context.getLayoutInflater().inflate(R.layout.homework_row, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDesc;

        public ViewHolder(@Nonnull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.hwTitle);
            tvDesc = itemView.findViewById(R.id.hwDesc);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkAdapter.ViewHolder holder, int position) {
        Homework h = getItem(position);
        holder.tvTitle.setText(h.getTitle());
        holder.tvDesc.setText(h.getDescription());
    }
}
