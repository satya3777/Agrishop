package com.example.agrishop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartList;
    private OnRemoveClickListener removeClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(Product product);
    }

    public CartAdapter(List<Product> cartList, OnRemoveClickListener listener) {
        this.cartList = cartList;
        this.removeClickListener = listener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Product product = cartList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText("Price: Rs." + product.getPrice());
        holder.removeButton.setOnClickListener(v -> {
            removeClickListener.onRemoveClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, priceTextView;
        public Button removeButton;

        public CartViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.productName);
            priceTextView = view.findViewById(R.id.productPrice);
            removeButton = view.findViewById(R.id.removeButton);
        }
    }
}
