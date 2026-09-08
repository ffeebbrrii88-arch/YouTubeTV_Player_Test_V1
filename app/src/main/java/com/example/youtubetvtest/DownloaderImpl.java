package com.example.youtubetvtest;

import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.downloader.Request;
import org.schabi.newpipe.extractor.downloader.Response;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;

public class DownloaderImpl extends Downloader {

    private static DownloaderImpl instance;
    private final OkHttpClient client;

    private DownloaderImpl(OkHttpClient client) {
        this.client = client;
    }

    public static DownloaderImpl init(OkHttpClient client) {
        if (instance == null) {
            instance = new DownloaderImpl(client);
        }
        return instance;
    }

    @Override
    public Response execute(Request request) throws IOException {

        Builder builder = new Builder()
                .url(request.url())
                .header(
                    "User-Agent",
                    "Mozilla/5.0 (Android)"
                );

        for (Map.Entry<String, java.util.List<String>> h :
                request.headers().entrySet()) {

            for (String value : h.getValue()) {
                builder.addHeader(h.getKey(), value);
            }
        }

        okhttp3.Response response =
                client.newCall(builder.build()).execute();

        String body =
                response.body() != null ?
                response.body().string() : "";

        return new Response(
                response.code(),
                response.message(),
                response.headers().toMultimap(),
                body,
                request.url()
        );
    }
}
