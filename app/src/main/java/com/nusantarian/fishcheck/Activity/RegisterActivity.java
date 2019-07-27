package com.nusantarian.fishcheck.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nusantarian.fishcheck.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText et_name, et_email, et_username, et_password, et_confpass;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Daftar Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mStorage = FirebaseStorage.getInstance().getReference().child("Users").child("Profile Picture");

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_confpass = findViewById(R.id.et_confpass);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register){
            String email = et_email.getText().toString();
            final String name = et_name.getText().toString();
            final String username = et_username.getText().toString();
            String password = et_password.getText().toString();
            String confpass = et_confpass.getText().toString();

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(name) && TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confpass)){
                Toast.makeText(this, "Silahkan Isi Data Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Silahkan Isi Email Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(name)){
                Toast.makeText(this, "Silahkan Isi Nama Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(username)){
                Toast.makeText(this, "Silahkan Isi Username Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Silahkan Isi Password Anda", Toast.LENGTH_SHORT).show();
            }
            else if (password.length() < 6){
                Toast.makeText(this, "Password Terlalu Pendek, Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
            }
            else if (!password.equals(confpass)){
                Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
            }else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            mDatabase.child(uid).child("Nama").setValue(name);
                            mDatabase.child(uid).child("Username").setValue(username);
                            Toast.makeText(RegisterActivity.this, "Pendaftaran Akun Sukses!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Pendaftaran Akun Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
