package com.example.phanngocxuanhoa_2123110537;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // Ẩn ActionBar mặc định để hiển thị toolbar tùy chỉnh
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnRegister =findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent an = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(an);
            }
        });

        Button btnHome =findViewById(R.id.btnLogin);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText objPhone = findViewById(R.id.editTextPhone2);
                String txtPhone = objPhone.getText().toString();

                EditText objPass = findViewById(R.id.editTextTextPassword2);
                String txtPass = objPass.getText().toString();

                if (txtPass.equals("0345") && txtPhone.equals("0365356410"))
                {
                    Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login fail!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}