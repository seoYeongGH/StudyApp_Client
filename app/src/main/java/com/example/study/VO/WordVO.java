package com.example.study.VO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WordVO {
    @SerializedName("word")
    String word;

    @SerializedName("mean")
    ArrayList<String> mean;

    public WordVO(String word, ArrayList<String> mean) {
        this.word = word;
        this.mean = mean;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<String> getMean() {
        return mean;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMean(ArrayList<String> mean) {
        this.mean = mean;
    }

    public String toMeanString(){
        String str = "";
        int meanSize = mean.size();
        int i=0;

        if(meanSize != 1) {
            for (i=0; i < meanSize - 1; i++) {
                str += mean.get(i);
                str += ", ";
            }
        }

        str += mean.get(i);

        return str;
    }
}
