package com.example.roombasic;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        navController= Navigation.findNavController(findViewById(R.id.fragmentContainerView3));
//        NavigationUI.setupActionBarWithNavController(this,navController);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navController= Navigation.findNavController(findViewById(R.id.fragmentContainerView3));
        navController.navigateUp();
    }
}