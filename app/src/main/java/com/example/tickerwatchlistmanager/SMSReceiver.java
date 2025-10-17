package com.example.tickerwatchlistmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null && intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String messageBody = smsMessage.getMessageBody();

                        Log.d(TAG, "Received SMS: " + messageBody);

                        processSmsMessage(context, messageBody);
                    }
                }
            }
        }
    }

    private void processSmsMessage(Context context, String message) {
        // Check if message matches the format: Ticker:<<TICKER>>
        if (message != null && message.matches("(?i)Ticker:<<[^>]+>>")) {
            int startIndex = message.indexOf("<<") + 2;
            int endIndex = message.indexOf(">>");
            String ticker = message.substring(startIndex, endIndex);

            // validate ticker
            if (isValidTicker(ticker)) {
                // Convert to uppercase
                ticker = ticker.toUpperCase();

                // Launch MainActivity with ticker
                Intent activityIntent = new Intent(context, MainActivity.class);
                activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.putExtra("TICKER", ticker);
                activityIntent.putExtra("VALID_TICKER", true);
                context.startActivity(activityIntent);
            } else {
                // invalid ticker - launch activity with error
                Intent activityIntent = new Intent(context, MainActivity.class);
                activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.putExtra("VALID_TICKER", false);
                activityIntent.putExtra("INVALID_TICKER", true);
                context.startActivity(activityIntent);
            }
        } else {
            // invalid format - launch activity with error
            Intent activityIntent = new Intent(context, MainActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("VALID_FORMAT", false);
            context.startActivity(activityIntent);
        }
    }

    private boolean isValidTicker(String ticker) {
        return ticker != null && !ticker.isEmpty() && ticker.matches("[a-zA-Z]+");
    }
}