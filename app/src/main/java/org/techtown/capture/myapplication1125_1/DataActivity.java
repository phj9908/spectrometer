package org.techtown.capture.myapplication1125_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import static org.techtown.capture.myapplication1125_1.MainActivity.count_frame;
import static org.techtown.capture.myapplication1125_1.MainActivity.sum_B;
import static org.techtown.capture.myapplication1125_1.MainActivity.sum_G;
import static org.techtown.capture.myapplication1125_1.MainActivity.sum_R;
import static org.techtown.capture.myapplication1125_1.MainActivity.sum_alpha;
import static org.techtown.capture.myapplication1125_1.MainActivity.sum_beta;

public class DataActivity extends AppCompatActivity {
    
//    double[][] data_dst= new double[100][3]; //    
//    double[] B_array= new double[100]; // 그래프 도시화 할 때 쓰기
//    double[] G_array= new double[100];
//    double[] R_array= new double[100];
    String[] add_array= new String[6];
    double[] aver_array= new double[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button reset_bt = findViewById(R.id.reset_but);
        TextView tx1 = (TextView)findViewById(R.id.textView);
        TextView tx2 = (TextView)findViewById(R.id.textView2);
        TextView tx3 = (TextView)findViewById(R.id.textView3);
        TextView tx4 = (TextView)findViewById(R.id.textView4);
        TextView tx5 = (TextView)findViewById(R.id.textView5);
        TextView tx6 = (TextView)findViewById(R.id.textView6);
        TextView sensing_txt = (TextView)findViewById(R.id.sensing_txt);
       // TextView result_txt = (TextView)findViewById(R.id.result_txt);

        Intent intent = getIntent();
        double data_src[] = intent.getExtras().getDoubleArray("data"); // 인덴트에 2차원 배열은 허용X

        double frame=0;
        frame=data_src[3];

        for(int i=0; i<6;i++) {

            if(i!=5) aver_array[i]=data_src[i]/frame; // 누적 값(픽셀,alpha,beta)/ 프레임 갯수

            add_array[i]="";
            switch(i){
                    case 0: add_array[0] = " B: " + aver_array[i]; break;
                    case 1: add_array[1]= " G: " +aver_array[i]; break;
                    case 2: add_array[2]= " R: " + aver_array[i]; break;
                    case 3: add_array[3]= ""+aver_array[i]; break;
                    case 4: add_array[4]= "" + aver_array[i]; break;
                    case 5: add_array[5]= " frame: " + (int)frame; break;
                }
        }

        tx1.setText(add_array[0]);tx2.setText(add_array[1]);tx3.setText(add_array[2]);
        tx4.setText(add_array[5]);
        tx5.setText("        # averaging pixel = cumulative pixel / frame ");
        tx6.setText("        # averaging (alpha)x+(beta) =" + add_array[3]+"x+ " + add_array[4]);
        sensing_txt.setText(" 1ppm"); //add_array[6]+"ppm"로 고치기


        setResult(Activity.RESULT_OK, intent); //이 액티비티가 종료되기전에 정상적으로 수행되었다고 메인액티비티에 응답

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //다시 MAtinActivity로 돌아감
            }
        });

        Intent launch_intent = new Intent(DataActivity.this,LaunchActivity.class);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(launch_intent); // 시작화면으로 돌아감 혹은 바로 다른모드 액티비티로 넘어가던가
            }
        });

        reset_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sum_B = 0;
                sum_G = 0;
                sum_R = 0;

                sum_alpha=0;
                sum_beta=0;
                count_frame = 0;

                tx1.setText(" ... ");
                tx2.setText(" ... ");
                tx3.setText(" ... ");
                tx4.setText(" ... ");
                tx6.setText(" ... ");
                sensing_txt.setText(" ... ");

            }
        });


    }
}

