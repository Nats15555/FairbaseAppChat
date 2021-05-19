package com.example.firebaseadd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebaseadd.utility.FireBaseConnection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private final FireBaseConnection fireBaseConnection=new FireBaseConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText userET = findViewById(R.id.userEditText);
        EditText passET = findViewById(R.id.passEditText);
        EditText emailET = findViewById(R.id.emailEditText);
        Button registerBtn = findViewById(R.id.buttonRegister);
        Button inLogin = findViewById(R.id.inLogin);

        inLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUserName = userET.getText().toString();
                String txtEmail = emailET.getText().toString();
                String txtPassword = passET.getText().toString();

                if (TextUtils.isEmpty(txtUserName) || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(RegisterActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be at the 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    register(txtUserName, txtEmail, txtPassword);
                }

            }
        });
    }

    private void register(String userName, String email, String password) {

        fireBaseConnection.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = fireBaseConnection.getLoginUser();
                            String userId="";
                            if(firebaseUser != null){
                                userId = firebaseUser.getUid();

                                Map<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", userId);
                                hashMap.put("username", userName);
                                hashMap.put("imageURL", "default");

                                fireBaseConnection.getMyUsers().child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(RegisterActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }
}