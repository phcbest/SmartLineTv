package com.phc.smartlinetv.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.phc.smartlinetv.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        onEvent();
        setContentView(mBinding.getRoot());
    }

    private void onEvent() {
        mBinding.btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchActivity.class);
            intent.putExtra("search",
                    Objects.requireNonNull(mBinding.etText.getText()).toString());
            startActivity(intent);
        });
    }
}