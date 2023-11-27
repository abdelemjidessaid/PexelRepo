package com.sharpminded.pexelrepo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sharpminded.pexelrepo.R;
import com.sharpminded.pexelrepo.VideoModel.Video;
import com.sharpminded.pexelrepo.VideoModel.VideoParser;
import com.sharpminded.pexelrepo.VideoModel.VideoResult;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideosActivity extends AppCompatActivity {
    private final String apiKey = "gVgtP1rdHAtF47oNU8kQilnvTA22Nn9VjwxRdmX0fppPBtvIrAWN8SYt";
    private final String baseUrl = "https://api.pexels.com/videos/";
    private RecyclerView recycler;
    private ImageButton nextPage, previousPage;
    private TextView pageNumber;
    private TextInputEditText searchInput;
    private VideoResult lastResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        this.recycler = findViewById(R.id.videos_recycler);
        this.nextPage = findViewById(R.id.videos_next_page);
        this.previousPage = findViewById(R.id.videos_previous_page);
        this.pageNumber = findViewById(R.id.videos_page_number);
        this.searchInput = findViewById(R.id.searchEditText);

        searchInput.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_NEXT) {
                String query = searchInput.getText().toString();
                search(query);
                Toast.makeText(VideosActivity.this, "Searching for " + query, Toast.LENGTH_SHORT).show();
                // Get the input method manager
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the keyboard
                View currentFocus = getCurrentFocus();
                if (currentFocus != null)
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                // clear the focus of text input view
                searchInput.clearFocus();

                return true;
            }
            return false;
        });

        search("");
    }

    private void search(String query) {
        String url;
        if (query.isEmpty()) url = baseUrl + "popular?per_page=50";
        else url = baseUrl + "search?query=" + query + "&per_page=50";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).addHeader("Authorization", apiKey).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(VideosActivity.this, "Fetching videos failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONObject json = new JSONObject(body);
                        VideoParser parser = new VideoParser();
                        VideoResult result = parser.videoResultParser(json);
                        lastResult = result;
                        // display the number of page
                        if (lastResult != null) runOnUiThread(() -> {
                            pageNumber.setText("" + lastResult.getPage());
                            Toast.makeText(VideosActivity.this, "Result : " + result.getTotal_results(), Toast.LENGTH_SHORT).show();
                        });
                        // display the result of videos
                        runOnUiThread(() -> display(result.getVideos()));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    runOnUiThread(() -> Toast.makeText(VideosActivity.this, "Bad response !", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void display(ArrayList<Video> videos) {
        recycler.setLayoutManager(new GridLayoutManager(VideosActivity.this, 2));
        recycler.setAdapter(new VideoAdapter(VideosActivity.this, videos));
    }

    public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
        private Context context;
        private ArrayList<Video> videos;

        public VideoAdapter(Context context, ArrayList<Video> videos) {
            this.context = context;
            this.videos = videos;
        }

        @NonNull
        @Override
        public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VideoHolder(LayoutInflater.from(context).inflate(R.layout.image_item, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
            Video video = videos.get(position);
            holder.textView.setText(video.getDuration() + "s");
            Picasso.get().load(video.getImage()).into(holder.imageView);

            holder.itemView.setOnClickListener(view -> {
                // open the video into VideoPreview activity
                Intent intent = new Intent(context, VideoPreviewActivity.class);
                intent.putExtra("url", video.getVideo_files().get(0).getLink());
                String title = video.getVideo_files().get(0).getId() + "";
                if (title.isEmpty()) title = "Video_" + System.currentTimeMillis();
                intent.putExtra("title", title);
                String extension = getExtensionFromMimeType(video.getVideo_files().get(0).getFile_type());
                if (extension.isEmpty()) extension = ".mp4";
                intent.putExtra("extension", extension);

                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return videos.size();
        }

        public String getExtensionFromMimeType(String mimeType) {
            if (mimeType == null || mimeType.isEmpty()) {
                return "";
            }

            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String extension = mimeTypeMap.getExtensionFromMimeType(mimeType);
            if (extension == null || extension.isEmpty()) {
                return "";
            } else {
                return "." + extension;
            }
        }

        public class VideoHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public VideoHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image_cardview);
                textView = itemView.findViewById(R.id.image_title);
            }
        }
    }
}