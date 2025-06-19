package com.example.phanngocxuanhoa_2123110537;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    ListView listNganh;

    String[] tutorials ={
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

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        // Ẩn ActionBar mặc định để hiển thị toolbar tùy chỉnh
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listNganh=findViewById(R.id.list);
        ArrayAdapter<String> arr;

        arr=new ArrayAdapter<String>(this,
                R.layout.item_home,tutorials);

        listNganh.setAdapter(arr);
//        Button aa = findViewById(R.id.btnBack);
//        aa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent b = new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(b);
//            }
//        });
    }

}