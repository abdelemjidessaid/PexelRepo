package com.sharpminded.pexelrepo.ImageModel;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageParser {

    static ArrayList<Image> parser(JSONArray imageArray) {
        ArrayList<Image> imageList = new ArrayList<>();

        try {
            for (int i = 0; i < imageArray.length(); i++) {
                JSONObject json = imageArray.getJSONObject(i);

                long id = json.getLong("id");
                long width = json.getLong("width");
                long height = json.getLong("height");
                long photographer_id = json.getLong("photographer_id");
                String url = json.getString("url");
                String photographer = json.getString("photographer");
                String photographer_url = json.getString("photographer_url");
                String avg_color = json.getString("avg_color");
                Src src = SrcParser.parser(json.getJSONObject("src"));
                boolean liked = json.getBoolean("liked");
                String alt = json.getString("alt");

                Image image = new Image(id, width, height, url, photographer, photographer_url, photographer_id, avg_color, src, liked, alt);
                imageList.add(image);
            }

        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return imageList;
    }
}
