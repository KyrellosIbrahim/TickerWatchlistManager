package com.example.tickerwatchlistmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, 67);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        handleSmsIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleSmsIntent(intent);
    }

    private void handleSmsIntent(Intent intent) {
        if (intent != null) {
            // Check for invalid format
            if (intent.hasExtra("VALID_FORMAT")) {
                boolean validFormat = intent.getBooleanExtra("VALID_FORMAT", true);
                if (!validFormat) {
                    Toast.makeText(this, "No valid watchlist entry found", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            if (intent.hasExtra("INVALID_TICKER")) {
                boolean invalidTicker = intent.getBooleanExtra("INVALID_TICKER", false);
                if (invalidTicker) {
                    Toast.makeText(this, "Invalid ticker", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            if (intent.hasExtra("VALID_TICKER")) {
                boolean validTicker = intent.getBooleanExtra("VALID_TICKER", false);
                if (validTicker && intent.hasExtra("TICKER")) {
                    final String ticker = intent.getStringExtra("TICKER");

                    if (ticker != null && !ticker.isEmpty()) {
                        findViewById(R.id.main).post(new Runnable() {
                            @Override
                            public void run() {
                                TickerListFragment tlf =
                                        (TickerListFragment) getSupportFragmentManager()
                                                .findFragmentById(R.id.TickerListFragment);

                                if (tlf != null) {
                                    tlf.addTicker(ticker);
                                }

                                InfoWebFragment ifw =
                                        (InfoWebFragment) getSupportFragmentManager()
                                                .findFragmentById(R.id.InfoWebFragment);

                                if (ifw != null) {
                                    ifw.loadUrlForTicker(ticker);
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}