package com.example.phanngocxuanhoa_2123110537;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CartManager {
    private static CartManager instance;
    private static final String PREF_NAME = "cart_pref";
    private static final String CART_KEY = "cart_items";
    private Context context;
    private List<CartItem> cartItems = new ArrayList<>();

    private CartManager(Context context) {
        this.context = context.getApplicationContext();
        loadCartFromPreferences();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    public static CartManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CartManager chưa được khởi tạo. Gọi getInstance(Context) trước.");
        }
        return instance;
    }

    // ✅ Sửa: luôn tạo productId ngẫu nhiên để không bị gộp
    public void addToCart(String productName, String size, int quantity, String price, int imageResId) {
        String productId = UUID.randomUUID().toString(); // 🔥 unique mỗi lần
        CartItem newItem = new CartItem(productName, size, quantity, price, imageResId);
        newItem.setProductId(productId);
        cartItems.add(newItem);
        saveCartToPreferences();
        Log.d("CART_DEBUG", "Thêm dòng mới: " + productName + ", SL: " + quantity);
    }

    // ✅ Optional: nếu bạn vẫn dùng addToCart(CartItem)
    public void addToCart(CartItem cartItem) {
        cartItem.setProductId(UUID.randomUUID().toString());
        cartItems.add(cartItem);
        saveCartToPreferences();
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void updateQuantity(String productId, String size, int newQuantity) {
        CartItem item = findItemById(productId);
        if (item != null) {
            if (newQuantity <= 0) {
                cartItems.remove(item);
            } else {
                item.setQuantity(newQuantity);
            }
            saveCartToPreferences();
        }
    }

    public void removeItem(String productId) {
        CartItem item = findItemById(productId);
        if (item != null) {
            cartItems.remove(item);
            saveCartToPreferences();
        }
    }
    // ✅ Hàm xóa sản phẩm theo id và size
    public void removeFromCart(String productId, String size) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getProductId().equals(productId) && item.getSize().equals(size)) {
                cartItems.remove(i);
                saveCartToPreferences(); // Lưu thay đổi
                break;
            }
        }
    }


    public void clearCart() {
        cartItems.clear();
        saveCartToPreferences();
    }

    public int getTotalItemCount() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPriceAsDouble();
        }
        return total;
    }

    public int getTotalPriceAsInt() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public String getFormattedTotalPrice() {
        double total = getTotalPrice();
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(total);
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    private CartItem findItemById(String productId) {
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    private void saveCartToPreferences() {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    private void loadCartFromPreferences() {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(CART_KEY, null);
        if (json != null) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<CartItem>>() {}.getType();
                List<CartItem> loadedItems = gson.fromJson(json, type);
                if (loadedItems != null) {
                    cartItems = loadedItems;
                }
            } catch (Exception e) {
                cartItems = new ArrayList<>();
            }
        }
        Log.d("CART_DEBUG", "Đã load giỏ hàng: " + cartItems.size() + " sản phẩm");
    }
}
