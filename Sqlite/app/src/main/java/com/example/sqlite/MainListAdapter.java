package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainListAdapter extends ArrayAdapter<StudentVO> {
    Context context;
    ArrayList<StudentVO> data;
    int resId;              // selected item id

    public MainListAdapter(@NonNull Context context, int resId, ArrayList<StudentVO> data) {
        super(context, resId);
        this.context = context;
        this.data = data;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public StudentVO getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);

            MainListWrapper wrapper = new MainListWrapper(convertView);
            convertView.setTag(wrapper);
        }
        MainListWrapper wrapper = (MainListWrapper)convertView.getTag();
        ImageView studentImageView = wrapper.studentImageView;
        TextView nameView = wrapper.nameView;
        final ImageView contactView = wrapper.contactView;

        final StudentVO vo = data.get(position);
        nameView.setText(vo.name);

        // 여기에 복잡한 로직이 들어 갈 수 있음

        return convertView;
    }
}
