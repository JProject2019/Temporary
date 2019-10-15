package ite.smu.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.util.Date;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"));
        abortBroadcast();
        Bundle bundle = intent.getExtras();

        Object messages[] = (Object[])bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        int smsCount = messages.length;
        for(int i=0; i < smsCount; i++){
            smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
        }

        Date curDate = new Date(smsMessage[0].getTimestampMillis());

        String origNumber = smsMessage[0].getOriginatingAddress();
        String message = smsMessage[0].getMessageBody().toString();

        Intent intent1 = new Intent(context, SmsDisplayActivity.class);
        intent1.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);

        intent1.putExtra("number", origNumber);
        intent1.putExtra("message", message);
        intent1.putExtra("timestamp", curDate.toString());
        context.startActivity(intent1);
    }
}
