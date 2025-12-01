package com.f52123078.aplikasibelajarmandiri.viewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.f52123078.aplikasibelajarmandiri.databinding.ActivityResourceBrowseBinding;
import com.f52123078.aplikasibelajarmandiri.model.MataKuliah;
import com.f52123078.aplikasibelajarmandiri.model.Prodi;
import com.f52123078.aplikasibelajarmandiri.model.Resource;
import com.f52123078.aplikasibelajarmandiri.model.ResourceBrowseModel;

import java.util.ArrayList;
import java.util.List;

public class ResourceBrowseActivity extends AppCompatActivity implements ResourceBrowseModel.BrowseDataListener {

    private ActivityResourceBrowseBinding binding;
    private ResourceBrowseModel model;
    private ResourceAdapter adapter;
    private List<Prodi> prodiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResourceBrowseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ResourceBrowseModel();
        setupToolbar();
        setupRecyclerView();
        setupFilterListeners();

        showLoading(true);
        model.loadProdi(this);
        model.loadFilteredResources(null, null, this);
    }

    private void setupToolbar() {
        binding.toolbarBrowse.setNavigationOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        binding.recyclerViewBrowse.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupFilterListeners() {
        binding.actProdiFilter.setOnItemClickListener((parent, view, position, id) -> {
            Prodi selectedProdi = (Prodi) parent.getItemAtPosition(position);
            binding.actMkFilter.setText("", false);
            binding.actMkFilter.setEnabled(false);
            if (selectedProdi != null) {
                showLoading(true);
                model.loadMataKuliah(selectedProdi.getDocumentId(), this);
                model.loadFilteredResources(selectedProdi.getDocumentId(), null, this);
            }
        });

        binding.actMkFilter.setOnItemClickListener((parent, view, position, id) -> {
            MataKuliah selectedMK = (MataKuliah) parent.getItemAtPosition(position);
            // untuk filter
            showLoading(true);
            model.loadFilteredResources(null, selectedMK != null ? selectedMK.getDocumentId() : null, this);
        });

        binding.btnClearFilter.setOnClickListener(v -> {
            binding.actProdiFilter.setText("", false);
            binding.actMkFilter.setText("", false);
            binding.actMkFilter.setEnabled(false);
            showLoading(true);
            model.loadFilteredResources(null, null, this);
        });
    }

    private void showLoading(boolean isLoading) {
        binding.progressBarBrowse.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.recyclerViewBrowse.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        binding.tvEmptyBrowse.setVisibility(View.GONE);
    }

    @Override
    public void onProdiLoaded(List<Prodi> prodiList) {
        this.prodiList = prodiList;
        ArrayAdapter<Prodi> prodiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, prodiList);
        binding.actProdiFilter.setAdapter(prodiAdapter);
    }

    @Override
    public void onMataKuliahLoaded(List<MataKuliah> mkList) {
        ArrayAdapter<MataKuliah> mkAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mkList);
        binding.actMkFilter.setAdapter(mkAdapter);
        binding.actMkFilter.setEnabled(true);
    }

    @Override
    public void onResourcesFiltered(List<Resource> resourceList, List<String> documentIds) { // <-- Ubah tanda tangan method
        showLoading(false);
        if (resourceList == null) resourceList = new ArrayList<>();
        if (documentIds == null) documentIds = new ArrayList<>(); // <-- Tambahkan null check untuk Ids

        // Kirimkan documentIds yang asli, bukan list kosong
        adapter = new ResourceAdapter(resourceList, documentIds); // <-- INI PERBAIKANNYA
        binding.recyclerViewBrowse.setAdapter(adapter);

        if (resourceList.isEmpty()) {
            binding.tvEmptyBrowse.setVisibility(View.VISIBLE);
            binding.recyclerViewBrowse.setVisibility(View.GONE);
        } else {
            binding.tvEmptyBrowse.setVisibility(View.GONE);
            binding.recyclerViewBrowse.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(String error) {
        showLoading(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
