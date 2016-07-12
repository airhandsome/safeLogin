package com.example.airhandsome.safelogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.file.upload.UploadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by airhandsome on 2016/3/14.
 */
public class register extends Activity {
    private EditText name, pass1, pass2;
    private Button reset, regist;
    private Intent intent;
    long starttime, tmp = 0;
    SensorManager sm;
    MySensorEventListener sensorEventListener;
    IpNumber ipaddress = new IpNumber();
    String ip = ipaddress.getIp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        name = (EditText) findViewById(R.id.name);
        pass1 = (EditText) findViewById(R.id.pass1);
        pass2 = (EditText) findViewById(R.id.pass2);
        reset = (Button) findViewById(R.id.reset);
        regist = (Button) findViewById(R.id.regist);
        sensorEventListener = new MySensorEventListener();
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        pass1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                           boolean nostart = false;

                                           @Override
                                           public void onFocusChange(View v, boolean hasFocus) {
                                               if (hasFocus) {
                                                   Toast.makeText(getApplicationContext(), "开启了传感器", Toast.LENGTH_SHORT);
                                                   restart();
                                                   nostart = true;
                                               } else if (nostart) {
                                                   Toast.makeText(getApplicationContext(), "关闭了传感器", Toast.LENGTH_SHORT);
                                                   stoped();
                                                   nostart = false;
                                               }
                                           }
                                       }
        );

        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                           boolean nostart = false;

                                           @Override
                                           public void onFocusChange(View v, boolean hasFocus) {
                                               if (hasFocus) {
                                                   Toast.makeText(getApplicationContext(), "开启了传感器", Toast.LENGTH_SHORT);
                                                   restart();
                                                   nostart = true;
                                               } else if (nostart) {
                                                   Toast.makeText(getApplicationContext(), "关闭了传感器", Toast.LENGTH_SHORT);
                                                   stoped();
                                                   nostart = false;
                                               }
                                           }
                                       }
        );
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                pass1.setText("");
                pass2.setText("");
            }
        });


        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass1.getText().toString().equals(pass2.getText().toString())) {
                    Toast.makeText(v.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    String psd = pass1.getText().toString();
                    String names = name.getText().toString();
                    String url = "http://"+ip+":8080/androidH//fileUpload.action";
                    System.out.println("url"+url);
                    UploadFile up = new UploadFile();
                    String[] road = { "/sdcard/safeLogin/oldacc.txt", "/sdcard/safeLogin/oldgyso.txt",
                            "/sdcard/safeLogin/oldori.txt" };
                    name.setText("开始执行");
                    try {
                        String result = up.send(url, road, "names", psd,"train","true");
                        name.setText("返回结果是" + result);
                    } catch (IOException e) {
                        e.printStackTrace();
                        name.setText("出异常了");
                    }
                    TimerTask task = new TimerTask() {
                        public void run() {
                            intent = new Intent(register.this, MainActivity.class);
                            startActivity(intent);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 1000);

                } else {
                    Toast.makeText(v.getContext(), "两次输入的密码不一样，请重新输入", Toast.LENGTH_SHORT).show();
                    pass1.setText("");
                    pass2.setText("");
                }
            }
        });
    }

    public void stoped() {
        sm.unregisterListener(sensorEventListener);
        write("\r\n", "acctime");
        write("\r\n", "oldgyso");
        write("\r\n", "gysotime");
        write("\r\n", "oldori");
        write("\r\n", "oritime");
        write("\r\n", "oldacc");
        tmp = 0;
    }

    public void restart() {
        starttime = System.currentTimeMillis();
        //获取方向传感器
        Sensor orientationSensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sm.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_UI);

        //获取加速度传感器
        Sensor accelerometerSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);


        //获取陀螺仪传感器
        Sensor gyroscopeSensor = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sm.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private final class MySensorEventListener implements SensorEventListener {
        //可以得到传感器实时测量出来的变化值
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                String str = x + ", " + y + ", " + z + "; ";
                write(str, "oldacc");
                write(String.valueOf(currentTime - starttime) + ", ", "acctime");
            } else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                String str = x + ", " + y + ", " + z + "; ";
                write(str, "oldori");
                write(String.valueOf(currentTime - starttime) + ", ", "oritime");
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                String str = x + ", " + y + ", " + z + "; ";
                write(str, "oldgyso");
                write(String.valueOf(currentTime - starttime) + ", ", "gysotime");
            }
        }

        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    private void write(String str, String name) {
        try {
            File file = new File("/sdcard/safeLogin/" + name + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            output.write(str);
            output.close();
            //  FileOutputStream fos = openFileOutput(file, MODE_APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
