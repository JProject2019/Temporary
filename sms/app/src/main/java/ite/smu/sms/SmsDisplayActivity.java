package ite.smu.sms;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.view.Window;
import android.content.Intent;

public class SmsDisplayActivity extends Activity {

    Button titleButton;
    Button closeButton;
    TextView messageTextView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sms_display);

        titleButton = findViewById(R.id.titleButton);
        closeButton = findViewById(R.id.closeButton);
        messageTextView = findViewById(R.id.messageTextView);

        closeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });


        Intent passedIntent = getIntent();
        if(passedIntent != null){
            processIntent(passedIntent);
        }
    }

    protected void onNewIntent(Intent intent){
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        String number = intent.getStringExtra("number");
        String message = intent.getStringExtra("message");
        String timestamp = intent.getStringExtra("timestamp");

        if(number != null){
            titleButton.setText(number + "number");
            messageTextView.setText("[" + timestamp + "]" + message);
            messageTextView.invalidate();
        }
    }
}
