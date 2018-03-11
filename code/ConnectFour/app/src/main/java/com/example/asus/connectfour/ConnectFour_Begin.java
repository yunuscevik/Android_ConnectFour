package com.example.asus.connectfour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Ekranda çalışan uygulamanın ilk sayfasının kodu.
 */
public class ConnectFour_Begin extends AppCompatActivity {
    private Spinner gameTypeSpinner;
    private Spinner boardSizeSpin;
    private Spinner moveTimeSpin;
    private EditText player1Edit;
    private EditText player2Edit;
    private Button help;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    /**
     * Uygulama çalıştıgında ilk açılan sayfa üzerinde oluşan objelerin ne iş yaptıklarını belirlediğimiz metod.
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_four__begin);
        player1Edit = (EditText) findViewById(R.id.player1Edit);
        final TextView player2Text = (TextView) findViewById(R.id.textView5);
        player2Edit = (EditText) findViewById(R.id.player2Edit);
        gameTypeSpinner = (Spinner) findViewById(R.id.gameTypeSpin);
        gameTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Oyun tipinin seçimine göre player2 bilgileri görünür ya da görünmez olarak belirlenir.
             * @param adapterView -
             * @param view -
             * @param position Oyun tipi seçilirken Spinner üzerindeki position değeri.
             * @param l -
             */
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    player2Text.setVisibility(View.INVISIBLE);
                    player2Edit.setVisibility(View.INVISIBLE);
                    player2Edit.setText("Computer");
                }
                else if(position == 1){
                    player2Text.setVisibility(View.VISIBLE);
                    player2Edit.setVisibility(View.VISIBLE);
                    player2Edit.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        List<String> boardSize = new ArrayList<>();
        List<String> moveTime = new ArrayList<>();
        for(int i=5; i <=60; i++) {
            if(i <= 40)
                boardSize.add( "" + i + "x" + i);
            moveTime.add( "" + i + " seconds");
        }
        boardSizeSpin = (Spinner) findViewById(R.id.boardSizeSpin);
        ArrayAdapter<String> arrBoardSize = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, boardSize);
        boardSizeSpin.setAdapter(arrBoardSize);


        moveTimeSpin = (Spinner) findViewById(R.id.moveTimeSpin);
        ArrayAdapter<String> arrmoveTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, moveTime);
        moveTimeSpin.setAdapter(arrmoveTime);

        help = (Button) findViewById(R.id.help);

        help.setOnClickListener(new OnClickListener() {
            /**
             * Help butonu üzerinde click yapıldığında AlertDialog ekranıyla oyunun hakkında bilgi verilir.
             * @param view -
             */
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConnectFour_Begin.this);
                builder.setTitle("HELP").setMessage(R.string.helpText);
                builder.setPositiveButton("I understand", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    /**
     * Android cihaz üzerindeki kapasitif geri tuşuna tıklama metodu.
     */
    @Override
    public void onBackPressed() {
        // iki kere tıklandığında cıkmak için
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            this.finish();
            super.onBackPressed();
            System.exit(0);
        }
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();

        back_pressed = System.currentTimeMillis();
    }

    /**
     * Uygulamanın ilk sayfasındaki Game Start tuşununa basıldığında,
     * ekranda bulunan bazı bilgilerin başka bir sayfaya aktarılmasını sağlar
     * @param v -
     */
    public void ClickToGame(View v){
        if(v.getId() == R.id.gameStartButton){
            Intent intent = new Intent(this,ConnectFour_Game.class);
            intent.putExtra("game_Type",gameTypeSpinner.getSelectedItem().toString());
            intent.putExtra("board_Size",boardSizeSpin.getSelectedItem().toString());
            intent.putExtra("move_Time",moveTimeSpin.getSelectedItem().toString());
            if((player1Edit.getText().toString()).contentEquals(""))
                player1Edit.setText("Player1");
            if((player2Edit.getText().toString()).contentEquals(""))
                player2Edit.setText("Player2");
            intent.putExtra("player1_Name",player1Edit.getText().toString());
            intent.putExtra("player2_Name",player2Edit.getText().toString());
            startActivity(intent);
        }
    }
}
