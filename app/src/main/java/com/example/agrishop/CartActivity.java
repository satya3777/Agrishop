package com.example.agrishop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartList;
    private DatabaseReference cartRef;
    private TextView totalPriceText;
    private Button placeOrderButton;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalPriceText = findViewById(R.id.totalPrice);
        placeOrderButton = findViewById(R.id.placeOrderButton);

        cartList = new ArrayList<>();
        cartRef = FirebaseDatabase.getInstance().getReference("Cart");

        fetchCartItems();

        placeOrderButton.setOnClickListener(v -> {
            // Place the order and show a confirmation message
            Toast.makeText(CartActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            clearCart();
        });
    }

    private void fetchCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                cartList.clear();
                totalAmount = 0; // Reset total amount

                for (DataSnapshot data : snapshot.getChildren()) {
                    Product product = data.getValue(Product.class);
                    if (product != null) {
                        cartList.add(product);
                        totalAmount += product.getPrice(); // Calculate total amount
                    }
                }

                cartAdapter = new CartAdapter(cartList, product -> {
                    removeProductFromCart(product);
                });
                recyclerView.setAdapter(cartAdapter);

                // Update the total amount
                updateTotalAmount();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CartActivity.this, "Error fetching cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeProductFromCart(Product product) {
        cartRef.child(product.getId()).removeValue(); // Remove from Firebase
        cartList.remove(product); // Remove from local list
        cartAdapter.notifyDataSetChanged(); // Notify adapter of data change
        totalAmount -= product.getPrice(); // Update total price
        updateTotalAmount(); // Update total amount in UI
    }

    private void updateTotalAmount() {
        totalPriceText.setText("Total: Rs." + totalAmount); // Update total price display
    }

    private void clearCart() {
        cartRef.removeValue();  // Remove all items from Firebase cart
        cartList.clear();       // Clear the local list
        cartAdapter.notifyDataSetChanged();  // Notify adapter of data change
        totalAmount = 0;        // Reset total price
        updateTotalAmount();    // Update total amount display
    }
}
