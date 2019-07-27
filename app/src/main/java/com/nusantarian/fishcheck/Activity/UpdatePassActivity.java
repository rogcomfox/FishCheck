package com.nusantarian.fishcheck.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nusantarian.fishcheck.R;

import java.util.Objects;

public class UpdatePassActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser mUser;
    private EditText et_newpass, et_confpass;
    private String newpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.update_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        et_newpass = findViewById(R.id.et_newpass);
        et_confpass = findViewById(R.id.et_confpass);
        findViewById(R.id.btn_simpan).setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_simpan){
            newpass = et_newpass.getText().toString();
            String confpass = et_confpass.getText().toString();

            if (TextUtils.isEmpty(newpass) && TextUtils.isEmpty(confpass)){
                Toast.makeText(this, "Silahkan Isi Password Baru Anda", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(newpass)){
                Toast.makeText(this, "Silahkan Isi Password Anda", Toast.LENGTH_SHORT).show();
            }
            else if (newpass.length() < 6){
                Toast.makeText(this, "Password Terlalu Pendek", Toast.LENGTH_SHORT).show();
            }
            else if (!newpass.equals(confpass)){
                Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Apakah Anda Yakin?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mUser.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(UpdatePassActivity.this, "Password Berhasil Diganti", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(UpdatePassActivity.this, "gagal Mengganti Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
