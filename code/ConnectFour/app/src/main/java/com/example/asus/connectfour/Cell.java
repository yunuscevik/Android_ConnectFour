package com.example.asus.connectfour;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;


/**
 * Created by Asus on 12.02.2018.
 */


@SuppressLint("AppCompatCustomView")
public class Cell extends Button {
    private char moveChar;
    private Context con;

    /**
     * @param context Uygulamanın herhangi bir zamandaki durumunu tutan objedir.
     */
    public Cell(Context context) {
        super(context);
        con = context;
        moveChar = '.';
        changeButtonColor(R.drawable.table_hole);
    }


    /**
     * @param d Butonun reng değeri.
     */
    @SuppressLint("NewApi")
    public void changeButtonColor(int d){
        @SuppressLint("RestrictedApi")
        Drawable draw = AppCompatDrawableManager.get().getDrawable(con,d);
        setBackground(draw);
    }


    // ---- Getter - Setter ---- //
    public char getMoveChar() {
        return moveChar;
    }
    public void setMoveChar(char moveChar) {
        this.moveChar = moveChar;
    }
}
