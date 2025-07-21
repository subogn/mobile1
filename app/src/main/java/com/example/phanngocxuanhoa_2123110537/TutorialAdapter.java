package com.example.phanngocxuanhoa_2123110537;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TutorialAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> tutorials; // Dữ liệu động

    // Dữ liệu gốc để ánh xạ theo tên
    private final List<String> originalTutorials = Arrays.asList(
            "Sữa chua Hy Lạp",
            "Pannacotta Vani",
            "Bánh kem Chocolate",
            "Bánh quy Bơ",
            "Bánh Tiramisu",
            "Bánh Mousse Dâu",
            "Bánh Macaron",
            "Bánh Su Kem",
            "Bánh Flan Caramel"
    );

    private final int[] images = {
            R.drawable.img_suachuahylap,
            R.drawable.img_pannacotta,
            R.drawable.img_chocolatecake,
            R.drawable.img_cookie,
            R.drawable.img_tiramisu,
            R.drawable.img_mousse_dautay,
            R.drawable.img_macaron,
            R.drawable.img_choux,
            R.drawable.img_flan
    };

    private final String[] descriptions = {
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

    private final String[] prices = {
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

    public TutorialAdapter(@NonNull Context context, List<String> tutorials) {
        super(context, R.layout.item_home, tutorials);
        this.context = context;
        this.tutorials = tutorials;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);

        TextView txtItem = itemView.findViewById(R.id.txtItem);
        ImageView imgItem = itemView.findViewById(R.id.imgItem);
        Button btnDetail = itemView.findViewById(R.id.btnDetail);
        TextView txtDescription = itemView.findViewById(R.id.txtDescription);
        TextView txtPrice = itemView.findViewById(R.id.txtPrice);

        String title = tutorials.get(position);
        int index = originalTutorials.indexOf(title); // tìm vị trí trong danh sách gốc

        if (index != -1) {
            txtItem.setText(title);
            txtDescription.setText(descriptions[index]);
            txtPrice.setText("Giá: " + prices[index]);
            imgItem.setImageResource(images[index]);

            btnDetail.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("description", descriptions[index]);
                intent.putExtra("price", prices[index]);
                intent.putExtra("imageResId", images[index]);
                context.startActivity(intent);
            });
        }

        return itemView;
    }

    // 👉 Hàm cập nhật dữ liệu khi tìm kiếm
    public void updateData(List<String> newList) {
        tutorials.clear();
        tutorials.addAll(newList);
        notifyDataSetChanged();
    }
}
