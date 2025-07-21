package com.example.phanngocxuanhoa_2123110537;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCart;
    private TextView totalPriceText, itemCountText;
    private Button checkoutButton;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 🔧 Khởi tạo CartManager một lần duy nhất
        CartManager.getInstance(this);

        initViews();
        setupRecyclerView();
        updateCartDisplay();

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        rvCart = findViewById(R.id.rv_cart);
        totalPriceText = findViewById(R.id.totalPriceText);
        itemCountText = findViewById(R.id.itemCountText);
        checkoutButton = findViewById(R.id.checkoutBtn);
    }

    private void setupRecyclerView() {
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Không bị crash vì đã khởi tạo ở onCreate()
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnCartItemListener() {
            @Override
            public void onQuantityChanged() {
                updateCartDisplay();
            }

            @Override
            public void onItemRemoved() {
                updateCartDisplay();
            }
        });

        rvCart.setAdapter(cartAdapter);
    }

    private void updateCartDisplay() {
        CartManager cartManager = CartManager.getInstance(); // ❌ Không cần truyền context nữa

        int totalItems = cartManager.getTotalItemCount();
        itemCountText.setText("Tổng sản phẩm: " + totalItems);

        double totalPrice = cartManager.getTotalPrice();
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        totalPriceText.setText("Tổng tiền: " + formatter.format(totalPrice));

        checkoutButton.setEnabled(totalItems > 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
        }
        updateCartDisplay();
    }
}
