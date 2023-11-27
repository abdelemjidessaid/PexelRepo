package com.sharpminded.pexelrepo.ImageModel;

import org.json.JSONObject;

public class SrcParser {

    static Src parser(JSONObject json) {
        Src src = null;

        try {
            String original = json.getString("original");
            String large2x  = json.getString("large2x");
            String large = json.getString("large");
            String medium = json.getString("medium");
            String small = json.getString("small");
            String portrait = json.getString("portrait");
            String landscape = json.getString("landscape");
            String tiny = json.getString("tiny");

            src = new Src(original, large2x, large, medium, small, portrait, landscape, tiny);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return src;
    }
}
