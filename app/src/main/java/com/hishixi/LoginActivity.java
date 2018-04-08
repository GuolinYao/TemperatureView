package com.hishixi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by seamus on 2018/3/19 11:57
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;
    private HashMap<String, String> mAccMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mAccMap = new HashMap<>();
        mAccMap.put("admin", "123456");
        mAccMap.put("zstu", "123456");
    }

    private void initView() {
    }

    @OnClick(R.id.sign_in_button)
    void login() {
        String account = mAccount.getText().toString();
        String pwd = mPassword.getText().toString();
        if (mAccMap.containsKey(account) && mAccMap.get(account).equals(pwd)) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            CacheUtils.saveIfLogin(this, "1");
            Intent intent = new Intent(this, IndexActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
