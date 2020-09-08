package com.example.sqlite;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainListWrapper {
    public ImageView studentImageView;
    public TextView nameView;
    public ImageView contactView;

    // adapter에서 사용할 예정
    public MainListWrapper(View root){
        studentImageView = (ImageView)root.findViewById(R.id.main_item_student_image);
        nameView = root.findViewById(R.id.main_item_name);
        contactView = root.findViewById(R.id.main_item_content);
    }
}
