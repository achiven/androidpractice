package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ViewHolder> {

    private ArrayList<StudentVO> mData = null;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView studentImageView;
        public TextView nameView;
        public ImageView contactView;
        public int position = -1;                   // adapter position will be stored.

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentImageView = (ImageView)itemView.findViewById(R.id.main_item_student_image);
            nameView = itemView.findViewById(R.id.main_item_name);
            contactView = itemView.findViewById(R.id.main_item_content);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(position != -1){
                DBHelper helper = new DBHelper(mContext);
                helper.deleteOne(mData.get(position));

                mData.remove(position);
                notifyDataSetChanged();
            }
        }
    }

    RecyclerListAdapter(Context context, ArrayList<StudentVO> list){
        mContext = context;
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        StudentVO vo = mData.get(position);
        holder.nameView.setText(vo.name);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
