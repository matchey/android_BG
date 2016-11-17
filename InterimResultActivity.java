package com.example.amsl.bg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.Locale;

public class InterimResultActivity extends AppCompatActivity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interim_result);

        Intent intent = getIntent();
        Player[] player = (Player[]) intent.getSerializableExtra("PLAYER");
        int counter = intent.getIntExtra("COUNT",1);
        int num = intent.getIntExtra("NUMBER",0);
        //int[] handicap = intent.getIntArrayExtra("HANDICAP");

        if(counter<1){
            counter = 1;
        }

        findViewById(R.id.buttonBack).setOnClickListener(this);
        ViewGroup vg = (ViewGroup) findViewById(R.id.layoutInterim);

        if(num>0) {
            float ave = 0;
            float max = player[0].get_ave();
            for (int i = 1; i < num; i++) {
                ave = player[i].get_ave();
                if (max < ave) {
                    max = ave;
                }
            }
            max = (int)max - (int)max % 10;
            for (int i = 0; i < num; i++) {
                // 行を追加
                getLayoutInflater().inflate(R.layout.interim_result, vg);
                // 文字設定
                ave = player[i].average;
                ave += (10.0f / 3.00001f) * 3f;
                ave = (int)ave - (int)ave % 10;
                int handicap = (int)max - (int)ave;
                handicap = handicap < 0 ? 0 : handicap;
                TableRow tr = (TableRow) vg.getChildAt(i + 1);

                String str = String.format(Locale.getDefault(), "%s(%d)", player[i].name, handicap);
                //((TextView) (tr.getChildAt(0))).setText(player[i].name);
                ((TextView) (tr.getChildAt(0))).setText(str);

                str = String.format(Locale.getDefault(), "%4.1f", 1f * player[i].sum / counter);
                //str = String.format(Locale.getDefault(), "%4.1f", 1f * player[i].average);
                ((TextView) (tr.getChildAt(1))).setText(str);

                str = String.format(Locale.getDefault(), "%5d yen", (int) player[i].income_expenditure - (int) player[i].last_result);
                ((TextView) (tr.getChildAt(2))).setText(str);

                str = String.format(Locale.getDefault(), "%5d yen", (int) player[i].income_expenditure);
                ((TextView) (tr.getChildAt(3))).setText(str);
            }
        }
    }
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.buttonBack:
                finish();
                break;
            default:
                break;
        }
    }
}
