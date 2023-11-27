package com.sharpminded.pexelrepo.VideoModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoParser {

    public ArrayList<Video> videoParser (JSONArray videos) {
        ArrayList<Video> list = new ArrayList<>();

        try {
            for (int i = 0; i < videos.length(); i++) {
                JSONObject json = videos.getJSONObject(i);

                long id = json.getLong("id");
                long width = json.getLong("width");
                long height = json.getLong("height");
                String url = json.getString("url");
                String image = json.getString("image");
                long duration = json.getLong("duration");
                VideoUser user = videoUserParser(json.getJSONObject("user"));
                ArrayList<VideoFile> video_files = videoFileParser(json.getJSONArray("video_files"));
                ArrayList<VideoThumbnail> video_pictures = videoThumbnailParser(json.getJSONArray("video_pictures"));

                Video video = new Video(id, width, height, url, image, duration, user, video_files, video_pictures);
                list.add(video);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public VideoUser videoUserParser(JSONObject json) {
        VideoUser user = null;
        try {
            long id = json.getLong("id");
            String name = json.getString("name");
            String url = json.getString("url");

            user = new VideoUser(id, name, url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public ArrayList<VideoThumbnail> videoThumbnailParser(JSONArray thumbnails) {
        ArrayList<VideoThumbnail> list = new ArrayList<>();
        try {
            for (int i = 0; i < thumbnails.length(); i++) {
                JSONObject json = thumbnails.getJSONObject(i);

                long id = json.getLong("id");
                String picture = json.getString("picture");
                long nr = json.getLong("nr");

                list.add(new VideoThumbnail(id, picture, nr));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public ArrayList<VideoFile> videoFileParser(JSONArray json) {
        ArrayList<VideoFile> videoFiles = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject videoJson = json.getJSONObject(i);

                long id = videoJson.getLong("id");
                String quality = videoJson.getString("quality");
                String file_type = videoJson.getString("file_type");
                long width = videoJson.getLong("width");
                long height = videoJson.getLong("height");
                String link = videoJson.getString("link");

                VideoFile videoFile = new VideoFile(id, quality, file_type, width, height, link);
                videoFiles.add(videoFile);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return videoFiles;
    }

    public VideoResult videoResultParser(JSONObject json) {
        VideoResult result = null;
        try {
            long page = json.getLong("page");
            long per_page = json.getLong("per_page");
            long total_results = json.getLong("total_results");
            String url = json.getString("url");
            ArrayList<Video> videos = videoParser(json.getJSONArray("videos"));

            result = new VideoResult(page, per_page, total_results, url, videos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}
