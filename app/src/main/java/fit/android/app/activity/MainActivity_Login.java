package fit.android.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fit.android.app.R;
import fit.android.app.dao.UserDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.helper.Message;
import fit.android.app.model.User;

public class MainActivity_Login extends AppCompatActivity {
    private final String TAG = ">>>>>>>>MESSAGE APP: ";
    private TextView tvRegister;
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //variables used
        tvRegister = findViewById(R.id.tvLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

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

                if(email.equals("") || password.equals("")) {
                    Message.showMessage(MainActivity_Login.this, "Message", "Please enter all the information to login!");
                }else {
                    signInWithFirebase(email, password);
                }
            }
        });
    }

    private void signInWithFirebase(String email, String password) {
        //init firebase auth
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            //send data to  MainActivity_TaskList
                            Intent intent = new Intent(getApplicationContext(), MainActivity_TaskList.class);
                            intent.putExtra("user_email", email);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity_Login.this, "Login fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}