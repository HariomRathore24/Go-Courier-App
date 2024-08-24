
package com.example.courierserviceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private RecyclerView recyclerView;
    private CardView cardView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the scan button
        View scanButton = view.findViewById(R.id.scan_button);
        // Set an OnClickListener to open the scanner when the button is clicked
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermissionAndOpenScanner();
            }
        });

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView);
        cardView = view.findViewById(R.id.deliveryCalculator);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // 2 columns grid
        recyclerView.setAdapter(new CardAdapter(getDataSet(), getContext())); // Pass context to the adapter

        // Set listener for CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open CalculateShipping activity
                Intent intent = new Intent(getActivity(), CalculateShipping.class);
                startActivity(intent);
            }
        });

        return view;
    }



    private List<CardData> getDataSet() {
        // Sample data
        List<CardData> dataSet = new ArrayList<>();
        dataSet.add(new CardData(R.drawable.india, "All India Parcel", "Description 1"));
        dataSet.add(new CardData(R.drawable.truck, "Heavy items ", "House sources"));
        dataSet.add(new CardData(R.drawable.documentation, "Documents", "quick service"));
        dataSet.add(new CardData(R.drawable.motorbike, "Delivery items", "quick service"));
        dataSet.add(new CardData(R.drawable.truck, "packers", "Description 5"));
        dataSet.add(new CardData(R.drawable.boxes, "more items", "Scan your items"));
        return dataSet;
    }

    private void checkCameraPermissionAndOpenScanner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();
            } else {
                openScanner();
            }
        } else {
            openScanner();
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
    }

    private void openScanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openScanner();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(requireContext(), "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}