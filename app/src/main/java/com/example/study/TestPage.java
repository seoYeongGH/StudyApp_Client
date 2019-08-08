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

import com.example.study.Retro.RetroController;
import com.example.study.Retro.WordService;
import com.example.study.VO.WordVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TestPage extends AppCompatActivity {
    List<WordVO> list_wordVO = new ArrayList<WordVO>();
    ArrayList<String> wordList = new ArrayList<String>();
    ArrayList<String> meanList = new ArrayList<String>();
    int totalNum = -1;
    int num;

    private int correctNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        WordService wordService = retrofit.create(WordService.class);

        Call<List<WordVO>> getWords = wordService.getWords("test");
        getWords.enqueue(new Callback<List<WordVO>>() {
            @Override
            public void onResponse(Call<List<WordVO>> call, Response<List<WordVO>> response) {
                if(response.isSuccessful()){
                    num = 0;

                    list_wordVO = response.body();
                    totalNum = list_wordVO.size();

                    setWordList();

                    setTxtView(0);
                }
                else{
                    Log.d("Fail",response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<List<WordVO>> call, Throwable t) {
                Log.d("ERR","ERR!!");
            }
        });

        setContentView(R.layout.test_activity);
    }

    public void onBtnSubmitClicked(View view){
        EditText editAnswer = findViewById(R.id.editAnswer);
        String strAnswer = editAnswer.getText().toString();

        Log.d("CONFIRM",""+num+"."+wordList.get(num)+"."+editAnswer.getText());
        if(wordList.get(num).equals(strAnswer)) {
            correctNum++;
            Log.d("CONFIRM",""+correctNum);
        }

        num++;
        if(num<totalNum) {
            setTxtView(num);
            editAnswer.setText("");
        }
        else
            showScore();

    }

    private void showScore(){
        String msg = "수고하셨습니다!\nScore: "+correctNum*20;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(":)");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(),MenuPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void setWordList(){
        for(int i=0; i<totalNum; i++){
            wordList.add(i,list_wordVO.get(i).getWord());
            meanList.add(i,list_wordVO.get(i).toMeanString());
        }

        return ;
    }

    private void setTxtView(int n){
        TextView txtTest = findViewById(R.id.txtTest);
        TextView txtNum = findViewById(R.id.txtTestNum);

        txtTest.setText(meanList.get(n));
        txtNum.setText("문제: "+(num+1)+"/"+totalNum);
    }
}
