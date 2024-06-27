package com.example.myapplicationduan1.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplicationduan1.LopDao.NVDao;
import com.example.myapplicationduan1.LopModel.NhanVien;
import com.example.myapplicationduan1.MainActivity;
import com.example.myapplicationduan1.R;

public class Login extends AppCompatActivity {
    private AppCompatButton btn_login, btn_clear;
    private EditText ed_user, ed_pass;
    Intent intent;
    NVDao nvdao;
    CheckBox chk_remember;
    TextView tv_forgot,tv_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btn_login = findViewById(R.id.login_btn);
        btn_clear = findViewById(R.id.btn_clear);
        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);
        chk_remember = findViewById(R.id.chk_remember);
        tv_forgot = findViewById(R.id.tv_forgot);
        tv_sign = findViewById(R.id.tv_sign);
        tv_sign.setPaintFlags(tv_sign.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Sign.class));
                finish();
            }
        });
        nvdao = new NVDao(this);
        ed_user = findViewById(R.id.ed_user);
        ed_pass = findViewById(R.id.ed_pass);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        Button btn_login = findViewById(R.id.login_btn);
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String user = ed_user.getText().toString();
//                String pass = ed_pass.getText().toString();
//
//                if (!user.isEmpty() && !pass.isEmpty()) {
//                    if (nvdao.getlogin(user, pass) > 0) {
//                        // Xử lý đăng nhập thành công
//                        // Chuyển sang activity Main hoặc AdminActivity tùy vào quyền truy cập
//                        Intent intent = new Intent(Login.this, MainActivity.class);
//                        intent.putExtra("admintion", user);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        // Đăng nhập thất bại
//                        Toast.makeText(Login.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Thông báo nếu username hoặc password trống
//                    Toast.makeText(Login.this, "Vui lòng nhập tên đăng nhập và mật khẩu!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        btn_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishAffinity();
//            }
//        });
        nvdao = new NVDao(this);
//        NhanVien nhanVien = nvdao.getUser("admin");
        nvdao.OPEN();
        if (nvdao.getUserName("admin") < 0) {
            nvdao.ADDNV(new NhanVien("admin", "admin", "admin"));
        }
        // Đọc Sharepreferences
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        ed_user.setText(preferences.getString("USERNAME", ""));
        ed_pass.setText(preferences.getString("PASSWORD", ""));
        chk_remember.setChecked(preferences.getBoolean("REMEMBER", false));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checklogin();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    public void checklogin() {
        String usered = ed_user.getText().toString();
        String passed = ed_pass.getText().toString();
        if (usered.isEmpty() || passed.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (nvdao.getlogin(usered, passed) > 0 || (usered.equalsIgnoreCase("admin") && passed.equalsIgnoreCase("admin"))
                    || (usered.equalsIgnoreCase("user") && passed.equalsIgnoreCase("user"))) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(usered, passed, chk_remember.isChecked());
                startActivity(intent = new Intent(Login.this, MainActivity.class).putExtra("admintion", usered));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại ! " +
                        "\nSai tài khoản,mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String user, String pass, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            // Xóa lưu trữ trước đó
            editor.clear();
        } else {
            // Lưu dữ liệu
            editor.putString("USERNAME", user);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }
        // LƯu lại toàn bộ dữ liệu
        editor.commit();
    }

}
