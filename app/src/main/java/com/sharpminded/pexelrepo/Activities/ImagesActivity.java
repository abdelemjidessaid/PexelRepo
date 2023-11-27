package com.sharpminded.pexelrepo.Activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sharpminded.pexelrepo.ImageModel.Image;
import com.sharpminded.pexelrepo.ImageModel.ImageResult;
import com.sharpminded.pexelrepo.ImageModel.ImageResultParser;
import com.sharpminded.pexelrepo.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImagesActivity extends Activity {
    private final String apiKey = "gVgtP1rdHAtF47oNU8kQilnvTA22Nn9VjwxRdmX0fppPBtvIrAWN8SYt";
    private final String baseUrl = "https://api.pexels.com/v1/";

    private RecyclerView recycler;
    private TextInputEditText searchInput;
    private TextView pageNumber;
    private ImageButton nextPage, previousPage;
    private ImageResult lastResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        // init views
        recycler = findViewById(R.id.images_recycler);
        searchInput = findViewById(R.id.searchEditText);
        pageNumber = findViewById(R.id.page_number);
        nextPage = findViewById(R.id.next_page);
        previousPage = findViewById(R.id.previous_page);

        nextPage.setOnClickListener(view -> {
            // go to the next page
            if (lastResult != null) goToPage(lastResult.getNext_page());
            else Toast.makeText(this, "There is no next page !!", Toast.LENGTH_SHORT).show();
        });

        previousPage.setOnClickListener(view -> {
            // go to the previous page
            if (lastResult != null) goToPage(lastResult.getNext_page());
            else Toast.makeText(this, "There is no next page !!", Toast.LENGTH_SHORT).show();
        });

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_NEXT) {
                    String query = searchInput.getText().toString();
                    search(query);
                    Toast.makeText(ImagesActivity.this, "Searching for " + query, Toast.LENGTH_SHORT).show();

                    // Get the input method manager
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    // Hide the keyboard
                    View currentFocus = getCurrentFocus();
                    if (currentFocus != null)
                        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);

                    searchInput.clearFocus();

                    return true;
                }
                return false;
            }
        });

        search("");
    }

    private void search(String query) {
        OkHttpClient client = new OkHttpClient();
        String url;
        if (query.isEmpty()) url = baseUrl + "curated?per_page=50";
        else url = baseUrl + "search?query=" + query + "&per_page=50";
        Request request = new Request.Builder().url(url).addHeader("Authorization", apiKey).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(ImagesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        // get the result and parse it into java objects
                        ImageResultParser resultParser = new ImageResultParser();
                        JSONObject json = new JSONObject(body);
                        ImageResult result = resultParser.parser(json);
                        lastResult = result;

                        // display the page number
                        runOnUiThread(() -> pageNumber.setText(result.getPage() + ""));

                        // display the page result
                        runOnUiThread(() -> display(result));
                    } catch (JSONException e) {
                        runOnUiThread(() ->Toast.makeText(ImagesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }
            }
        });
    }

    private void goToPage(String pageUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(pageUrl).addHeader("Authorization", apiKey).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(ImagesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    if (body.isEmpty()) {
                        Toast.makeText(ImagesActivity.this, "No response !!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        // get the result and parse it into java objects
                        ImageResultParser resultParser = new ImageResultParser();
                        JSONObject json = new JSONObject(body);
                        ImageResult result = resultParser.parser(json);
                        lastResult = result;

                        // display the page number
                        runOnUiThread(() -> pageNumber.setText(result.getPage() + ""));

                        // display the page result
                        runOnUiThread(() -> display(result));
                    } catch (JSONException e) {
                        Toast.makeText(ImagesActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    private void display(ImageResult result) {
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        recycler.setAdapter(new ImageAdapter(getApplicationContext(), result.getPhotos()));
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
        public Context context;
        public ArrayList<Image> images;

        public ImageAdapter(Context context, ArrayList<Image> images) {
            this.context = context;
            this.images = images;
        }

        @NonNull
        @Override
        public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.image_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

            holder.textView.setText(images.get(position).getAlt());
            Picasso.get().load(images.get(position).getSrc().getLarge()).into(holder.imageView);

            holder.itemView.setOnClickListener(view -> {
                // open the image in the preview screen
                Image image = images.get(position);
                Intent intent = new Intent(context, PreviewImageActivity.class);
                intent.putExtra("title", image.getAlt());
                intent.putExtra("url", image.getSrc().getOriginal());
                startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public class ImageHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public ImageHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image_cardview);
                textView = itemView.findViewById(R.id.image_title);
            }
        }
    }
}