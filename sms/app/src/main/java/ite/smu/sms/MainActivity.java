package ite.smu.sms;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();
    }

    private void checkDangerousPermissions(){
        String[] permissions = {
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int i=0; i<permissions.length; i++){
            permissionCheck = ContextCompat.checkSelfPermission(this,permissions[i]);
            if(permissionCheck == PackageManager.PERMISSION_DENIED){
                break;
            }
        }

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"권한 있음", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"권한 없음", Toast.LENGTH_LONG).show();
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[0])){
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(this,permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if(requestCode == 1){
            for(int i=0; i<permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, permissions[i] + "권한이 승인됨.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,permissions[i] + "권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
