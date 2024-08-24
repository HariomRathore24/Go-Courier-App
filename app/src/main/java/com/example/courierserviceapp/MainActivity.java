package com.example.courierserviceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import static com.example.courierserviceapp.R.*;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        CardView cardView = findViewById(R.id.deliveryCalculator);
//
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns grid
//        recyclerView.setAdapter(
//                new CardAdapter(getDataSet(), this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();
                if (id == R.id.home){

                    loadFag(new HomeFragment() , true);

        } else if( id == R.id.order) {
                    loadFag(new OrderFragment(), false);

                } else if( id == R.id.offer ) {
                    loadFag(new OfferFragment(), false);
        }
        else {
                    loadFag(new ProfileFragment(), false);
        }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home );

    }

    private void loadFag(Fragment fragment , boolean flag) {
                            FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                   if(flag){
                       ft.add(R.id.flFragment, fragment);
                   }else {
                       ft.replace(R.id.flFragment, fragment);
                   }
                    ft.commit();
    }


    private List<CardData> getDataSet() {
        // Sample data
        List<CardData> dataSet = new ArrayList<>();
        dataSet.add(new CardData(drawable.india, "All India Parcel", "Description 1"));
        dataSet.add(new CardData(drawable.truck, "Heavy items ", "House sources"));
        dataSet.add(new CardData(R.drawable.documentation, "Documents", "quick service"));
        dataSet.add(new CardData(drawable.motorbike, "Delivery items", "quick service"));
        dataSet.add(new CardData(R.drawable.truck, "packers", "Description 5"));
        dataSet.add(new CardData(drawable.boxes, "more items", "Scan your items"));
        return dataSet;
    }


}