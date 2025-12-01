package com.f52123078.aplikasibelajarmandiri.viewModel; // Sesuaikan package

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

// Sesuaikan import package
import com.f52123078.aplikasibelajarmandiri.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;
    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ambil data dari Intent
        String url = getIntent().getStringExtra(EXTRA_URL);
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        // Setup Toolbar
        setSupportActionBar(binding.toolbarWebview);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title != null ? title : "Loading...");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbarWebview.setNavigationOnClickListener(v -> finish());

        // Setup WebView
        binding.webView.getSettings().setJavaScriptEnabled(true); // Wajib untuk banyak website

        // Set WebViewClient agar link tetap terbuka di dalam aplikasi
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.progressBarWeb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progressBarWeb.setVisibility(View.GONE);
                // Set judul toolbar dari halaman web jika intent tidak kirim judul
                if (title == null || title.isEmpty()) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(view.getTitle());
                    }
                }
            }
        });

        // Set WebChromeClient untuk handle progress bar
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100 && binding.progressBarWeb.getVisibility() == View.GONE) {
                    binding.progressBarWeb.setVisibility(View.VISIBLE);
                }
                if (newProgress == 100) {
                    binding.progressBarWeb.setVisibility(View.GONE);
                }
            }
        });

        // Muat URL
        if (url != null) {
            binding.webView.loadUrl(url);
        }
    }

    // Handle tombol back di HP agar kembali ke halaman web sebelumnya
    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}