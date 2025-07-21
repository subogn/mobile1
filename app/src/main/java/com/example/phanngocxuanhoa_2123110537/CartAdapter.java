package com.example.phanngocxuanhoa_2123110537;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartItemListener listener;

    // Interface để tương thích với CartActivity
    public interface OnCartItemListener {
        void onQuantityChanged();
        void onItemRemoved();
    }

    // Interface cũ để tương thích
    public interface OnCartItemChangeListener {
        void onQuantityChanged(String productId, int newQuantity);
        void onItemRemoved(String productId);
    }

    private OnCartItemChangeListener changeListener;

    public CartAdapter(List<CartItem> cartItems, OnCartItemListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    // Constructor cũ để tương thích
    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setOnCartItemChangeListener(OnCartItemChangeListener listener) {
        this.changeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvProductName.setText(item.getProductName());
        holder.tvSize.setText("Size: " + item.getSize());
        holder.tvPrice.setText(item.getPrice());
        holder.tvTotalPrice.setText(item.getFormattedTotalPrice());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.ivProductImage.setImageResource(item.getImageResId());

        // Xử lý nút giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            if (currentQuantity > 1) {
                int newQuantity = currentQuantity - 1;
                item.setQuantity(newQuantity);

                // Cập nhật CartManager
                CartManager.getInstance().updateQuantity(item.getProductId(), item.getSize(), newQuantity);

                // Cập nhật UI
                holder.tvQuantity.setText(String.valueOf(newQuantity));
                holder.tvTotalPrice.setText(item.getFormattedTotalPrice());

                // Gọi callback
                if (listener != null) {
                    listener.onQuantityChanged();
                }
                if (changeListener != null) {
                    changeListener.onQuantityChanged(item.getProductId(), newQuantity);
                }
            }
        });

        // Xử lý nút tăng số lượng
        holder.btnPlus.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            int newQuantity = currentQuantity + 1;
            item.setQuantity(newQuantity);

            // Cập nhật CartManager
            CartManager.getInstance().updateQuantity(item.getProductId(), item.getSize(), newQuantity);

            // Cập nhật UI
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            holder.tvTotalPrice.setText(item.getFormattedTotalPrice());

            // Gọi callback
            if (listener != null) {
                listener.onQuantityChanged();
            }
            if (changeListener != null) {
                changeListener.onQuantityChanged(item.getProductId(), newQuantity);
            }
        });

        // Xử lý nút xóa
        holder.btnRemove.setOnClickListener(v -> {
            // Xóa khỏi CartManager
            CartManager.getInstance().removeFromCart(item.getProductId(), item.getSize());

            // Xóa khỏi list và cập nhật RecyclerView
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());

            // Gọi callback
            if (listener != null) {
                listener.onItemRemoved();
            }
            if (changeListener != null) {
                changeListener.onItemRemoved(item.getProductId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateItems(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvSize, tvPrice, tvTotalPrice, tvQuantity;
        Button btnMinus, btnPlus, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}