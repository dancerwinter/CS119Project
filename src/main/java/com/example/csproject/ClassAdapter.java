package com.example.csproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ClassAdapter extends RealmRecyclerViewAdapter<Class,ClassAdapter.ViewHolder> {
    private MainActivity context;
    public ClassAdapter(@Nullable RealmResults<Class> data, MainActivity context) {
        super(data, true);
        this.context = context;
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = context.getLayoutInflater().inflate(R.layout.class_row, parent, false);
//        v.setOnClickListener(onClick);
        return new ViewHolder(v);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Class c = (Class) v.getTag();
            ClassDetails_.intent(context)
                    .classUUID(c.getUUID())
                    .start();
        }
    };

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int position) {
        Class c = getItem(position);

        holder.tvSubject.setText(c.getSubject() + " - ");
        holder.tvSection.setText(c.getSection());
        holder.tvTeacher.setText(c.getTeacher());
        holder.tvTime.setText(c.getTimeStart() + " - " + c.getTimeEnd());
        holder.tvRepeat.setText(repeatMaker(c.getMonday(),c.getTuesday(),c.getWednesday(),c.getThursday(),c.getFriday(),c.getSaturday(),c.getSunday()));
        holder.tvLocation.setText(c.getBuilding() + " " + c.getRoom());
        holder.layout.setTag(c);

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSubject;
        private TextView tvSection;
        private TextView tvTime;
        private TextView tvTeacher;
        private TextView tvRepeat;
        private TextView tvLocation;
        private LinearLayout layout;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvSection = itemView.findViewById(R.id.tvSection);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTeacher = itemView.findViewById(R.id.tvTeacher);
            tvRepeat = itemView.findViewById(R.id.tvRepeat);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            layout = itemView.findViewById(R.id.layout);

            layout.setOnClickListener(onClick);

        }
    }

    private String repeatMaker(Boolean mon, Boolean tue, Boolean wed, Boolean thu, Boolean fri, Boolean sat, Boolean sun){
        String result = "";
        if(mon){
            result += "M ";
        }
        if(tue){
            result += "T ";
        }
        if(wed){
            result += "W ";
        }
        if(thu){
            result += "Th ";
        }
        if(fri){
            result += "F ";
        }
        if(sat){
            result += "S ";
        }
        if(sun){
            result += "Sn ";
        }

        return result;
    }

}
