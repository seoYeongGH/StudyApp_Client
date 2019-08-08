package com.example.study;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.study.Retro.ResponseData;
import com.example.study.Retro.RetroController;
import com.example.study.Retro.UserService;
import com.example.study.VO.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.study.Constant.ALERTNULL;
import static com.example.study.Constant.ERRID;
import static com.example.study.Constant.OK;
import static com.example.study.Constant.NONEDATA;

public class FindPage extends AppCompatActivity {
    String obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_activity);
    }

    protected void onResume (){
        super.onResume();

        EditText editName1 = findViewById(R.id.iptFindId1);
        EditText editEmail1 = findViewById(R.id.iptFindId2);

        EditText editName2 = findViewById(R.id.iptFindPw1);
        EditText editId = findViewById(R.id.iptFindPw2);
        EditText editEmail2 = findViewById(R.id.iptFindPw3);


        editName1.setText("");
        editEmail1.setText("");

        editName2.setText("");
        editId.setText("");
        editEmail2.setText("");

        editName1.requestFocus();
    }

    public void onBtnFindIdClicked(View view){
        EditText editName = findViewById(R.id.iptFindId1);
        EditText editEmail = findViewById(R.id.iptFindId2);

        String strName = editName.getText().toString();
        String strEmail = editEmail.getText().toString();

        if(strName.length()==0 || strEmail.length()==0){
            showAlert(ALERTNULL,null);

            return;
        }

        UserVO userVO = new UserVO();
        userVO.setName(strName);
        userVO.setEmail(strEmail);

        obj = "ID";
        findInfo("findId", userVO);
    }

    public void onBtnFindPwClicked(View view){
        EditText editName = findViewById(R.id.iptFindPw1);
        EditText editId = findViewById(R.id.iptFindPw2);
        EditText editEmail = findViewById(R.id.iptFindPw3);

        String strName = editName.getText().toString();
        String strId = editId.getText().toString();
        String strEmail = editEmail.getText().toString();

        if(strName.length()==0 || strId.length()==0 || strEmail.length()==0 ){
            showAlert(ALERTNULL,null);

            return;
        }

        UserVO userVO = new UserVO();
        userVO.setName(strName);
        userVO.setId(strId);
        userVO.setEmail(strEmail);

        obj = "P.W";
        findInfo("findPw",userVO);
    }



    private void findInfo(String doing, UserVO userVO){
        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);

        Call<ResponseData> doService = userService.doService("findServer",doing,userVO);
        doService.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    int flag = response.body().getCode();
                    String data = response.body().getData();

                    showAlert(flag, data);
                }
                else{
                    Log.d("Fail_Find",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("ERR_Find",t.toString());
            }
        });
    }

    private void showAlert(final int alertCase, String data){
        String msg;

        switch(alertCase){
            case OK: msg = obj+": "+data+"\nOK를 누르면 로그인페이지로 이동합니다."; break;
            case ALERTNULL: msg = "모든 정보를 입력해야합니다.";break;
            case ERRID: msg = "입력하신 아이디가 잘못되었습니다.\n아이디 찾기를 먼저 해주세요.";break;
            case NONEDATA: msg = "가입정보가 없습니다.\n회원가입을 해주세요.";break;
            default: msg = "ERROR!!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notification");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(alertCase == OK){
                    Intent intent =  new Intent(getApplicationContext(),LoginPage.class);
                    startActivity(intent);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
