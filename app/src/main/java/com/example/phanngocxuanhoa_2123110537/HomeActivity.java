package com.example.phanngocxuanhoa_2123110537;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    ListView listNganh;

    String[] tutorials = {
            "Sữa chua Hy Lạp",
            "Pannacotta Vani",
            "Bánh kem Chocolate",
            "Bánh quy Bơ",
            "Bánh Tiramisu",
            "Bánh Mousse Dâu",
            "Bánh Macaron",
            "Bánh Su Kem",
            "Bánh Flan Caramel"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listNganh = findViewById(R.id.list);

        // Gán adapter tùy chỉnh
        TutorialAdapter adapter = new TutorialAdapter(this, tutorials);
        listNganh.setAdapter(adapter);

        // 👉 Gán sự kiện click icon giỏ hàng
        ImageView cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
}
