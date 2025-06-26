package com.example.phanngocxuanhoa_2123110537;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivBack, ivFavorite, ivIceCream;
    private TextView tvTitle, tvDescription, tvPrice, tvRating;
    private RatingBar ratingBar;
    private Button btnAddToCart;
    private RecyclerView rvRelated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initViews();
        setupDataFromIntent();
        setupClickListeners();
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

        // Giả lập điểm rating nếu bạn muốn
        tvRating.setText("4.5");
        ratingBar.setRating(4.5f);
    }

    private void setupClickListeners() {
        ivBack.setOnClickListener(v -> finish());

        ivFavorite.setOnClickListener(v -> {
            ivFavorite.setSelected(!ivFavorite.isSelected());
            // TODO: lưu trạng thái yêu thích
        });

        btnAddToCart.setOnClickListener(v -> {
            // TODO: xử lý thêm vào giỏ
        });
    }
}
