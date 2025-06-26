package com.example.phanngocxuanhoa_2123110537;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    private LinearLayout cartItemsLayout;
    private TextView totalPriceText;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        cartItemsLayout = findViewById(R.id.cartItemsLayout);
        totalPriceText = findViewById(R.id.totalPriceText);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Gán dữ liệu cứng vào giỏ hàng
        int total = 0;
        total += addCartItem("Bánh kem dâu", "1 cái", 50000);
        total += addCartItem("Bánh quy", "2 gói", 30000);
        total += addCartItem("Bánh mì", "3 cái", 45000);

        totalPriceText.setText("Tổng tiền: " + total + "đ");
    }

    private int addCartItem(String name, String quantity, int price) {
        // Inflate layout item_cart.xml
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.item_cart, cartItemsLayout, false);

        // Set nội dung
        TextView txtInfo = itemView.findViewById(R.id.txtProductInfo);
        TextView txtPrice = itemView.findViewById(R.id.txtProductPrice);

        txtInfo.setText(name + " (" + quantity + ")");
        txtPrice.setText(price + "đ");

        // Thêm vào layout chính
        cartItemsLayout.addView(itemView);

        return price;
    }
}
