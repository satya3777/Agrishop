package com.example.agrishop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
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

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final List<Product> productList = new ArrayList<>();
    private ProductAdapter adapter;
    private DatabaseReference productRef;
    private Button cartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);

        productRef = FirebaseDatabase.getInstance().getReference("Products");

        cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v -> {
            // Launch CartActivity to view the cart
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        populateDatabaseIfEmpty();
        fetchProducts();
    }

    private void populateDatabaseIfEmpty() {
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    List<Product> sampleProducts = new ArrayList<>();

                    // Create sample products with a generated id
                    String id1 = productRef.push().getKey();  // Generate unique ID
                    String id2 = productRef.push().getKey();
                    String id3 = productRef.push().getKey();
                    String id4 = productRef.push().getKey();
                    String id5 = productRef.push().getKey();
                    String id6 = productRef.push().getKey();
                    String id7 = productRef.push().getKey();
                    String id8 = productRef.push().getKey();
                    String id9 = productRef.push().getKey();
                    String id10 = productRef.push().getKey();
                    String id11 = productRef.push().getKey();
                    String id12 = productRef.push().getKey();

                    sampleProducts.add(new Product(id1, "Apple", 120, "Fresh apples", ""));
                    sampleProducts.add(new Product(id2, "Banana", 60, "Sweet bananas", ""));
                    sampleProducts.add(new Product(id3, "Carrot", 40, "Organic carrots", ""));
                    sampleProducts.add(new Product(id4, "Beat", 1200, "Fresh Beat", ""));
                    sampleProducts.add(new Product(id5, "Watermelon", 60, "Sweet Watermelon", ""));
                    sampleProducts.add(new Product(id6, "Kivi", 40, "juicy kivi", ""));
                    sampleProducts.add(new Product(id7, "Guava", 40, "Organic carrots", ""));
                    sampleProducts.add(new Product(id8, "Leach", 1200, "Fresh Beat", ""));
                    sampleProducts.add(new Product(id9, "Pomegranate", 71, "Sweet Watermelon", ""));
                    sampleProducts.add(new Product(id10, "Tomato", 400, "juicy kivi", ""));
                    sampleProducts.add(new Product(id11, "Jamun", 405, "Organic carrots", ""));
                    sampleProducts.add(new Product(id12, "Grapes", 120, "Fresh Beat", ""));


                    // Save products to Firebase
                    for (Product product : sampleProducts) {
                        productRef.child(product.getId()).setValue(product);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void fetchProducts() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Product product = data.getValue(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
