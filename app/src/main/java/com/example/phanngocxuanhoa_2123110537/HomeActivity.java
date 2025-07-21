package com.example.phanngocxuanhoa_2123110537;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ListView listNganh;
    SearchView searchView;

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

    List<String> tutorialList; // Danh sách dữ liệu động để tìm kiếm
    TutorialAdapter adapter;

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
        searchView = findViewById(R.id.searchView); // tìm view SearchView

        // Chuyển mảng sang danh sách để dễ xử lý
        tutorialList = new ArrayList<>(Arrays.asList(tutorials));
        adapter = new TutorialAdapter(this, tutorialList);
        listNganh.setAdapter(adapter);

        // 👉 Gán sự kiện tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Không cần xử lý khi nhấn Enter
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText); // Lọc danh sách khi người dùng gõ
                return true;
            }
        });

        // 👉 Gán sự kiện click icon giỏ hàng
        ImageView cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    // Hàm lọc danh sách theo từ khóa
    private void filterList(String keyword) {
        List<String> filteredList = new ArrayList<>();
        for (String item : tutorials) {
            if (item.toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateData(filteredList); // Gọi hàm cập nhật adapter
    }
}
