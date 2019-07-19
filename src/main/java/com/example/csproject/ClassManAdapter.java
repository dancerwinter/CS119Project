package com.example.csproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ClassManAdapter extends RealmRecyclerViewAdapter<Class, ClassManAdapter.ViewHolder> {
    private ManageSubjects context;
    public ClassManAdapter(@Nullable RealmResults<Class> data, ManageSubjects context) {
        super(data, true);
        this.context = context;
    }

    @NonNull
    @Override
    public ClassManAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = context.getLayoutInflater().inflate(R.layout.class_edit, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassManAdapter.ViewHolder holder, int position) {
        Class c = getItem(position);
        holder.subjectName.setText(c.getSubject());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView subjectName;
        public ViewHolder(@NonNull View itemView){
            super (itemView);
            subjectName = itemView.findViewById(R.id.textView);
        }
    }
}
