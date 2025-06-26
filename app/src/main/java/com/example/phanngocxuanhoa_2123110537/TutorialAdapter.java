package com.example.phanngocxuanhoa_2123110537;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TutorialAdapter extends ArrayAdapter<String> {

    Context context;
    String[] tutorials;

    int[] images = {
            R.drawable.img_suachuahylap,
            R.drawable.img_pannacotta,
            R.drawable.img_chocolatecake,
            R.drawable.img_cookie,
            R.drawable.img_tiramisu,
            R.drawable.img_mousse,
            R.drawable.img_macaron,
            R.drawable.img_choux,
            R.drawable.img_flan
    };

    String[] descriptions = {
            "Sữa chua Hy Lạp béo ngậy, tốt cho sức khỏe.",
            "Pannacotta vani mềm mịn, thơm lừng.",
            "Bánh kem chocolate đậm đà, quyến rũ.",
            "Bánh quy bơ giòn tan, ngọt ngào.",
            "Tiramisu kiểu Ý với cà phê và phô mai.",
            "Bánh mousse dâu mát lạnh, thanh mát.",
            "Macaron Pháp nhiều màu, nhiều vị.",
            "Su kem ngọt dịu, nhân kem tươi béo.",
            "Bánh flan caramel mịn màng, thơm sữa."
    };

    String[] prices = {
            "35.000đ",
            "40.000đ",
            "45.000đ",
            "25.000đ",
            "50.000đ",
            "42.000đ",
            "38.000đ",
            "30.000đ",
            "28.000đ"
    };

    public TutorialAdapter(@NonNull Context context, String[] tutorials) {
        super(context, R.layout.item_home, tutorials);
        this.context = context;
        this.tutorials = tutorials;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);

        TextView txtItem = itemView.findViewById(R.id.txtItem);
        ImageView imgItem = itemView.findViewById(R.id.imgItem);
        Button btnDetail = itemView.findViewById(R.id.btnDetail);

        txtItem.setText(tutorials[position]);
        imgItem.setImageResource(images[position]);

        btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", tutorials[position]);
            intent.putExtra("description", descriptions[position]);
            intent.putExtra("price", prices[position]);
            intent.putExtra("imageResId", images[position]);
            context.startActivity(intent);
        });

        return itemView;
    }
}
