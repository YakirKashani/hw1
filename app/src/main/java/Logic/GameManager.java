package Logic;


import java.util.ArrayList;
import java.util.Random;

import Utilities.SignalManager;


public class GameManager {
    private int life;
    private int [][] Game_Map;
    private final int ROWS = 8;
    private  final int COLS = 3;
    private int playerCell;
    private  int ObstacleCreateSpeed;
    private int ObstacleMovementSpeed;
    private Random rand = new Random();
    private int NextCol_RANDOM;
    private ArrayList<Index> LiveObstaclesArrayList;
    private boolean newGame;

    public GameManager(){
        this.life = 3;
        Game_Map = new int[ROWS][COLS];
        for(int i=0;i<ROWS;i++)
        {
            for(int j=0;j<COLS;j++)
            {
                Game_Map[i][j] = 0;
            }
        }
        playerCell = 1;
        Game_Map[ROWS-1][playerCell] = 1;
        ObstacleCreateSpeed = 3;
        ObstacleMovementSpeed = 1;
        LiveObstaclesArrayList = new ArrayList<Index>();
        newGame = false;
    }

    public void MovePlayer(int direction) {
        boolean MoveAble = false;
        if (((this.playerCell == 2) && (direction == -1)) || (this.playerCell == 0 && direction == 1) || (this.playerCell == 1))
            MoveAble = true;
        if (MoveAble) {
            this.Game_Map[ROWS-1][playerCell] = 0;
            this.playerCell += direction;
            this.Game_Map[ROWS-1][playerCell] = 1;
        }
    }

    public void CreateObcstacle(){
        NextCol_RANDOM = rand.nextInt(COLS);
        this.Game_Map[0][NextCol_RANDOM] = 1;
        LiveObstaclesArrayList.add(new Index().setROW(0).setCOL(NextCol_RANDOM));
    }


    public void MoveObstacles() {
        for (int i = 0; i < LiveObstaclesArrayList.size(); i++) {
            Game_Map[LiveObstaclesArrayList.get(i).getROW()][LiveObstaclesArrayList.get(i).getCOL()] = 0;
            if (LiveObstaclesArrayList.get(i).getROW() == ROWS - 2) {
                if(LiveObstaclesArrayList.get(i).getCOL() == playerCell)
                    HIT();
                LiveObstaclesArrayList.remove(i);
                i--;
                //checkCollision
            } else {
                LiveObstaclesArrayList.get(i).setROW(LiveObstaclesArrayList.get(i).getROW() + 1);
                Game_Map[LiveObstaclesArrayList.get(i).getROW()][LiveObstaclesArrayList.get(i).getCOL()] = 1;
            }
        }
    }

    public void HIT(){
        SignalManager.getInstance().vibrate(500);
        SignalManager.getInstance().toast("HIT!");
        if(life == 1)
        {
            life = 3;
            newGame = true;
        }
        else {
            life--;
        }
    }



    public void setLife(int life) {

        this.life = life;
    }

    public int getLife() {

        return life;
    }

    public GameManager(int life){

        this.life = life;
    }

    public int[][] getGame_Map() {
        return Game_Map;
    }

    public int getValue(int row,int col){
        return this.Game_Map[row][col];
    }

    public int getObstacleCreateSpeed() {
        return ObstacleCreateSpeed;
    }

    public int getObstacleMovementSpeed() {
        return ObstacleMovementSpeed;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }
}


