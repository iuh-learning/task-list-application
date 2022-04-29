package fit.android.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fit.android.app.R;

public class MainActivity_Login extends AppCompatActivity {

    private TextView tvRegister;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //variables used
        tvRegister = findViewById(R.id.tvLogin);
        btnLogin = findViewById(R.id.btnLogin);

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
                Intent intent = new Intent(getApplicationContext(), MainActivity_TaskList.class);
                startActivity(intent);
            }
        });
    }
}