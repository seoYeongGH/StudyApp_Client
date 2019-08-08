package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class StudyPage extends AppCompatActivity {
    List<WordVO> list_wordVO = new ArrayList<WordVO>();
    ArrayList<String> wordList = new ArrayList<String>();
    ArrayList<String> meanList = new ArrayList<String>();
    int totalNum = -1;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = RetroController.getInstance().getRetrofit();
        WordService wordService = retrofit.create(WordService.class);

        Call<List<WordVO>> getWords = wordService.getWords("study");
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

        setContentView(R.layout.study_activity);
    }

    public void onBtnNWordClicke(View view){
        num++;
        if(num>=totalNum)
            num=0;
        setTxtView(num);
    }

    public void onBtnPWordClicked(View view){
        num--;

        if(num<0)
            num = totalNum-1;

        setTxtView(num);
    }

    private void setWordList(){
        for(int i=0; i<totalNum; i++){
            wordList.add(i,list_wordVO.get(i).getWord());
            meanList.add(i,list_wordVO.get(i).toMeanString());
        }

        return ;
    }

    private void setTxtView(int n){
        TextView txtWord = findViewById(R.id.txtWord);
        TextView txtMean = findViewById(R.id.txtMean);
        TextView txtNum = findViewById(R.id.txtWordNum);

        txtWord.setText(wordList.get(n));
        txtMean.setText(meanList.get(n));
        txtNum.setText("Study: "+(num+1)+"/"+totalNum);
    }
}
