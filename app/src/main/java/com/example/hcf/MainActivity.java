package com.example.hcf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hcf.databinding.ActivityMainBinding;
import com.example.hcf.model.User;

public class MainActivity extends AppCompatActivity {
    public User user;


    private void saveUser(User user){
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name",user.getUser_name());
        editor.putString("user_passwd", user.getUser_password());
        editor.apply();
    }

    private User readUser(){
        User user = new User();
        SharedPreferences sharedPreferences =
                getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        user.setUser_name(sharedPreferences.getString("user_name",""));
        user.setUser_password(sharedPreferences.getString("user_passwd",""));
        return user;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        mainBinding.signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                String inputName = mainBinding.nameInput.getText().toString();
                String inputPasswd = mainBinding.passwordInput.getText().toString();
                if (!inputName.isEmpty() && !inputPasswd.isEmpty()) {
                    user.setUser_name(inputName);
                    user.setUser_password(inputPasswd);
                    saveUser(user);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mainBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                String inputName = mainBinding.nameInput.getText().toString();
                String inputPasswd = mainBinding.passwordInput.getText().toString();
                User user = readUser();
                if (!inputName.isEmpty() && !inputPasswd.isEmpty()) {
                    if (!inputName.equals(user.getUser_name()) || !inputPasswd.equals(user.getUser_password())) {
                        Toast.makeText(MainActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        intent = new Intent(view.getContext(), ImageViewActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
