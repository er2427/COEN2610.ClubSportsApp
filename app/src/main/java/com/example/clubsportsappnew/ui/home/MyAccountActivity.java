package com.example.clubsportsappnew.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubsportsappnew.MainActivity;
import com.example.clubsportsappnew.R;
import com.example.clubsportsappnew.databinding.MyAccountBinding;

public class MyAccountActivity extends AppCompatActivity {
    private MyAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}