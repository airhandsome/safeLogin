package com.example.airhandsome.safelogin;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.file.upload.UploadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView results = null;
    String type = null;
    EditText name, pass, ip;
    SensorManager sm;
    Button submit, register, znz;
    MySensorEventListener sensorEventListener;
    RadioButton train;
    Intent intent;
    ImageView img;
    float ori[][], gyro[][], acc[][];
    long starttime, tmp = 0;
    boolean nostart = false;
    IpNumber ipadderss = new IpNumber();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img = (ImageView) findViewById(R.id.regist);
        results = (TextView) findViewById(R.id.edt1);
        
        submit = (Button)findViewById(R.id.submit);
        register = (Button)findViewById(R.id.register);
        znz = (Button)findViewById(R.id.change);
        train = (RadioButton) findViewById(R.id.train);
        if(train.isChecked()) type = "train"; else type = "predict";
        sensorEventListener = new MySensorEventListener();
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        ori = new float[3][300];
        acc = new float[3][300];
        gyro = new float[3][300];
        name = (EditText) findViewById(R.id.userNameText);
        pass = (EditText) findViewById(R.id.passwdText);
        ip = (EditText) findViewById(R.id.ip);
        ip.setText("192.168.123.1");
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pass.setText("");
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip_num =  ip.getText().toString();
                String url = "http://"+ip_num+":8080/androidH//fileUpload.action";
                UploadFile up = new UploadFile();
                String[] road = { "/sdcard/safeLogin/newacc.txt", "/sdcard/safeLogin/newgyso.txt",
                        "/sdcard/safeLogin/newori.txt" };
                String psd = pass.getText().toString();
                String names = name.getText().toString();
                results.setText("开始执行");
                try {
                    String result = up.send(url, road, names, psd,"predict","true");
                    results.setText("返回结果是"+result);
                } catch (IOException e) {
                    e.printStackTrace();
                    results.setText("出异常了");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, register.class);
                MainActivity.this.startActivity(intent);
            }
        });
        znz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             // stoped();
                ipadderss.setIp(ip.getText().toString());
            }
        });
    }
    //暂停传感器的捕获

    public void stoped(){
        sm.unregisterListener(sensorEventListener);
        write("\r\n", "newtime");
        write("\r\n", "newgyso");
        write("\r\n", "gysotime");
        write("\r\n", "newori");
        write("\r\n", "oritime");
        write("\r\n", "newacc");
        tmp = 0;
    }
    public void restart(){
        starttime =System.currentTimeMillis();
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
    private final class MySensorEventListener implements SensorEventListener
    {
        //可以得到传感器实时测量出来的变化值
        @Override
        public void onSensorChanged(SensorEvent event)
        {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                String str = x + ", " + y + ", " + z + "; ";
                write(str, "newacc");
                write(String.valueOf(currentTime - starttime) + ", ", "newtime");
            }else  if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                String str = x + ", " + y + ", " + z + "; ";
                write(str, "newori");
                write(String.valueOf(currentTime - starttime) + ", ", "oritime");
            }else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                String str = x + ", " + y + ", " + z + "; ";
                write(str, "newgyso");
                write(String.valueOf(currentTime - starttime) + ", ", "gysotime");
            }
        }
        //重写变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
        }
    }
        private void write(String str, String name){
            try{
                File file = new File("/sdcard/safeLogin/"+name+".txt");
                if (!file.exists()){
                    file.createNewFile();
                }
                BufferedWriter output = new BufferedWriter (new OutputStreamWriter(new FileOutputStream (file, true), "UTF-8"));
                output.write(str);
                output.close();
                //  FileOutputStream fos = openFileOutput(file, MODE_APPEND);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

}
