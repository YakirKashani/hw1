package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import Logic.GameManager;
import Utilities.SignalManager;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton main_FAB_right;
    private ExtendedFloatingActionButton main_FAB_left;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[][] main_IMG_GameMap;
    private GameManager gameManager;
//    private MaterialTextView timer_LBL_time;

    private static final long DELAY = 1000;
    final Handler handler = new Handler();
    private long startTime;
    private int Seconds;
    private boolean timerOn = false;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,DELAY);
            updateTimer();
            if(Seconds % gameManager.getObstacleMovementSpeed() == 0 && Seconds!=0)
                gameManager.MoveObstacles();
            if(Seconds % gameManager.getObstacleCreateSpeed() == 0 && Seconds!=0)
                gameManager.CreateObcstacle();
            refreshUI();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameManager = new GameManager();
        refreshUI();
        main_FAB_left.setOnClickListener(view -> PlayerMove(-1)); // -1 move left
        main_FAB_right.setOnClickListener(view -> PlayerMove(1)); // 1 move right
        startTimer();
        SignalManager.init(this);
    }
    private void refreshUI() {
        for(int row = 0;row<8;row++) {
            for (int col = 0; col < 3; col++) {
                if(gameManager.getValue(row,col) == 1)
                    main_IMG_GameMap[row][col].setVisibility(View.VISIBLE);
                else
                    main_IMG_GameMap[row][col].setVisibility(View.INVISIBLE);
            }
        }
//        timer_LBL_time.setText(String.format("%d",Seconds));
        if(gameManager.isNewGame())
        {
            for(int i=0;i<main_IMG_hearts.length;i++)
                main_IMG_hearts[i].setVisibility(View.VISIBLE);
            gameManager.setNewGame(false);
        }
        else if(gameManager.getLife() < main_IMG_hearts.length)
            main_IMG_hearts[gameManager.getLife()].setVisibility(View.INVISIBLE);
    }

    private void PlayerMove(int direction) {
        gameManager.MovePlayer(direction);
        refreshUI();
    }



    private void findViews() {
        main_FAB_right = findViewById(R.id.main_FAB_right);
        main_FAB_left = findViewById(R.id.main_FAB_left);
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
        main_IMG_GameMap = new ShapeableImageView[][]{
                {findViewById(R.id.R0_C0),
                        findViewById(R.id.R0_C1),
                        findViewById(R.id.R0_C2)},

                {findViewById(R.id.R1_C0),
                        findViewById(R.id.R1_C1),
                        findViewById(R.id.R1_C2)},


                {findViewById(R.id.R2_C0),
                        findViewById(R.id.R2_C1),
                        findViewById(R.id.R2_C2)},

                {findViewById(R.id.R3_C0),
                        findViewById(R.id.R3_C1),
                        findViewById(R.id.R3_C2)},
                {findViewById(R.id.R4_C0),
                        findViewById(R.id.R4_C1),
                        findViewById(R.id.R4_C2)},

                {findViewById(R.id.R5_C0),
                        findViewById(R.id.R5_C1),
                        findViewById(R.id.R5_C2)},

                {findViewById(R.id.R6_C0),
                        findViewById(R.id.R6_C1),
                        findViewById(R.id.R6_C2)},

                {findViewById(R.id.R7_C0),
                        findViewById(R.id.R7_C1),
                        findViewById(R.id.R7_C2),
                }
        };

//        timer_LBL_time = findViewById(R.id.timer_LBL_time);
    }
    private void updateTimer(){
        long currentMillis = System.currentTimeMillis() - startTime;
        int seconds = (int)(currentMillis/1000);
        Seconds = seconds;
    }
    public void startTimer(){
        if(!timerOn){
            timerOn = true;
            startTime = System.currentTimeMillis();
            handler.postDelayed(runnable,0);
        }
    }
}