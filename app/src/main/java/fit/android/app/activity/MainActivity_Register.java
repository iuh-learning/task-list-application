package fit.android.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fit.android.app.R;
import fit.android.app.dao.UserDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.helper.Message;
import fit.android.app.helper.MessageBoxListener;
import fit.android.app.model.User;

public class MainActivity_Register extends AppCompatActivity {

    private TextView tvlogIn;
    private EditText txtFullNameRegister;
    private EditText txtEmailRegister;
    private EditText txtPasswordRegister;
    private EditText txtConfirmPasswordRegister;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        //variables used
        tvlogIn = findViewById(R.id.tvLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtFullNameRegister = findViewById(R.id.txtFullName);
        txtEmailRegister = findViewById(R.id.txtEmailRegister);
        txtPasswordRegister = findViewById(R.id.txtPasswordRegister);
        txtConfirmPasswordRegister = findViewById(R.id.txtConfirmRegister);

        //get database from AppDatabase
        AppDatabase database = AppDatabase.getDatabase(this);

        //get userDAO use method
        UserDAO userDAO = database.userDAO();

        //event click text view move log in display
        tvlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Login.class);
                startActivity(intent);
            }
        });

        //event click button register then add user into SQLite
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = txtFullNameRegister.getText().toString().trim();
                String email = txtEmailRegister.getText().toString().trim();
                String password = txtPasswordRegister.getText().toString().trim();
                String confirmPass = txtConfirmPasswordRegister.getText().toString().trim();

                if(fullName.equals("") || email.equals("") || password.equals("") || confirmPass.equals("")) {
                    Message.showMessage(MainActivity_Register.this,
                                "Thông báo", "Vui lòng nhập đẩy đủ thông tin!");
                }else {
                    if(confirmPass.equals(password)) {
                        User user = new User(fullName, email, password);
                        userDAO.insert(user);
                        Message.showMessage(MainActivity_Register.this,"Thông báo", "Tạo tài khoản thành công!");
                    }else {
                        Message.showMessage(MainActivity_Register.this,"Thông báo", "Mật khẩu nhập lại phải giống mật khẩu đã nhập!");
                    }
                }
            }
        });
    }


}