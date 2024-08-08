package com.example.business.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.business.Activity.MainActivity;
import com.example.business.R;
import com.example.business.UserDao;
import com.example.business.User;

public class register extends AppCompatActivity {

    private static final String TAG = "postgresql-register";
    EditText userAccount = null;
    EditText userPassword = null;
    EditText userName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userAccount = findViewById(R.id.editTextTextPersonName);
        userPassword = findViewById(R.id.editTextTextPasswordName);
        userName = findViewById(R.id.editTextTextPersonName2);
    }
    public void gotologin (View v) {
        String userAccount1 = userAccount.getText().toString();
        String userPassword1 = userPassword.getText().toString();
        String userName1 = userName.getText().toString();


        User user = new User();

        user.setUserAccount(userAccount1);
        user.setUserPassword(userPassword1);
        user.setUserName(userName1);

        new Thread(){
            @Override
            public void run() {

                int msg = 0;
                UserDao userDao = new UserDao();

                User uu = userDao.findUser(user.getUserAccount());
                if(uu != null){
                    msg = 1;
                }
                else{
                    boolean flag = userDao.register(user);
                    if(flag){
                        msg = 2;
                    }
                }
                hand.sendEmptyMessage(msg);

            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler()
    {
        public void handleMessage(Message msg) {

            if(msg.what == 0) {
                Toast.makeText(getApplicationContext(),"註冊失敗",Toast.LENGTH_LONG).show();
            } else if(msg.what == 1) {
                Toast.makeText(getApplicationContext(),"帳號已存在",Toast.LENGTH_LONG).show();
            } else if(msg.what == 2) {
                Toast.makeText(getApplicationContext(), "註冊成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                //将想要传递的数据用putExtra封装在intent中
                intent.putExtra("a","註冊");
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        }
    };
}