package com.f52123078.aplikasibelajarmandiri.viewModel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Sesuaikan import package
import com.f52123078.aplikasibelajarmandiri.R; // Import R
import com.f52123078.aplikasibelajarmandiri.databinding.ItemResourceCardBinding;
import com.f52123078.aplikasibelajarmandiri.model.Resource;
import com.f52123078.aplikasibelajarmandiri.model.UserActivityModel; // Import Model baru

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {

    private final List<Resource> resourceList;
    private final List<String> documentIds; // Tambahkan documentIds
    private final UserActivityModel userActivityModel; // Instance model baru

    // Modifikasi konstruktor
    public ResourceAdapter(List<Resource> resourceList, List<String> documentIds) {
        this.resourceList = resourceList;
        this.documentIds = documentIds;
        this.userActivityModel = new UserActivityModel(); // Buat instance
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResourceCardBinding binding = ItemResourceCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ResourceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        String docId = documentIds.get(position); // Dapatkan ID Dokumen
        holder.bind(resource, docId, userActivityModel); // Kirim ID dan model ke bind
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    static class ResourceViewHolder extends RecyclerView.ViewHolder {
        private final ItemResourceCardBinding binding;

        public ResourceViewHolder(@NonNull ItemResourceCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Modifikasi bind untuk menerima docId dan userActivityModel
        public void bind(final Resource resource, final String docId, final UserActivityModel userActivityModel) {
            binding.tvResourceTitle.setText(resource.getTitle());
            String subtitle = (resource.getCourse() != null ? resource.getCourse() : "-") +
                    " • " +
                    (resource.getCategory() != null ? resource.getCategory() : "-");
            binding.tvResourceSubtitle.setText(subtitle);

            // Ganti icon berdasarkan tipe
            int iconRes = R.drawable.ic_launcher_foreground; // Default icon
            if (resource.getType() != null) {
                switch (resource.getType().toLowerCase()) {
                    case "youtube":
                        iconRes = R.drawable.play;
                        break;
                    case "pdf":
                        iconRes = R.drawable.pdf;
                        break;
                    case "website":
                        iconRes = R.drawable.domain;
                        break;
                    default:
                        iconRes = R.drawable.help;
                }
            }
            binding.ivIcon.setImageResource(iconRes);

//            OnclickListener
            itemView.setOnClickListener(v -> {
                // 1. Simpan data (Biarkan)
                userActivityModel.saveLastAccessedResource(
                        docId,
                        resource.getTitle(),
                        resource.getDesc(),
                        resource.getUrl()
                );

                // 2. Buka WebViewActivity dengan Logika Final
                Context context = v.getContext();
                Intent intent = new Intent(context, WebViewActivity.class);

                String urlToLoad = resource.getUrl();
                String type = resource.getType();
                String title = resource.getTitle();

                if (urlToLoad == null || urlToLoad.isEmpty()) {
                    Toast.makeText(context, "Link resource tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                String lowerUrl = urlToLoad.toLowerCase();

                // Prioritas 1: Cek apakah ini link Google Drive.
                // Jika ya, JANGAN diubah. Biarkan WebView memuat halaman GDrive.
                if (lowerUrl.contains("drive.google.com")) {
                    // (Tidak melakukan apa-apa)
                }
                // Prioritas 2: Cek tipe dari database (UNTUK LINK NON-GDRIVE)
                else if (type != null) {
                    if (type.equalsIgnoreCase("pdf")) {
                        // Ini PDF dari link langsung, bungkus dengan gview
                        urlToLoad = "https://docs.google.com/gview?embedded=true&url=" + urlToLoad;
                    } else if (type.equalsIgnoreCase("youtube")) {
                        // Ini YouTube, ubah ke link embed
                        if (urlToLoad.contains("/watch?v=")) {
                            String videoId = urlToLoad.split("v=")[1].split("&")[0];
                            urlToLoad = "https://www.youtube.com/embed/" + videoId;
                        }
                    }
                }
                // Prioritas 3: Fallback (NON-GDRIVE dan tipe null)
                // Cek URL mentah jika 'type' tidak ada di database
                else if (lowerUrl.contains(".pdf")) {
                    urlToLoad = "https://docs.google.com/gview?embedded=true&url=" + urlToLoad;
                } else if (lowerUrl.contains("youtube.com/watch?v=")) {
                    String videoId = urlToLoad.split("v=")[1].split("&")[0];
                    urlToLoad = "https://www.youtube.com/embed/" + videoId;
                }

                // --- BATAS PERUBAHAN ---

                // Kirim ke WebViewActivity
                intent.putExtra(WebViewActivity.EXTRA_URL, urlToLoad);
                intent.putExtra(WebViewActivity.EXTRA_TITLE, title);
                context.startActivity(intent);
            });
        }
    }
}
