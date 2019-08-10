package com.example.study;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import static com.example.study.Constant.ALERTNULL;
import static com.example.study.Constant.ERRID;
import static com.example.study.Constant.ERRPW;
import static com.example.study.Constant.OK;

public class LoginPage extends AppCompatActivity {
    private int loginFlag = -1;
    private String inputId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        View layout = findViewById(R.id.layoutFind);
        onTxtClicked(layout);
    }

    public void onBtnLoginClicked(View view){
        EditText editId = findViewById(R.id.loginId);
        EditText editPw = findViewById(R.id.loginPw);

        inputId = editId.getText().toString();
        String loginPw = editPw.getText().toString();

        if(inputId.length()==0 || loginPw.length()==0) {
            showAlert(ALERTNULL);
            return;
        }
        UserVO userVO = new UserVO();
        userVO.setId(inputId);
        userVO.setPassword(loginPw);

        doLogin(userVO);

        return ;
    }

    private void showAlert(int alertCase){
        String msg;

        switch(alertCase){
            case ALERTNULL: msg = "모든 항목을 입력해야합니다."; break;
            case ERRID: msg = "존재하지 않는 아이디입니다.";break;
            case ERRPW: msg = "일치하지 않는 비밀번호입니다.";break;
            default: msg = "ERROR!!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(":(");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void doLogin(UserVO userVO){
        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);

        Call<ResponseData> doService = userService.doService("loginServer","",userVO);
        doService.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    loginFlag = response.body().getCode();
                    checkData(loginFlag);
                }
                else{
                    Log.d("Fail_Login",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("ERR_Login",t.toString());
            }
        });
    }

    private void checkData(Integer flag){
        switch(flag){
            case OK:
                Session session = Session.getInstance();
                session.setId(inputId);
                Toast toast = Toast.makeText(this,"로그인이 완료되었습니다.",Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(this,MenuPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case ERRID:
            case ERRPW: showAlert(flag);break;
            default: Log.d("Login_Err","Login_Flag");
        }
    }

    public void onTxtClicked(View view){
        TextView txtJoin  = findViewById(R.id.txtJoin);
        TextView txtFindId = findViewById(R.id.txtFindId);
        TextView txtFindPw = findViewById(R.id.txtFindPw);

        txtJoin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(),JoinPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        final Intent ittFind = new Intent(getApplicationContext(),FindPage.class);
        ittFind.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        txtFindId.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(ittFind);
            }
        });

        txtFindPw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(ittFind);
            }
        });
    }
}
