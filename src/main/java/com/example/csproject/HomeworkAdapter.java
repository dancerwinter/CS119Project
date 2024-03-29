package com.example.csproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import javax.annotation.Nonnull;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class HomeworkAdapter extends RealmRecyclerViewAdapter<Homework, HomeworkAdapter.ViewHolder> {

    private ClassDetails context;

    public HomeworkAdapter(@Nullable RealmResults<Homework> data, ClassDetails context) {
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
        private Button done;
        public ViewHolder(@Nonnull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.hwTitle);
            tvDesc = itemView.findViewById(R.id.hwDesc);
            done = itemView.findViewById(R.id.hwDone);

            done.setOnClickListener(deleteListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkAdapter.ViewHolder holder, int position) {
        Homework h = getItem(position);
        holder.tvTitle.setText(h.getTitle());
        holder.tvDesc.setText(h.getDescription());
        holder.done.setTag(h);
    }
    private View.OnClickListener deleteListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Homework c = (Homework) v.getTag();
            context.deleteHW(c);
        }
    };
}
