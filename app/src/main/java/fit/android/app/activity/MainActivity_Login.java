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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        //variables used
        tvRegister = findViewById(R.id.tvLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        //room database
        userDAO = AppDatabase.getDatabase(this).userDAO();

        //init app no user then get data user
        if(userDAO.getAll().isEmpty()) {
            getDataUserFromFirebaseSaveToRoomDatabase();
        }

        //call func
        appEventHandle();
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
                            Message.showMessage(MainActivity_Login.this, "Message", "Login fail. Email or password not true!");
                        }
                    }
                });

    }

    private void getDataUserFromFirebaseSaveToRoomDatabase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sn : snapshot.getChildren()) {
                    User user = sn.getValue(User.class);
                    try{
                        userDAO.insert(user);
                    }catch (Exception e) {
                        Log.e("ERROR:", "INSERT FAIL");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }

    private void appEventHandle() {
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
}