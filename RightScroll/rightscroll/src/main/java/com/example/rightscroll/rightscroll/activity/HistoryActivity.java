package com.example.rightscroll.rightscroll.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.example.rightscroll.rightscroll.R;

public class HistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView textView = (TextView) findViewById(R.id.history);
        textView.setText(createMessage());
    }

    private String createMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("ver0.11\n");
        sb.append("・スタート画面・変更履歴画面追加\n");
        sb.append("・上昇・下降時の加速度を変更\n");
        sb.append("・前方への加速機能追加\n");
        sb.append("・スコアを(m)に変更\n");
        sb.append("・ハイスコア機能追加\n");
        sb.append("・中断・再開機能追加\n");
        sb.append("・画面回転の制御追加\n");
        sb.append("・障害物の速度を障害物毎に設定\n");
        sb.append("・障害物の出現率を変更\n");
        sb.append("\n");
        sb.append("ver0.10\n");
        sb.append("・初版\n");
        return sb.toString();
    }
}
