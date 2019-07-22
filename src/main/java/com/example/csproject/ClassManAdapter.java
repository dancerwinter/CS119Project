package com.example.csproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.editClass.setTag(c);
        holder.deleteClass.setTag(c);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView subjectName;
        private Button editClass;
        private Button deleteClass;
        public ViewHolder(@NonNull View itemView){
            super (itemView);
            subjectName = itemView.findViewById(R.id.textView);
            editClass = itemView.findViewById(R.id.button);
            deleteClass = itemView.findViewById(R.id.button2);



            editClass.setOnClickListener(editListener);
            deleteClass.setOnClickListener(deleteListener);
        }
    }
    private View.OnClickListener editListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Class c = (Class) v.getTag();
            context.goToEdit(c);
        }
    };
    private View.OnClickListener deleteListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Class c = (Class) v.getTag();
            context.deleteClass(c);
        }
    };
}
