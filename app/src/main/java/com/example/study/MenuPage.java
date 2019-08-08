package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setIdView();
    }

    private void setIdView(){
        Session session = Session.getInstance();
        TextView idView = findViewById(R.id.idView);
        if(idView == null)Log.d("NULL","NULL!!");
        idView.setText(session.getId()+"님이 공부중입니다.");
    }

    public void onBtnStudyClicked(View view){
        Intent intent = new Intent(this,StudyPage.class);
        startActivity(intent);
    }

    public void onBtnTestClicked(View view){
        Intent intent = new Intent(this,TestPage.class);
        startActivity(intent);
    }
}
