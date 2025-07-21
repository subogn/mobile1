package com.example.phanngocxuanhoa_2123110537;

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

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    Button btnRegister;

    // 🔥 SỬ DỤNG API TEST CÔNG CỘNG (giống như LoginActivity)
    String URL_REGISTER = "https://jsonplaceholder.typicode.com/posts"; // API test công cộng

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

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

        // Set up click listeners
        setupClickListeners();
    }

    private void initViews() {
        edtUsername = findViewById(R.id.editTextText);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        edtPhone = findViewById(R.id.editTextPhone);
        edtPassword = findViewById(R.id.editTextTextPassword);
        edtConfirmPassword = findViewById(R.id.editTextTextcfPassword);
        btnRegister = findViewById(R.id.btnRegister2);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (validateInput(username, email, phone, password, confirmPassword)) {
                registerUser(username, email, phone, password);
            }
        });
    }

    private boolean validateInput(String username, String email, String phone, String password, String confirmPassword) {
        // Validate username
        if (username.isEmpty()) {
            edtUsername.setError("Vui lòng nhập tên người dùng");
            edtUsername.requestFocus();
            return false;
        }

        if (username.length() < 3) {
            edtUsername.setError("Tên người dùng phải có ít nhất 3 ký tự");
            edtUsername.requestFocus();
            return false;
        }

        // Validate email
        if (email.isEmpty()) {
            edtEmail.setError("Vui lòng nhập email");
            edtEmail.requestFocus();
            return false;
        }

        if (!isValidEmail(email)) {
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return false;
        }

        // Validate phone
        if (phone.isEmpty()) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            edtPhone.requestFocus();
            return false;
        }

        if (phone.length() < 10 || phone.length() > 11) {
            edtPhone.setError("Số điện thoại không hợp lệ");
            edtPhone.requestFocus();
            return false;
        }

        // Validate password
        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            edtPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPassword.requestFocus();
            return false;
        }

        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            edtConfirmPassword.setError("Vui lòng xác nhận mật khẩu");
            edtConfirmPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            edtConfirmPassword.requestFocus();
            return false;
        }

        // Check if phone already exists in demo accounts
        if (isPhoneAlreadyExists(phone)) {
            edtPhone.setError("Số điện thoại đã được đăng ký");
            edtPhone.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }

    private boolean isPhoneAlreadyExists(String phone) {
        // Kiểm tra với các tài khoản demo đã có
        return phone.equals("0123456789") ||
                phone.equals("0987654321") ||
                phone.equals("0111222333");
    }

    private void registerUser(String username, String email, String phone, String password) {
        // Show loading state
        btnRegister.setEnabled(false);
        btnRegister.setText("Đang đăng ký...");

        // Log for debugging
        android.util.Log.d("RegisterActivity", "Attempting register with phone: " + phone);
        android.util.Log.d("RegisterActivity", "API URL: " + URL_REGISTER);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL_REGISTER,
                response -> handleRegisterSuccess(response, username, email, phone),
                this::handleRegisterError
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);
                android.util.Log.d("RegisterActivity", "Sending params: " + params.toString());
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

    private void handleRegisterSuccess(String response, String username, String email, String phone) {
        android.util.Log.d("RegisterActivity", "Register response: " + response);

        // MOCK REGISTER - Giả lập đăng ký thành công cho demo
        // Lưu thông tin người dùng mới vào SharedPreferences để có thể đăng nhập
        saveNewUserData(username, email, phone);

        Toast.makeText(this, "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.", Toast.LENGTH_LONG).show();

        // Navigate back to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void handleRegisterError(VolleyError error) {
        String errorMessage = "Lỗi kết nối server";

        android.util.Log.e("RegisterActivity", "Register error: " + error.toString());

        if (error.networkResponse != null) {
            android.util.Log.e("RegisterActivity", "Error status code: " + error.networkResponse.statusCode);
            android.util.Log.e("RegisterActivity", "Error response: " + new String(error.networkResponse.data));
        }

        if (error instanceof TimeoutError) {
            errorMessage = "Kết nối quá chậm, vui lòng thử lại";
        } else if (error instanceof NoConnectionError) {
            errorMessage = "Không có kết nối internet";
        } else if (error instanceof ServerError) {
            errorMessage = "Lỗi server, vui lòng thử lại sau";
        } else if (error instanceof NetworkError) {
            errorMessage = "Lỗi mạng, kiểm tra kết nối";
        } else if (error instanceof ParseError) {
            errorMessage = "Lỗi xử lý dữ liệu từ server";
        }

        // Show detailed error in debug
        if (error.getCause() != null) {
            android.util.Log.e("RegisterActivity", "Error cause: " + error.getCause().getMessage());
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        resetRegisterButton();
    }

    private void resetRegisterButton() {
        btnRegister.setEnabled(true);
        btnRegister.setText("Đăng Ký");
    }

    private void saveNewUserData(String username, String email, String phone) {
        // Lưu thông tin người dùng mới để có thể đăng nhập sau này
        // Trong thực tế, điều này sẽ được lưu trên server
        getSharedPreferences("NewUsers", MODE_PRIVATE)
                .edit()
                .putString("user_" + phone + "_name", username)
                .putString("user_" + phone + "_email", email)
                .putString("user_" + phone + "_password", edtPassword.getText().toString().trim())
                .apply();

        android.util.Log.d("RegisterActivity", "New user data saved: " + phone + ", " + username + ", " + email);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}