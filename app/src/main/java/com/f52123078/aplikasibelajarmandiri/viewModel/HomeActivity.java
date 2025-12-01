package com.f52123078.aplikasibelajarmandiri.viewModel; // Sesuaikan package

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

// Sesuaikan import package
import com.f52123078.aplikasibelajarmandiri.R;
import com.f52123078.aplikasibelajarmandiri.databinding.ActivityHomeBinding;
import com.f52123078.aplikasibelajarmandiri.model.HomeModel;
import com.f52123078.aplikasibelajarmandiri.view.AccountFragment; // Class Fragment yang asli
import com.f52123078.aplikasibelajarmandiri.view.HomeFragment; // Class Fragment yang asli
import com.f52123078.aplikasibelajarmandiri.view.ResourcesFragment; // Class Fragment yang asli


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity"; // Tag Log
    private ActivityHomeBinding binding;
    private HomeModel homeModel; // Masih dipakai untuk logout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: Activity started");

        homeModel = new HomeModel(); // Inisialisasi untuk logout

        setupToolbar();
        setupBottomNavigation();

        // Muat fragment awal (HomeFragment) hanya jika activity baru dibuat
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: Loading initial fragment (HomeFragment)");
            loadFragment(new HomeFragment(), "HOME"); // Muat HomeFragment pertama kali dengan tag
            binding.bottomNavigation.setSelectedItemId(R.id.navigation_home); // Tandai menu Home aktif
        } else {
            Log.d(TAG, "onCreate: Activity recreated, fragment restored by system.");
            // Optional: ensure correct fragment is shown based on selected nav item if needed
            // int selectedItemId = binding.bottomNavigation.getSelectedItemId();
            // handleNavigationSelection(selectedItemId); // Panggil fungsi internal untuk memuat fragment yang sesuai
        }
    }

    private void setupToolbar() {
        Log.d(TAG, "setupToolbar: Setting up toolbar");
        binding.toolbarHome.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                Log.i(TAG, "Logout button clicked");
                homeModel.logout();
                goToLogin();
                return true;
            }
            return false;
        });
    }

    private void setupBottomNavigation() {
        Log.d(TAG, "setupBottomNavigation: Setting up bottom navigation");
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            return handleNavigationSelection(item.getItemId()); // Panggil helper function
        });

        // Set reselection listener agar tidak me-reload fragment yang sama
        binding.bottomNavigation.setOnItemReselectedListener(item -> {
            Log.d(TAG, "BottomNav: Item reselected, doing nothing.");
            // Tidak melakukan apa-apa jika item yang sama dipilih lagi
        });
    }

    // Helper function to handle navigation item selection
    // Helper function to handle navigation item selection
    private boolean handleNavigationSelection(int itemId) {
        Fragment selectedFragment = null;
        String tag = null;

        if (itemId == R.id.navigation_home) {
            Log.d(TAG, "BottomNav: Home selected");
            selectedFragment = findOrCreateFragment(HomeFragment.class, "HOME");
            tag = "HOME";

            // --- INI PERUBAHANNYA ---
        } else if (itemId == R.id.navigation_resources) {
            Log.d(TAG, "BottomNav: Resources selected, launching ResourceBrowseActivity");
            // Buat Intent untuk membuka Activity baru
            Intent intent = new Intent(this, ResourceBrowseActivity.class);
            startActivity(intent);
            // Kembalikan 'false' agar item nav tidak dipilih secara permanen
            return false;
            // --- BATAS PERUBAHAN ---

        } else if (itemId == R.id.navigation_account) {
            Log.d(TAG, "BottomNav: Account selected");
            selectedFragment = findOrCreateFragment(AccountFragment.class, "ACCOUNT");
            tag = "ACCOUNT";
        }

        if (selectedFragment != null) {
            Log.d(TAG, "BottomNav: Loading fragment with tag: " + tag);
            loadFragment(selectedFragment, tag); // Ganti fragment dengan tag
            return true; // Item terpilih berhasil ditangani
        } else {
            Log.w(TAG, "BottomNav: No fragment selected for itemId: " + itemId);
            return false; // Item tidak dikenali
        }
    }


    // Method helper untuk memuat/mengganti fragment
    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check if the fragment is already added and visible
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (currentFragment != null && currentFragment.getClass() == fragment.getClass() && currentFragment.isVisible()) {
            Log.d(TAG, "loadFragment: Fragment already visible, skipping transaction for " + tag);
            return; // Don't reload if it's the same type and already visible
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag); // Ganti isi FrameLayout dengan tag
        // No back stack for main navigation usually
        transaction.commit();
        Log.d(TAG, "loadFragment: Committed transaction for " + fragment.getClass().getSimpleName() + " with tag " + tag);
    }

    // Helper untuk mencari fragment yang sudah ada atau buat baru
    // Ini mencegah fragment dibuat ulang setiap kali tab diklik
    private <T extends Fragment> T findOrCreateFragment(Class<T> fragmentClass, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                Log.d(TAG, "findOrCreateFragment: Creating new instance for tag: " + tag);
                return fragmentClass.newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException e) {
                Log.e(TAG, "findOrCreateFragment: Error creating fragment instance", e);
                // Fallback or handle error appropriately, maybe return null or throw exception
                // Returning null might lead to issues if not handled in the caller
                // For simplicity, let's re-throw but ideally handle more gracefully
                throw new RuntimeException("Could not instantiate fragment " + fragmentClass.getName(), e);
            }
        } else {
            Log.d(TAG, "findOrCreateFragment: Found existing instance for tag: " + tag);
            // Ensure the found fragment is actually of the expected type
            if (fragmentClass.isInstance(fragment)) {
                return fragmentClass.cast(fragment);
            } else {
                // This case should ideally not happen if tags are unique and managed correctly
                Log.e(TAG, "findOrCreateFragment: Found fragment with tag " + tag + " but it was of the wrong class type: " + fragment.getClass().getName());
                // If types mismatch, maybe remove the old one and create a new one? Risky.
                // Let's create a new one as a fallback, but log the error prominently.
                try { return fragmentClass.newInstance(); }
                catch (Exception e) {
                    Log.e(TAG, "findOrCreateFragment: Error creating fallback fragment instance", e);
                    throw new RuntimeException("Could not instantiate fallback fragment " + fragmentClass.getName(), e);
                }
            }
        }
    }


    private void goToLogin() {
        Log.i(TAG, "goToLogin: Returning to MainActivity");
        Intent intent = new Intent(HomeActivity.this, MainActivity.class); // Ke MainActivity (Login)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Tambahkan log saat activity dihancurkan (opsional)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity destroyed");
    }
}