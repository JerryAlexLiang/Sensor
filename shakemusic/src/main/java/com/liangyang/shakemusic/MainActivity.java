package com.liangyang.shakemusic;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    public static Boolean flag = false;
    private int ringValue = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text_view);
        //初始化传感器
        initSensor();

    }

    /**
     * 初始化传感器
     */
    private void initSensor() {
        //初始化传感器管理器实例
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获得传感器的类型，这里获得的类型是加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //此方法用来注册，只有注册过才会生效
        //参数：SensorEventListener的实例，Sensor的实例，更新速率
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                //mTextView.setText("x=" + x + ",y=" + y + ",z=" + z);

                if ((Math.abs(x)+Math.abs(y)+Math.abs(z))>=ringValue && flag==false){
                    flag=true;
                    mTextView.setVisibility(View.VISIBLE);
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.a);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.release();
                            flag=false;
                            mTextView.setVisibility(View.GONE);
                        }
                    });
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
