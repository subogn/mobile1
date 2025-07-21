package com.example.phanngocxuanhoa_2123110537;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivBack, ivFavorite, ivIceCream;
    private TextView tvTitle, tvDescription, tvPrice, tvRating;
    private RatingBar ratingBar;
    private Button btnAddToCart;
    private RecyclerView rvRelated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupDataFromIntent();
        setupClickListeners();

        rvRelated = findViewById(R.id.rv_related);
        List<RelatedProduct> relatedProducts = new ArrayList<>();
        relatedProducts.add(new RelatedProduct("Bánh đào", "25.000đ", R.drawable.img_banhdao));
        relatedProducts.add(new RelatedProduct("Bánh matcha", "30.000đ", R.drawable.img_banhmatcha));
        relatedProducts.add(new RelatedProduct("Bánh trứng", "20.000đ", R.drawable.img_banhtrung));

        RelatedAdapter adapter = new RelatedAdapter(this, relatedProducts);
        rvRelated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvRelated.setAdapter(adapter);
    }

    private void initViews() {
        ivBack = findViewById(R.id.iv_back);
        ivFavorite = findViewById(R.id.iv_favorite);
        ivIceCream = findViewById(R.id.iv_ice_cream);
        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_price);
        tvRating = findViewById(R.id.tv_rating);
        ratingBar = findViewById(R.id.rating_bar);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        rvRelated = findViewById(R.id.rv_related);
    }

    private void setupDataFromIntent() {
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String price = getIntent().getStringExtra("price");
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.ic_banhkem);

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvPrice.setText(price);
        ivIceCream.setImageResource(imageResId);

        tvRating.setText("4.5");
        ratingBar.setRating(4.5f);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());

        ivFavorite.setOnClickListener(v -> {
            ivFavorite.setSelected(!ivFavorite.isSelected());
        });

        btnAddToCart.setOnClickListener(v -> showAddToCartDialog());
    }

    private void showAddToCartDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_to_cart);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Thêm code để tăng kích thước dialog
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        ImageView ivProductImage = dialog.findViewById(R.id.iv_product_image);
        TextView tvProductName = dialog.findViewById(R.id.tv_product_name);
        TextView tvProductPrice = dialog.findViewById(R.id.tv_product_price);
        RadioGroup rgSize = dialog.findViewById(R.id.rg_size);
        TextView tvQuantity = dialog.findViewById(R.id.tv_quantity);
        Button btnMinus = dialog.findViewById(R.id.btn_minus);
        Button btnPlus = dialog.findViewById(R.id.btn_plus);
        Button btnConfirmAdd = dialog.findViewById(R.id.btn_confirm_add);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);

        String title = getIntent().getStringExtra("title");
        String originalPrice = getIntent().getStringExtra("price");
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.ic_banhkem);

        ivProductImage.setImageResource(imageResId);
        tvProductName.setText(title);

        // Lấy giá gốc (size S) từ string
        final int basePrice = extractPriceFromString(originalPrice);

        // Hiển thị giá ban đầu (size S)
        tvProductPrice.setText(formatPrice(basePrice));

        final int[] quantity = {1};
        tvQuantity.setText(String.valueOf(quantity[0]));

        // Xử lý thay đổi size và cập nhật giá
        rgSize.setOnCheckedChangeListener((group, checkedId) -> {
            int newPrice = basePrice;

            if (checkedId == R.id.rb_size_s) {
                newPrice = basePrice; // Size S: giá gốc
            } else if (checkedId == R.id.rb_size_m) {
                newPrice = (int) (basePrice * 1.5); // Size M: +50%
            } else if (checkedId == R.id.rb_size_l) {
                newPrice = basePrice * 2; // Size L: +100%
            }

            tvProductPrice.setText(formatPrice(newPrice));
        });

        // Chọn size S mặc định
        rgSize.check(R.id.rb_size_s);

        btnMinus.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                tvQuantity.setText(String.valueOf(quantity[0]));
            }
        });

        btnPlus.setOnClickListener(v -> {
            quantity[0]++;
            tvQuantity.setText(String.valueOf(quantity[0]));
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirmAdd.setOnClickListener(v -> {
            int selectedSizeId = rgSize.getCheckedRadioButtonId();
            String selectedSize = "";

            if (selectedSizeId != -1) {
                RadioButton selectedRadioButton = dialog.findViewById(selectedSizeId);
                selectedSize = selectedRadioButton.getText().toString();
            }

            if (selectedSize.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn size bánh", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tính giá hiện tại dựa trên size đã chọn
            String currentPrice = tvProductPrice.getText().toString();

            // Thêm vào giỏ hàng thực tế
            addToCart(title, selectedSize, quantity[0], currentPrice, imageResId);

            String message = "Đã thêm " + quantity[0] + " " + title +
                    " (Size " + selectedSize + ") vào giỏ hàng";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            dialog.dismiss();
        });

        dialog.show();
    }

    // Method để trích xuất số từ chuỗi giá (ví dụ: "25.000đ" -> 25000)
    private int extractPriceFromString(String priceString) {
        if (priceString == null) return 0;

        // Loại bỏ tất cả ký tự không phải số
        String numericString = priceString.replaceAll("[^0-9]", "");

        try {
            return Integer.parseInt(numericString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Method để format giá thành chuỗi hiển thị
    private String formatPrice(int price) {
        // Chuyển đổi số thành định dạng xxx.xxxđ
        String priceStr = String.valueOf(price);
        StringBuilder formatted = new StringBuilder();

        int length = priceStr.length();
        for (int i = 0; i < length; i++) {
            if (i > 0 && (length - i) % 3 == 0) {
                formatted.append(".");
            }
            formatted.append(priceStr.charAt(i));
        }

        return formatted.toString() + "đ";
    }

    private void addToCart(String productName, String size, int quantity, String price, int imageResId) {
        CartItem item = new CartItem(productName, size, quantity, price, imageResId);
        CartManager cartManager = CartManager.getInstance(getApplicationContext());
        cartManager.addToCart(item);
    }
}
