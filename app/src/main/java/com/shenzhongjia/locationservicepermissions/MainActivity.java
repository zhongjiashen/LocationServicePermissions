package com.shenzhongjia.locationservicepermissions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLocationEnabled()) {
                    Toast.makeText(MainActivity.this, "拥有一切权限，可以正常使用！", Toast.LENGTH_SHORT).show();
                } else {
                    dialog();
                }
            }
        });
    }
    protected void dialog() {
        AlertDialog.Builder builder = null;
        if(builder==null) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("定位服务未开启!请开启定位服务!否则该页面功能无法正常使用！");

            builder.setTitle("提示");

            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });

            builder.setNegativeButton("退出该页面", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
        builder.create().show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!isLocationEnabled()) {
            dialog();
      } /*else {
            Toast.makeText(this, "定位服务未开启!请开启定位服务", Toast.LENGTH_SHORT).show();

        }*/
    }
    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
