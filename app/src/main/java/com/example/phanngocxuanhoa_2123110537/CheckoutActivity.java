package com.example.phanngocxuanhoa_2123110537;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private ListView lvCartItems;
    private TextView tvTotalPrice;
    private Button btnConfirmPayment;
    private EditText etDeliveryAddress;
    private CartListAdapter adapter;
    private CartManager cartManager;

    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_ADDRESS = "delivery_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        lvCartItems = findViewById(R.id.lv_cart_items);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnConfirmPayment = findViewById(R.id.btn_confirm_payment);
        etDeliveryAddress = findViewById(R.id.etDeliveryAddress); // 🔥 Thêm EditText địa chỉ



        // Load địa chỉ từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedAddress = prefs.getString(KEY_ADDRESS, "");
        etDeliveryAddress.setText(savedAddress);

        // Lấy danh sách sản phẩm trong giỏ
        cartManager = CartManager.getInstance(this);
        List<CartItem> cartItems = cartManager.getCartItems();

        // Đổ dữ liệu lên ListView
        adapter = new CartListAdapter(this, cartItems);
        lvCartItems.setAdapter(adapter);

        // Hiển thị tổng tiền
        updateTotalPrice();

        // Xác nhận thanh toán
        btnConfirmPayment.setOnClickListener(v -> {
            String enteredAddress = etDeliveryAddress.getText().toString().trim();

            if (enteredAddress.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu địa chỉ
            prefs.edit().putString(KEY_ADDRESS, enteredAddress).apply();

            Toast.makeText(this, "Đã thanh toán thành công!", Toast.LENGTH_LONG).show();
            cartManager.clearCart(); // Xoá giỏ
            adapter.setItems(cartManager.getCartItems()); // 🔁 Cập nhật danh sách mới
            adapter.notifyDataSetChanged(); // Cập nhật ListView
            updateTotalPrice(); // Cập nhật tổng tiền

        });
    }

    // ✅ Hàm cập nhật tổng tiền hiển thị
    private void updateTotalPrice() {
        int total = 0;
        for (CartItem item : cartManager.getCartItems()) {
            total += item.getQuantity() * parsePrice(item.getPrice());
        }

        // Format tiền theo VNĐ
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTotalPrice.setText("Tổng: " + format.format(total));
    }

    // ✅ Hàm chuyển giá về số nguyên
    private int parsePrice(String priceStr) {
        try {
            return Integer.parseInt(priceStr.replace("đ", "").replace(",", "").replace(".", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
