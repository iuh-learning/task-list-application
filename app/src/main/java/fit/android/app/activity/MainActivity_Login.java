package fit.android.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fit.android.app.R;
import fit.android.app.dao.UserDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.helper.Message;
import fit.android.app.model.User;

public class MainActivity_Login extends AppCompatActivity {

    private TextView tvRegister;
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //variables used
        tvRegister = findViewById(R.id.tvLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        //get database
        AppDatabase database = AppDatabase.getDatabase(this);

        //get userDAO use method
        UserDAO userDAO = database.userDAO();

        //event click text view move register display
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Register.class);
                startActivity(intent);
            }
        });

        //event click text view move register display
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                Toast.makeText(MainActivity_Login.this, userDAO.getAll().get(0).getEmail() + " - "+ userDAO.getAll().get(0).getPassword() + "", Toast.LENGTH_SHORT).show();

                if(email.equals("") || password.equals("")) {
                    Message.showMessage(MainActivity_Login.this, "Thông báo", "Vui lòng nhập đầy đủ thông tin để thực hiện đăng nhập!");
                }else {
                    User user = userDAO.findByEmail(email);
                    if(user == null) {
                        Message.showMessage(MainActivity_Login.this, "Thông báo", "Tài khoản không tồn tại!");
                    }else {
                        if(!user.getPassword().equals(password)) {
                            Message.showMessage(MainActivity_Login.this, "Thông báo", "Mật khẩu không đúng!");
                        }else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity_TaskList.class);
                            intent.putExtra("user_email", email);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

}