package com.example.airhandsome.safelogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by airhandsome on 2016/3/9.
 */
public class Test extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button btn = (Button) findViewById(R.id.bt);
        btn.setText("你好,are you ok？");
    }
}
