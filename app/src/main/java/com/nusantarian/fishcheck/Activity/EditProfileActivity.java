package com.nusantarian.fishcheck.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nusantarian.fishcheck.R;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private EditText et_email, et_username, et_name;
    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private String uid, name, username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.pengaturan_profil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mStorage = FirebaseStorage.getInstance().getReference().child("Users").child("Profile Pictures");

        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_username = findViewById(R.id.et_username);
        findViewById(R.id.btn_simpan).setOnClickListener(this);
        findViewById(R.id.img_profpic).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_editprofile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_updatepass){
            startActivity(new Intent(EditProfileActivity.this, UpdatePassActivity.class));
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_simpan:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Simpan Data");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                break;
            case R.id.img_profpic:
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.inflate(R.menu.edit_profile_menu);
                popupMenu.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_pilih:
                break;
            case R.id.nav_take:
                break;
            case R.id.nav_delete:
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
