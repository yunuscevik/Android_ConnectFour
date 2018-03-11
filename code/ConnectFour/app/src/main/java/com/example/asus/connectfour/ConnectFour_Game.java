package com.example.asus.connectfour;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Created by Asus on 11.02.2018.
 */

/**
 * Ekranda çalışan uygulamanın ikinci sayfasının kodu.
 */
public class ConnectFour_Game extends Activity  {

    private Cell gameCell[][];
    private ConnectFour con;
    private String chooseGame;
    private int size;
    private  int moveTime;
    private Context context;
    private TextView moveTimeTv;
    private TextView p1;
    private TextView p2;
    private TextView moveOrder;
    private TextView player1Score;
    private TextView player2Score;
    private TextView move;
    private Boolean countDownRunTime;
    private CountDownTimer countDownTimer;
    private long timeRemaining ;
    private BroadcastReceiver brReceiver;
    private boolean home;

    // ---- Getters - Setters ---- //
    public BroadcastReceiver getBrReceiver() { return brReceiver; }
    public TextView getP1() {
        return p1;
    }
    public void setP1(TextView p1) {
        this.p1 = p1;
    }
    public TextView getP2() {
        return p2;
    }
    public void setP2(TextView p2) {
        this.p2 = p2;
    }
    public TextView getMoveOrder() {
        return moveOrder;
    }
    public void setMoveOrder(TextView moveOrder) {
        this.moveOrder = moveOrder;
    }
    public TextView getPlayer1Score() {
        return player1Score;
    }
    public void setPlayer1Score(TextView player1Score) {
        this.player1Score = player1Score;
    }
    public TextView getPlayer2Score() {
        return player2Score;
    }
    public void setPlayer2Score(TextView player2Score) {
        this.player2Score = player2Score;
    }
    public TextView getMove() {
        return move;
    }
    public void setMove(TextView move) {
        this.move = move;
    }
    public CountDownTimer getCountDownTimer() { return countDownTimer; }
    public void setCountDownTimer(CountDownTimer countDownTimer) { this.countDownTimer = countDownTimer; }
    public int getMoveTime() {
        return moveTime;
    }
    public void setMoveTime(int moveTime) {
        this.moveTime = moveTime;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getChooseGame() {
        return chooseGame;
    }
    public void setChooseGame(String chooseGame) {
        this.chooseGame = chooseGame;
    }

    /**
     * Uygulama çalıştıgında ilk açılan sayfa üzerinde oluşan objelerin ne iş yaptıklarını belirlediğimiz metod.
     * @param savedInstanceState
     */
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_four__game);

        countDownRunTime = true;
        context = this;
        home = false;
        Intent intent = getIntent(); // yonlendirilen saylaflardan bilgiler alınır.
        String sizeText = intent.getStringExtra("board_Size");
        String moveTimeText = intent.getStringExtra("move_Time");
        setChooseGame(intent.getStringExtra("game_Type"));
        ((TextView) findViewById(R.id.gameType)).setText(getChooseGame()+"\n"+sizeText);
        moveTimeTv = (TextView) findViewById(R.id.moveTime);
        moveTimeTv.setText(moveTimeText);
        p1 = (TextView) findViewById(R.id.player1Name);
        p1.setText(intent.getStringExtra("player1_Name"));
        p2 = (TextView) findViewById(R.id.player2Name);
        p2.setText(intent.getStringExtra("player2_Name"));
        moveOrder = (TextView) findViewById(R.id.moveOrder);
        if(moveOrder.getText() != p1.getText())
            moveOrder.setText(p1.getText());
        player1Score = (TextView) findViewById(R.id.player1Score);
        player2Score = (TextView) findViewById(R.id.player2Score);
        move = (TextView) findViewById(R.id.move);
        setSize(Integer.parseInt(sizeText.substring(0,(sizeText.length()/2))));
        setMoveTime(Integer.parseInt(new StringTokenizer(moveTimeText).nextToken()));
        Button undo = (Button)findViewById(R.id.undo);

        gameCell= new Cell[getSize()][getSize()];
        con = new ConnectFour(getSize(),getChooseGame());
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        // Gridin satır - sütünunun ne kadar olacağı belirlenir.
        grid.setRowCount(size+2);
        grid.setColumnCount(size);

        repeatCountDownTimer(((getMoveTime()+1) * 1000), 1000);
        coordinateButton(grid);
        int i = 0, j = 0, margin = 10;
        for(i = 0; i < size; i++){
            for(j = 0; j < size; j++) {
                gameCell[i][j] = new Cell(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setMargins(margin,margin,margin,margin);
                gameCell[i][j].setLayoutParams(params);
                grid.addView(gameCell[i][j]); // grid üzerine butonlar eklenir.

                final int finalI = i;
                final int finalJ = j;
                // Grid üzerinde bulunan butonlardan hangisine basıldığı belirlenir ve oyuna hamle için tetiklenir.
                gameCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        countDownRunTime = false;
                        if("Player - Computer".equals( getChooseGame()))
                            con.playerAndComputer(gameCell, finalJ, false, ConnectFour_Game.this);
                        else if ("Player1 - Player2".equals(getChooseGame())){
                            con.player1AndPlayer2(gameCell, finalJ, false,ConnectFour_Game.this);
                        }
                    }
                });
                // Oyun esnasında Undo butonuna basıldığında yapacağı hamleler ve oyunu tetiklemesi belirlenir.
                undo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCountDownTimer().cancel();
                        repeatCountDownTimer(((getMoveTime()+1) * 1000), 1000);
                        if("Player - Computer".equals( getChooseGame()))
                            setChooseGame(con.playerAndComputer(gameCell, finalJ, true, ConnectFour_Game.this));
                        else if ("Player1 - Player2".equals(getChooseGame())){
                            setChooseGame(con.player1AndPlayer2(gameCell, finalJ, true, ConnectFour_Game.this));
                        }
                    }
                });
            }
        }
        coordinateButton(grid);

        // Android cihazın ekran bilgilerinin alındığı ve ona göre işleme konulduğu kod parçaları.
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        brReceiver = new GameScreenReceiver();
        registerReceiver(brReceiver, filter);
    }


    /**
     * Geri sayımı duraklatıp AlertDialog ekranı ile kullanıcıya Resume ya da Exit gibi seçenekler sunar.
     */
    private void ResumAndExitDialog(){
        getCountDownTimer().cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GAME PAUSE");
        builder.setCancelable(false);
        builder.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                long millisInFuture = timeRemaining;
                repeatCountDownTimer(millisInFuture, 1000);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getApplicationContext(), "This game has ended. We will wait for you again for the game. :)", Toast.LENGTH_LONG).show();
                unregisterReceiver(brReceiver);
                finish();
            }
        });
        builder.create().show();
    }

    /**
     * Android cihaz üzerinde bulunan Home tuşunun özelliği ile ResumeAndExitDialog() metodu çağrılır.
     */
    @Override
    protected void onUserLeaveHint() {
        home = true;
        ResumAndExitDialog();
        super.onUserLeaveHint();
    }

    /**
     * Android cihaz üzerinde bulunan Back tuşunun özelliği ile ResumeAndExitDialog() metodu çağrılır.
     */
    @Override
    public void onBackPressed() {
        ResumAndExitDialog();
    }

    /**
     * Android cihaz üzerinde bulunan Power tuşuna tıklama sonucu onPause() metodu çağrılır.
     * Bu sırada ResumeAndExitDialog() metodu çağrılması için Override edilir.
     */
    @Override
    protected void onPause() {
        if(GameScreenReceiver.screenOn == true){
            if(isFinishing() == false) {
                if(home == false)
                    ResumAndExitDialog();
            }
        }
        home = false;
        super.onPause();
    }

    /**
     * Oyun tahtasının hangi column larda olduğunu temsil eden butonlar.
     * @param grid Oyun ekranı üzerindeki tahtayı oluşturur.
     */
    private void coordinateButton(GridLayout grid){
        for(int k = 0; k < size; k++) {
            Button b = new Button(this);
            b.setText(""+(k+1));
            b.setBackgroundColor(grid.getDrawingCacheBackgroundColor());
            b.setGravity(Gravity.CENTER);
            b.setTypeface(null, Typeface.BOLD);
            grid.addView(b);
        }
    }

    /**
     * Bu metod ile zamanın tekrar tekrar yenilenmesi sağlanır.
     * @param millisInFuture Zaman hangi süreden başlar.
     * @param countDownInterval Zamanın geriye doğru kaç aralıkla azalacağını belirtir.
     */
    public void repeatCountDownTimer(long millisInFuture, long countDownInterval) {
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            String rep = "";
            public void onTick(long millisUntilFinished) {
                if(countDownRunTime == true) {
                    moveTimeTv.setText("" + String.format("%d seconds", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
                    timeRemaining = millisUntilFinished;
                }
                else {
                    cancel();
                    moveTimeTv.setText("0 seconds");
                    onFinish();
                }

            }

            public void onFinish() { // süre doldugunda çalışacak kısım
                Random rand = new Random();
                int pPosY = rand.nextInt(getSize()) + 0; // random olarak hangi sütuna atılacağını belirler.

                if(countDownRunTime == true) {
                    if ("Player - Computer".equals(getChooseGame())) {
                        move.setText(String.format("Column %d was moved.",pPosY+1));
                        rep = con.playerAndComputer(gameCell, pPosY,false, ConnectFour_Game.this);
                    }
                    else if ("Player1 - Player2".equals(getChooseGame())) {
                        move.setText(String.format("Column %d was moved.",pPosY+1));
                        rep = con.player1AndPlayer2(gameCell, pPosY,false, ConnectFour_Game.this);
                    }
                    if (rep.contentEquals("continue")) {
                        cancel();
                        moveTimeTv.setText(""+getMoveTime()+" seconds");
                        repeatCountDownTimer(((getMoveTime()+1) * 1000), 1000);
                    }

                }
                else {
                    countDownRunTime = true;
                    cancel();
                    moveTimeTv.setText(""+getMoveTime()+" seconds");
                    repeatCountDownTimer(((getMoveTime()+1) * 1000), 1000);
                }

            }

        }.start();
    }
}
