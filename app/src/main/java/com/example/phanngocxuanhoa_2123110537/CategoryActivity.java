package com.example.phanngocxuanhoa_2123110537;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    TextView tvCategoryTitle;
    ListView lvCategoryItems;

    String[] defaultItems = {
            "Bánh kem Chocolate", "Bánh Su kem", "Tiramisu", "Mousse Dâu"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        lvCategoryItems = findViewById(R.id.lvCategoryItems);

        // Nhận tên danh mục từ Intent
        String category = getIntent().getStringExtra("category");
        tvCategoryTitle.setText(category);

        // TODO: bạn có thể phân loại danh sách theo danh mục nếu muốn
        lvCategoryItems.setAdapter(new android.widget.ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, defaultItems
        ));
    }
}
