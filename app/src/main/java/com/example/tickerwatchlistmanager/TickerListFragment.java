package com.example.tickerwatchlistmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TickerListFragment extends Fragment {
    private static List<String> tickers;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private static final int MAX_TICKERS = 5;
    public TickerListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        defaultTickers();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);

        lv = view.findViewById(R.id.tickerlist);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tickers);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ticker = tickers.get(i);
                InfoWebFragment ifw = (InfoWebFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.InfoWebFragment);

                if(ifw != null) {
                    ifw.loadUrlForTicker(ticker);
                }
            }
        });
        return view;
    }

    private void defaultTickers() {
        if (tickers == null) {
            tickers = new ArrayList<>();
            tickers.add("NEE");
            tickers.add("AAPL");
            tickers.add("DIS");
        }
    }

    public void addTicker(String ticker) {
        if (tickers.size() <= MAX_TICKERS && !tickers.contains(ticker.toUpperCase())) {
            tickers.add(ticker.toUpperCase());
        } else {
            tickers.set(5, ticker.toUpperCase());
        }

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}