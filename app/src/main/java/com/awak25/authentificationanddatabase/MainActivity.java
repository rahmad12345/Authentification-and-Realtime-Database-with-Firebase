package com.awak25.authentificationanddatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private Button btnLogin,btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
//                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        updateUI(user);
                                        String email = user.getEmail();

                                        Toast.makeText(MainActivity.this, "Berhasil Register", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
//                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
                                    }// ...
                                }
                            });
                }
            }
        });
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();

                    if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                        Toast.makeText(MainActivity.this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }else{

                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
//                                            updateUI(user);
                                            String email = user.getEmail();

                                            Toast.makeText(MainActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(MainActivity.this, RealtimeDatabaseActivity.class));
                                            
                                        } else {
                                            // If sign in fails, display a message to the user.
//                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
//                                            updateUI(null);
                                        }
                                        // ...
                                    }
                                });
                    }
                }
            });
    }
}
