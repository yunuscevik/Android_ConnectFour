package com.example.asus.connectfour;

/**
 * Created by Asus on 13.02.2018.
 */

import static java.lang.Character.toLowerCase;

/**
 * Oyununda kazanan "Var mı?" "Yok mu?" diye bakan class
 * @author Yunus Çevik
 */
public class Control {
    private int rowSize;
    private int colSize;
    public Control(int size){
        setRowSize(size);
        setColSize(size);
    }

    /**
     *
     * @return (rowSize)
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     *
     * @param rowSize Oyunu kontrol etmek için alınan row değeri
     */
    public final void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    /**
     *
     * @return (colSize)
     */
    public int getColSize() {
        return colSize;
    }

    /**
     *
     * @param colSize Oyunu kontrol etmek için alınan col değeri
     */
    public final void setColSize(int colSize) {
        this.colSize = colSize;
    }

    /**
     * Bu fonksiyon hangi oyuncunun kazanıp kazanmadığını kontrol eder eğer satır, sutun ya da çapraz 4 tane yan yana aynı karakterin yan yana gelmesihalinde bool değer döndürür.
     * @param gameBoard Oyunun oynandığı board
     * @param pPosX Pozisyon x kordinatı
     * @param pPosY Pozisyon y kordinatı
     * @param characterOfPlayer Oyuncunun oynadığı karakter
     * @return (True - False)
     */
    public boolean control(Cell[][] gameBoard,int pPosX,int pPosY,char characterOfPlayer){
        //satır satır kontrol: Her satırda 4 tane yan yana aynı karakterin gelidiğini kontrol eder.
        int upCount=0;
        for(int i=0; i < getColSize(); i++){
            if(gameBoard[pPosX][i].getMoveChar() == characterOfPlayer)
                upCount++;
            else
                upCount=0;

            if(upCount==4){
                for(int j=i-upCount+1; j <= i; j++){
                    gameBoard[pPosX][j].setMoveChar(toLowerCase(characterOfPlayer));
                    gameBoard[pPosX][j].changeButtonColor(R.drawable.green_button);
                }
                return true;
            }
        }

        // sutun sutun kontrol: Her sutunda 4 tane üst üste aynı karakterin gelidiğini kontrol eder.
        upCount=0;
        for(int i=0; i < getRowSize(); i++){
            if(gameBoard[i][pPosY].getMoveChar() == characterOfPlayer)
                upCount++;
            else
                upCount=0;

            if(upCount==4){
                for(int j=i-upCount+1; j <= i; j++){
                    gameBoard[j][pPosY].setMoveChar(toLowerCase(characterOfPlayer));
                    gameBoard[j][pPosY].changeButtonColor(R.drawable.green_button);
                }
                return true;
            }
        }
        // sağdan sola capraz: Board üzerinde sağdan sola çapraz 4 tane çapraz aynı karakterin gelidiğini kontrol eder.
        upCount=0;
        int tempPposXUp=pPosX;
        int tempPposYUp=pPosY;
        int smallPposXUp=0;
        int smallPposYUp=0;
        boolean flag=true;
        for(int i=0; i < 3 && flag==true; i++){
            tempPposXUp--;
            tempPposYUp++;
            if((tempPposXUp>=0 && tempPposXUp < getRowSize()) && (tempPposYUp>=0 && tempPposYUp< getColSize())){
                if(gameBoard[tempPposXUp][tempPposYUp].getMoveChar() == characterOfPlayer){
                    smallPposXUp=tempPposXUp;
                    smallPposYUp=tempPposYUp;
                    upCount++;
                }
                else
                    flag=false;
            }
            else
                flag=false;

        }
        int downCount=0;
        int tempPposXDown=pPosX;
        int tempPposYDown=pPosY;
        int smallPposXDown=0;
        int smallPposYDown=0;
        flag=true;
        for(int i=0; i < 3 && flag==true; i++){
            tempPposXDown++;
            tempPposYDown--;
            if((tempPposXDown>=0 && tempPposXDown< getRowSize()) && (tempPposYDown>=0 && tempPposYDown< getColSize())){
                if(gameBoard[tempPposXDown][tempPposYDown].getMoveChar() == characterOfPlayer){
                    smallPposXDown=tempPposXDown;
                    smallPposYDown=tempPposYDown;
                    downCount++;
                }
                else
                    flag=false;
            }
            else
                flag=false;

        }
        if((upCount+downCount)>=3){
            for(int i=0; i < 4 ; i++){
                if(downCount != 0){
                    gameBoard[smallPposXDown][smallPposYDown].setMoveChar(toLowerCase(characterOfPlayer));
                    gameBoard[smallPposXDown][smallPposYDown].changeButtonColor(R.drawable.green_button);
                    smallPposXDown--;
                    smallPposYDown++;
                }
                if(upCount != 0){
                    gameBoard[smallPposXUp][smallPposYUp].setMoveChar(toLowerCase(characterOfPlayer));
                    gameBoard[smallPposXUp][smallPposYUp].changeButtonColor(R.drawable.green_button);
                    smallPposXUp++;
                    smallPposYUp--;
                }
            }
            return true;
        }
        // soldan saga capraz: Board üzerinde soldan sağa çapraz 4 tane çapraz aynı karakterin gelidiğini kontrol eder.
        upCount=0;
        tempPposXUp=pPosX;
        tempPposYUp=pPosY;
        smallPposXUp=0;
        smallPposYUp=0;
        flag=true;
        for(int i=0; i < 3 && flag==true; i++){
            tempPposXUp--;  //// hatalı yerler
            tempPposYUp--;
            if((tempPposXUp>=0 && tempPposXUp < getRowSize()) && (tempPposYUp>=0 && tempPposYUp< getColSize())){
                if(gameBoard[tempPposXUp][tempPposYUp].getMoveChar() == characterOfPlayer){
                    smallPposXUp=tempPposXUp;
                    smallPposYUp=tempPposYUp;
                    upCount++;
                }
                else
                    flag=false;
            }
            else
                flag=false;

        }
        downCount=0;
        tempPposXDown=pPosX;
        tempPposYDown=pPosY;
        smallPposXDown=0;
        smallPposYDown=0;
        flag=true;
        for(int i=0; i < 3 && flag==true; i++){
            tempPposXDown++; /// hatalı yerler
            tempPposYDown++;
            if((tempPposXDown>=0 && tempPposXDown< getRowSize()) && (tempPposYDown>=0 && tempPposYDown< getColSize())){
                if(gameBoard[tempPposXDown][tempPposYDown].getMoveChar() == characterOfPlayer){
                    smallPposXDown=tempPposXDown;
                    smallPposYDown=tempPposYDown;
                    downCount++;
                }
                else
                    flag=false;
            }
            else
                flag=false;


        }
        if((upCount+downCount)>=3){

            for(int i=0; i < 4 ; i++){

                if(downCount != 0){
                    gameBoard[smallPposXDown][smallPposYDown].setMoveChar(toLowerCase(characterOfPlayer));
                    gameBoard[smallPposXDown][smallPposYDown].changeButtonColor(R.drawable.green_button);
                    smallPposXDown--;
                    smallPposYDown--;
                }
                if(upCount != 0){
                    gameBoard[smallPposXUp][smallPposYUp].setMoveChar(toLowerCase(characterOfPlayer));
                    gameBoard[smallPposXUp][smallPposYUp].changeButtonColor(R.drawable.green_button);
                    smallPposXUp++;
                    smallPposYUp++;
                }
            }
            return true;
        }
        return false;
    }
}
