package com.example.airhandsome.safelogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by airhandsome on 2016/3/14.
 */
public class loginFail extends Activity {
    public Button fail_yes, fail_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fail);

        fail_yes = (Button) findViewById(R.id.fail_yes);
        fail_no = (Button) findViewById(R.id.fail_no);
        fail_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据写入正数据
            }
        });

        fail_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将数据写入负数据
            }
        });
    }
}
