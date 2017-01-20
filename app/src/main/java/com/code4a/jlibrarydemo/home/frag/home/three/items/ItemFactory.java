package com.code4a.jlibrarydemo.home.frag.home.three.items;

import android.app.Activity;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;

import java.io.IOException;

public class ItemFactory {

    public static BaseVideoItem createItemFromAsset(String title, String assetName, int imageResource, Activity activity, VideoPlayerManager<MetaData> videoPlayerManager) throws IOException {
        return new AssetVideoItem(title, activity.getAssets().openFd(assetName), videoPlayerManager, Picasso.with(activity), imageResource);
    }

    public static BaseVideoItem createItemFromUri(String videoName, String url, int videoPic, Activity activity, VideoPlayerManager<MetaData> videoPlayerManager) throws IOException {
        return new DirectLinkVideoItem(videoName, url, videoPlayerManager, Picasso.with(activity), videoPic);
    }
}
