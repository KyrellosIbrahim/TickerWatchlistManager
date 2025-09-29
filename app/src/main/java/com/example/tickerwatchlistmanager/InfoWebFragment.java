package com.example.tickerwatchlistmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfoWebFragment extends Fragment {
    WebView wv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_web, container, false);
        wv = view.findViewById(R.id.webview);

        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.seekingalpha.com/");

        return view;
    }

    public void loadUrlForTicker(String ticker) {
        String url = "https://seekingalpha.com/symbol/" + ticker.toUpperCase();
        wv.loadUrl(url);
    }
}