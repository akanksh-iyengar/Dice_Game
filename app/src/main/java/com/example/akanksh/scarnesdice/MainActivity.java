package com.example.akanksh.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int comp_tscore,comp_oscore,user_tscore,user_oscore,player=0,flag=0,winner=0;
    Button hold,roll,reset;
    ImageView dicefaceimage;
    TextView scoreview,scoreviewcpu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("lifecycle","In onCreate()");

        hold=(Button)findViewById(R.id.button_hold);
        reset=(Button)findViewById(R.id.button5_reset);
        roll=(Button)findViewById(R.id.button_roll);
        resetgame();
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_oscore+=user_tscore;
                user_tscore=0;
                contextSwitch();
                AIplay();
            }
        });
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score=Rolldice();
                hold.setEnabled(true);
                int flag=0;
                if(score==1){
                    user_tscore=0;
                    user_oscore+=user_tscore;
                    flag=1;
                }
                else {
                    user_tscore += score;
                    if(user_tscore+user_oscore>=100){
                        winner=0;
                        gameclose();
                    }
                }
                updateScore();
                if(flag==1){
                    contextSwitch();
                    AIplay();
            }}
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            resetgame();
            }
        });
    }

    int Rolldice(){
        Random r= new Random();
        int roll_val = r.nextInt(6);
        dicefaceimage = (ImageView)findViewById(R.id.imageView_dice);
        int [] dice_faces={R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
        dicefaceimage.setImageResource(dice_faces[roll_val]);
        return roll_val + 1;
    }

    void updateScore(){
        String scoreText,scoreText1;
        scoreview=(TextView)findViewById(R.id.textView_uscore);
        scoreviewcpu=(TextView)findViewById(R.id.textView_cpuscore);
            scoreText="Your Score: "+user_tscore+" Your overall Score: "+user_oscore;
            scoreText1= "AI Score: "+comp_tscore+" AI overall Score: "+comp_oscore;
        scoreview.setText(scoreText);
        scoreviewcpu.setText(scoreText1);
    }

    void AIplay(){
        comp_tscore=0;
        final Handler handler = new Handler();
        Runnable cpu = new Runnable() {
            @Override
            public void run() {
                int score = Rolldice();
                if (score == 1){
                    comp_tscore = 0;
                    contextSwitch();
                    flag=1;
                }
                else {
                    comp_tscore += score;
                    if(comp_oscore+comp_oscore>=100){
                        winner=1;
                        gameclose();
                    }
                    updateScore();}
                if (comp_tscore > 15) {
                    comp_oscore += comp_tscore;
                    updateScore();
                    contextSwitch();
                        flag=1;
                    }
                if(comp_tscore<=15 && flag==0)
                    handler.postDelayed(this, 1000);
            }
        };
        //if(flag==0)
        handler.postDelayed(cpu,1000);
    }

    void contextSwitch(){
        if(player==0){
            player=1;
            hold.setEnabled(false);
            roll.setEnabled(false);
        }
        else{
            player=0;
            //hold.setEnabled(true);
            roll.setEnabled(true);
        }
        flag=0;
    }

    void resetgame(){
        user_oscore=user_tscore=comp_tscore=comp_oscore=0;
        player=0;
        updateScore();
        player=1;
        contextSwitch();
        hold.setEnabled(false);
    }

    void gameclose(){
        TextView win=(TextView)findViewById(R.id.textView2);
        if(winner==0)
            win.setText("You win");
        else
            win.setText("You lost to the simplest of the AIs");
        roll.setEnabled(false);
        hold.setEnabled(false);
        reset.setEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("lifecycle","In onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("lifecycle","In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("lifecycle","In Destroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("lifecycle","In onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("lifecycle","In onResume()");
    }
}
