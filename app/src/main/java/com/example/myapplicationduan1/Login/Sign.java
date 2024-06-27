package com.example.myapplicationduan1.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationduan1.LopDao.NVDao;
import com.example.myapplicationduan1.R;

public class Sign extends AppCompatActivity {
    NVDao nvDao;
    EditText ed_username, ed_fullname, ed_password, ed_repassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        nvDao = new NVDao(this);
        ed_username = findViewById(R.id.ed_username);
        ed_fullname = findViewById(R.id.ed_fullname);
        ed_password = findViewById(R.id.ed_password);
        ed_repassword = findViewById(R.id.ed_repassword);

        // Xử lý sự kiện khi nhấn nút Đăng ký
        Button btn_sign = findViewById(R.id.login_btnsign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_username.getText().toString();
                String fullname = ed_fullname.getText().toString();
                String password = ed_password.getText().toString();
                String repassword = ed_repassword.getText().toString();

                if (username.isEmpty() && fullname.isEmpty() && password.isEmpty() && repassword.isEmpty()) {
                    Toast.makeText(Sign.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if (repassword.compareTo(password) == 0) {
                        if (validate(password)) {
                            nvDao.Sign(username,fullname, password);
                            Toast.makeText(Sign.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Sign.this, Login.class));
                        } else {
                            Toast.makeText(Sign.this, "Mật khẩu không quá 8 kí tự", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Sign.this, "Mật khẩu và Nhập lại mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button btn_back = findViewById(R.id.login_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign.this, Login.class);
                startActivity(intent);
            }
        });



    }
    private static boolean validate(String pass){
        if (pass.length() > 8) {
            return false;
        }
        return true;
    }
}