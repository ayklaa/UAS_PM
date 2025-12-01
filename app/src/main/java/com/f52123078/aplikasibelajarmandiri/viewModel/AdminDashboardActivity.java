package com.f52123078.aplikasibelajarmandiri.viewModel;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.f52123078.aplikasibelajarmandiri.R;
import com.f52123078.aplikasibelajarmandiri.databinding.ActivityAdminDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {

    // View: Dikelola oleh ViewBinding
    private ActivityAdminDashboardBinding binding;

    // Model: Kita hanya butuh FirebaseAuth untuk logout di sini
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Controller: Menginisialisasi View
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Controller: Menginisialisasi Model (FirebaseAuth)
        mAuth = FirebaseAuth.getInstance();

        // Setup listener untuk semua tombol
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Listener Toolbar
        binding.toolbarAdmin.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout_admin) {
                // Logout
                mAuth.signOut();
                // Kembali ke halaman Login
                Intent intent = new Intent(AdminDashboardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        // Listener Card "Kelola Resources"
        binding.cardManageResources.setOnClickListener(v -> {
            // Nanti kita akan buat AdminManageResourceActivity.java
             Intent intent = new Intent(AdminDashboardActivity.this, AdminManageResourceActivity.class);
             startActivity(intent);
            Toast.makeText(this, "Nanti kita buka Panel Add/Edit Resource", Toast.LENGTH_SHORT).show();
        });

        // Listener Card "Kelola Prodi"
        binding.cardManageProdi.setOnClickListener(v -> {
            Toast.makeText(this, "Nanti kita buka Panel Kelola Prodi", Toast.LENGTH_SHORT).show();
        });

        // Listener Card "Kelola MK"
        binding.cardManageMk.setOnClickListener(v -> {
            Toast.makeText(this, "Nanti kita buka Panel Kelola Mata Kuliah", Toast.LENGTH_SHORT).show();
        });

        // Listener Card "Kelola Users"
        binding.cardManageUsers.setOnClickListener(v -> {
            Toast.makeText(this, "Nanti kita buka Panel Kelola Users", Toast.LENGTH_SHORT).show();
        });
    }
}
