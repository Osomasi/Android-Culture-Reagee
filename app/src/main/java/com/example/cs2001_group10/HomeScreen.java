package com.example.cs2001_group10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class HomeScreen extends AppCompatActivity {

    private static final String TAG = "Home Screen Activity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);

        Button settings_menu = (Button) findViewById(R.id.setting);
        settings_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, SettingsMenu.class));
            }
        });

        Button select_topics_menu = (Button) findViewById(R.id.topics);
        select_topics_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, Topics.class));
            }
        });

        Button popup_Test = (Button) findViewById(R.id.popUpTest);
        popup_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popup = new Intent(getBaseContext(), Questions.class);
                startActivity(popup);
            }
        });

    }


}
