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
    private List<String> tickers = new ArrayList<String>();
    private ListView lv;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tickers);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ticker = tickers.get(i);
                InfoWebFragment ifw = (InfoWebFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.InfoWebFragment);

                assert ifw != null;
                ifw.loadUrlForTicker(ticker);
            }
        });
        return view;
    }

    private void defaultTickers() {
        tickers.add("NEE");
        tickers.add("AAPL");
        tickers.add("DIS");
    }
}