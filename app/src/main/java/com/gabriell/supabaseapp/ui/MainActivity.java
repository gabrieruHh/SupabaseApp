package com.gabriell.supabaseapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gabriell.supabaseapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.tvId.setText("Olá Gabriel!");

        setContentView(binding.getRoot());
    }
}