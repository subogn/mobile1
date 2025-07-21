package com.example.phanngocxuanhoa_2123110537;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnLogin, btnRegister;
    ProgressBar progressBar;

    // 🔥 SỬ DỤNG API TEST CÔNG CỘNG
    String URL_LOGIN = "https://jsonplaceholder.typicode.com/posts"; // API test công cộng

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Initialize views
        initViews();

        // Set click listeners
        setupClickListeners();
    }

    private void initViews() {
        edtPhone = findViewById(R.id.editTextPhone2);
        edtPassword = findViewById(R.id.editTextTextPassword2);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Add ProgressBar if you have it in layout
        // progressBar = findViewById(R.id.progressBar);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (validateInput(phone, password)) {
                loginUser(phone, password);
            }
        });
    }

    private boolean validateInput(String phone, String password) {
        if (phone.isEmpty()) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            edtPhone.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            edtPassword.requestFocus();
            return false;
        }

        // Validate phone format
        if (phone.length() < 10 || phone.length() > 11) {
            edtPhone.setError("Số điện thoại không hợp lệ");
            edtPhone.requestFocus();
            return false;
        }

        return true;
    }

    private void loginUser(String phone, String password) {
        // Show loading state
        btnLogin.setEnabled(false);
        btnLogin.setText("Đang đăng nhập...");

        // Log for debugging
        android.util.Log.d("LoginActivity", "Attempting login with phone: " + phone);
        android.util.Log.d("LoginActivity", "API URL: " + URL_LOGIN);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_LOGIN,
                this::handleLoginSuccess,
                this::handleLoginError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", password);
                android.util.Log.d("LoginActivity", "Sending params: " + params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("User-Agent", "Android App");
                return headers;
            }
        };

        // Set timeout and retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000, // 15 seconds timeout
                2, // 2 retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(stringRequest);
    }

    private void handleLoginSuccess(String response) {
        android.util.Log.d("LoginActivity", "Login response: " + response);

        // MOCK LOGIN - Giả lập đăng nhập thành công cho demo
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra tài khoản demo hoặc tài khoản đã đăng ký
        if (isValidAccount(phone, password)) {
            // Tài khoản hợp lệ - đăng nhập thành công
            UserInfo userInfo = getUserInfo(phone);

            // Save user info to SharedPreferences
            saveUserData(phone, userInfo.name, userInfo.email);

            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            // Navigate to HomeActivity
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            // Tài khoản không hợp lệ
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_LONG).show();
        }

        // Reset button state
        resetLoginButton();
    }

    private boolean isValidAccount(String phone, String password) {
        // Kiểm tra tài khoản demo trước
        if (isValidDemoAccount(phone, password)) {
            return true;
        }

        // Kiểm tra tài khoản đã đăng ký
        return isValidRegisteredAccount(phone, password);
    }

    private boolean isValidDemoAccount(String phone, String password) {
        // Danh sách tài khoản demo
        return (phone.equals("0123456789") && password.equals("123456")) ||
                (phone.equals("0987654321") && password.equals("password")) ||
                (phone.equals("0111222333") && password.equals("admin"));
    }

    private boolean isValidRegisteredAccount(String phone, String password) {
        // Kiểm tra tài khoản đã đăng ký trong SharedPreferences
        SharedPreferences newUserPrefs = getSharedPreferences("NewUsers", MODE_PRIVATE);
        String savedPassword = newUserPrefs.getString("user_" + phone + "_password", null);

        return savedPassword != null && savedPassword.equals(password);
    }

    private UserInfo getUserInfo(String phone) {
        UserInfo userInfo = new UserInfo();

        // Kiểm tra tài khoản demo trước
        if (isValidDemoAccount(phone, edtPassword.getText().toString().trim())) {
            userInfo.name = getDemoUserName(phone);
            userInfo.email = getDemoUserEmail(phone);
        } else {
            // Lấy thông tin từ tài khoản đã đăng ký
            SharedPreferences newUserPrefs = getSharedPreferences("NewUsers", MODE_PRIVATE);
            userInfo.name = newUserPrefs.getString("user_" + phone + "_name", "User");
            userInfo.email = newUserPrefs.getString("user_" + phone + "_email", "user@example.com");
        }

        return userInfo;
    }

    private String getDemoUserName(String phone) {
        switch (phone) {
            case "0123456789": return "Nguyen Van A";
            case "0987654321": return "Tran Thi B";
            case "0111222333": return "Le Van C";
            default: return "Demo User";
        }
    }

    private String getDemoUserEmail(String phone) {
        switch (phone) {
            case "0123456789": return "a@example.com";
            case "0987654321": return "b@example.com";
            case "0111222333": return "admin@example.com";
            default: return "demo@example.com";
        }
    }

    private void handleLoginError(VolleyError error) {
        String errorMessage = "Lỗi kết nối server";

        android.util.Log.e("LoginActivity", "Login error: " + error.toString());

        if (error.networkResponse != null) {
            android.util.Log.e("LoginActivity", "Error status code: " + error.networkResponse.statusCode);
            android.util.Log.e("LoginActivity", "Error response: " + new String(error.networkResponse.data));
        }

        if (error instanceof TimeoutError) {
            errorMessage = "Kết nối quá chậm, vui lòng thử lại";
        } else if (error instanceof NoConnectionError) {
            errorMessage = "Không có kết nối internet";
        } else if (error instanceof NetworkError) {
            errorMessage = "Lỗi mạng, kiểm tra kết nối";
        } else if (error instanceof ParseError) {
            errorMessage = "Lỗi xử lý dữ liệu từ server";
        } else if (error instanceof ServerError) {
            errorMessage = "Lỗi server, vui lòng thử lại sau";
        }

        // Show detailed error in debug
        if (error.getCause() != null) {
            android.util.Log.e("LoginActivity", "Error cause: " + error.getCause().getMessage());
        }

        Toast.makeText(this, errorMessage + "\nKiểm tra URL API: " + URL_LOGIN, Toast.LENGTH_LONG).show();
        resetLoginButton();
    }

    private void resetLoginButton() {
        btnLogin.setEnabled(true);
        btnLogin.setText("Đăng Nhập");
    }

    private void saveUserData(String phone, String name, String email) {
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .putString("user_phone", phone)
                .putString("user_name", name)
                .putString("user_email", email)
                .putBoolean("is_logged_in", true)
                .apply();

        android.util.Log.d("LoginActivity", "User data saved: " + phone + ", " + name + ", " + email);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }

    // Helper class để lưu thông tin user
    private static class UserInfo {
        String name;
        String email;
    }
}