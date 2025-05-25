package com.example.app_movie;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_movie.data.UserRepository;

public class MainActivity extends AppCompatActivity {

    private UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new UserRepository(this);
        EditText usernameEditText = findViewById(R.id.et_username);
        EditText passwordEditText = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.btn_login);

        //  массив пользователей из ресурсов
//        Resources res = getResources();
//        String[] credentials = res.getStringArray(R.array.user_credentials);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                repository.login(username,password, (isValid)->
                {
                    if (Boolean.TRUE.equals(isValid)) {
                        Intent intent = new Intent(MainActivity.this, MovieNavigation.class);
//                        intent.putExtra("username", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Неверные учетные данные!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}



