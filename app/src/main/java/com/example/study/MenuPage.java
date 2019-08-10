package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study.Retro.ResponseData;
import com.example.study.Retro.RetroController;
import com.example.study.Retro.UserService;
import com.example.study.VO.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.study.Constant.OK;

public class MenuPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        View layout = findViewById(R.id.layoutMenu);
        setTxtLogout(layout);
        setIdView();
    }

    public void onBackPressed(){
        BackButton.getInstance().onBtnPressed(getApplicationContext(),this);
    }
    private void setIdView(){
        Session session = Session.getInstance();
        TextView idView = findViewById(R.id.idView);
        if(idView == null)Log.d("NULL","NULL!!");
        idView.setText(session.getId()+"님이 공부중입니다.");
    }

    public void setTxtLogout(View view){
        TextView txtLogout = findViewById(R.id.txtLogout);
        txtLogout.setText(Html.fromHtml("<u>logout</u>"));

        txtLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                doLogout();
            }
        });

    }
    public void onBtnStudyClicked(View view){
        Intent intent = new Intent(this,StudyPage.class);
        startActivity(intent);
    }

    public void onBtnTestClicked(View view){
        Intent intent = new Intent(this,TestPage.class);
        startActivity(intent);
    }

    private void doLogout(){
        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);

        Call<ResponseData> doService = userService.doService("loginServer","logout",new UserVO());
        doService.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    int flag = response.body().getCode();
                    if(flag == OK) {
                        Session.getInstance().setId(null);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),"Fail Locout!!",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
}
