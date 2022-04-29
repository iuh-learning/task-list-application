package fit.android.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fit.android.app.R;

public class MainActivity_Register extends AppCompatActivity {

    private TextView tvlogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        //variables used
        tvlogIn = findViewById(R.id.tvLogin);

        //event click text view move log in display
        tvlogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_Login.class);
                startActivity(intent);
            }
        });
    }
}