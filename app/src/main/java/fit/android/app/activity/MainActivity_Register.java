package fit.android.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.app.Dialog;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fit.android.app.R;
import fit.android.app.dao.UserDAO;
import fit.android.app.database.AppDatabase;
import fit.android.app.helper.Message;
import fit.android.app.helper.MessageBoxListener;
import fit.android.app.model.User;

public class MainActivity_Register extends AppCompatActivity {
    private final String TAG = ">>>>> LOG MESSGAE: ";
    private TextView tvlogIn;
    private EditText txtFullNameRegister;
    private EditText txtEmailRegister;
    private EditText txtPasswordRegister;
    private EditText txtConfirmPasswordRegister;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private UserDAO userDAO;

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
        userDAO = database.userDAO();

        //call func
        appEventHandle();
    }

    private void clearInput() {
        txtFullNameRegister.setText("");
        txtEmailRegister.setText("");
        txtPasswordRegister.setText("");
        txtConfirmPasswordRegister.setText("");

        txtFullNameRegister.requestFocus();
    }

    private void registerAndAddAcountToFirebase(String email, String password, String fullName) {
        //init firebase auth
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            try {
                                //1. if create email - password successfully then create model user
                                User user = new User(email, fullName, password);

                                //2. insert user into room database
                                userDAO.insert(user);

                                //3. clear inputs
                                clearInput();

                                //4. show message notification
                                Message.showMessage(MainActivity_Register.this, "Message", "Create account success!");

                                //5. save data from database client to firebase
                                saveDataFromClientToFireBase();
                            } catch (Exception e) {
                                Message.showMessage(MainActivity_Register.this, "Message", "Create account fail!");
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Message.showMessage(MainActivity_Register.this, "Message", "Email was exist!");
                        }
                    }
                });
    }

    private void saveDataFromClientToFireBase() {
        List<User> users = userDAO.getAll();
        Map<String, User> mapUsers = new HashMap<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        for (User u : users) {
            mapUsers.put(u.getId() + "", u);
        }

        try {
            mDatabase.child("users").setValue(mapUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appEventHandle() {
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

                if (fullName.equals("") || email.equals("") || password.equals("") || confirmPass.equals("")) {
                    Message.showMessage(MainActivity_Register.this,
                            "Message", "Please enter all the information!");
                } else {
                    if (confirmPass.equals(password)) {
                        //call function
                        registerAndAddAcountToFirebase(email, password, fullName);
                    } else {
                        Message.showMessage(MainActivity_Register.this, "Message", "Confirm password must equal password!");
                    }
                }
            }
        });
    }
}