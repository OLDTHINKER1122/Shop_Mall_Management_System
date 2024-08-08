package com.example.business.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.os.Handler;
import android.widget.Toast;

import com.example.business.R;
import com.example.business.UserDao;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void gotoUser(View v) {
        EditText EditTextAccount = findViewById(R.id.editTextTextPersonName);
        EditText EditTextPassword = findViewById(R.id.editTextTextPasswordName);

        new Thread() {
            @Override
            public void run() {
                UserDao userDao = new UserDao();
                int msg = userDao.login(EditTextAccount.getText().toString(), EditTextPassword.getText().toString());
                hand1.sendEmptyMessage(msg);
                if (msg == 1){

                    Intent intent = new Intent(getApplicationContext(), user.class);
                    Bundle bundle = new Bundle();
                    String name = userDao.findUserName(EditTextAccount.getText().toString());
                    bundle.putString("name",name);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            }
        }.start();
    }

    public void gotoRegister(View v) {
        startActivity(new Intent(getApplicationContext(), register.class));
    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2){
                Toast.makeText(getApplicationContext(), "密碼錯誤", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3){
                Toast.makeText(getApplicationContext(), "帳號不存在", Toast.LENGTH_LONG).show();
            }
        }
    };

}