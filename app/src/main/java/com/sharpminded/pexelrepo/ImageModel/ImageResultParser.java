package com.sharpminded.pexelrepo.ImageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageResultParser {

    public ImageResult parser(JSONObject json) {
        ImageResult result = null;

        try {
            long total_results = json.getLong("total_results");
            long page = json.getLong("page");
            long per_page = json.getLong("per_page");
            ArrayList<Image> photos = ImageParser.parser(json.getJSONArray("photos"));
            String next_page = json.getString("next_page");

            result = new ImageResult(total_results, page, per_page, photos, next_page);
        } catch (JSONException e) {
            return null;
        }

        return result;
    }
}
