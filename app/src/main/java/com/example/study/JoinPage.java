package com.example.study;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import static com.example.study.Constant.DIFFPW;
import static com.example.study.Constant.DUPID;
import static com.example.study.Constant.DUPNAME;
import static com.example.study.Constant.OK;

public class JoinPage extends AppCompatActivity {
    private int joinFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void onBtnJoinClicked(View view){
        EditText eTxtName = findViewById(R.id.iptFindId1);
        EditText eTxtId = findViewById(R.id.iptTxtId);
        EditText eTxtPw = findViewById(R.id.iptTxtPw);
        EditText eChkPw = findViewById(R.id.iptChkPw);
        EditText eTxtEmail = findViewById(R.id.iptTxtEmail);

        String strName = eTxtName.getText().toString();
        String strId = eTxtId.getText().toString();
        String strPassword = eTxtPw.getText().toString();
        String strChk = eChkPw.getText().toString();
        String strEmail = eTxtEmail.getText().toString();

        if(strName.length()==0 || strId.length()==0 || strPassword.length()==0 || strEmail.length()==0) {
            showAlert(ALERTNULL);
            return;
        }

        if(!strPassword.equals(strChk)){
            showAlert(DIFFPW);
            return;
        }

        UserVO userVO = new UserVO(strName, strId, strPassword, strEmail);
        saveMember(userVO);

        return ;
    }


    private void showAlert(int alertCase){
        String msg;
        switch (alertCase){
            case ALERTNULL: msg = "모든 항목을 입력해야 합니다."; break;
            case DIFFPW: msg = "입력한 비밀번호가 일치하지 않습니다."; break;
            case DUPNAME: msg = "이미 가입한 사용자입니다."; break;
            case DUPID: msg = "이미 존재하는 ID입니다."; break;
            default : msg = "ERROR!!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sorry");
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveMember(UserVO userVO){
        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);

        Call<ResponseData> doService = userService.doService("joinServer","",userVO);
        doService.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    joinFlag = response.body().getCode();
                    Log.d("TEST1",""+joinFlag);
                    checkDup(joinFlag);
                }

                else{
                    Log.d("Fail_Join",response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void checkDup(Integer flag){
        Log.d("TEST2",""+flag);
        switch (flag){
            case OK:
                Toast toast = Toast.makeText(this, "회원가입이 완료되었습니다.",Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(this, LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case DUPNAME:
            case DUPID:showAlert(flag); break;
            default: Log.d("Join_Err","Join_Flag");
        }
    }

}
