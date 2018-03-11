package com.example.asus.connectfour;

/**
 * Created by Asus on 13.02.2018.
 */

import android.widget.TextView;
import java.util.Random;

/**
 * Oyunda computerin yapması gereken işlemlerin olduğu class (Akıllı Hamlelerin yapıldığı)
 * @author Yunus Çevik
 */
public class Computer {
    private int rowSize;
    private int colSize;
    private int xCount;
    private int oCount;
    private int pCount;
    public int pPosX=0;
    public int pPosY=0;

    /**
     *
     * @param size Board' un size değeri
     */
    public Computer(int size){
        setRowSize(size);
        setColSize(size);
        setoCount(0);
        setpCount(0);
        setxCount(0);
    }

    /**
     *
     * @return int(xCount)
     */
    public int getxCount() {
        return xCount;
    }

    /**
     *
     * @param xCount Board üzerindeki 'X' değerlerinin sayısı
     */
    public final void setxCount(int xCount) {
        this.xCount = xCount;
    }

    /**
     *
     * @return int(oCount)
     */
    public int getoCount() {
        return oCount;
    }

    /**
     *
     * @param oCount Board üzerindeki 'O' değerlerinin sayısı
     */
    public final void setoCount(int oCount) {
        this.oCount = oCount;
    }

    /**
     *
     * @return int(pCount)
     */
    public int getpCount() {
        return pCount;
    }

    /**
     *
     * @param pCount Board üzerindeki '.' değerlerinin sayısı
     */
    public final void setpCount(int pCount) {
        this.pCount = pCount;
    }

    /**
     *
     * @return int(rowSize)
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     *
     * @param rowSize Board' un row değeri
     */
    public final void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    /**
     *
     * @return int(colSize)
     */
    public int getColSize() {
        return colSize;
    }

    /**
     *
     * @param colSize Board' un column değeri
     */
    public final void setColSize(int colSize) {
        this.colSize = colSize;
    }
    /**
     * Computerin belirli pozisyona hamle yaptığı method.
     * @param gameBoard Oyunun oynandığı board
     * @param pPosX Board üzerinde oynanacak satır değeri
     * @param pPosY Board üzerinde oynanacak sutun değeri
     * @return boolean (True - False)
     */
    boolean setMoveChecker(Cell[][] gameBoard,int pPosX,int pPosY){
        gameBoard[pPosX][pPosY].setMoveChar('O');
        gameBoard[pPosX][pPosY].changeButtonColor(R.drawable.yellow_button);
        return true;
    }
    /**
     * Computer hamle yapmadan önce rakip taşları("x"), kendi taşlarını("o") ve boş noktaları(".") sayar.
     * Sayılan değerler "Call by Reference" ile değişkenlere verilir.
     * @param gameBoard Oyunun oynandığı board
     * @param row Board' un üzerindeki satır
     * @param col Board' un üzerindeki sütün
     */
    public void checkerCount(Cell[][] gameBoard,int row,int col){
        if(gameBoard[row][col].getMoveChar()=='X')
            setxCount(getxCount()+1);
        else if(gameBoard[row][col].getMoveChar()=='O')
            setoCount(getoCount()+1);
        else if(gameBoard[row][col].getMoveChar()=='.'){
            setpCount(getpCount()+1);
            pPosX=row;
            pPosY=col;
        }
    }
    /**
     * "checkerCount" fonksiyonun saydığı değerleri kontrol eder. Böylece computer in atak ya da savunma mı
     * yapacağı belirlenir ve kendi karakterini boş hücreye koyar.
     * @param gameBoard Oyunun oynandığı board
     * @param played Hamle yapıldı mı? Hamle yapılmadı mı?
     * @param ADR Atak - Defans - Random için Board' un taranma sırası
     * @return boolean (True - False)
     */
    public boolean moveChecker(Cell[][] gameBoard,boolean played,int ADR, TextView move){
        if(played==false && (ADR==0 && getoCount()==3 && getpCount()==1 && getxCount()!=3)){
            if(pPosX+1 < getRowSize()){
                if(gameBoard[pPosX+1][pPosY].getMoveChar() != '.'){
                    played = setMoveChecker(gameBoard, pPosX, pPosY);
                    move.setText(String.format("Computer: Column %d was moved. ((Win Attack))",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. ((Win Attack))\n",(pPosY+1));
                }
            }
            else{
                if(gameBoard[pPosX][pPosY].getMoveChar() == '.'){
                    played = setMoveChecker(gameBoard, pPosX, pPosY);
                    move.setText(String.format("Computer: Column %d was moved. ((Win Attack))",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. ((Win Attack))\n",(pPosY+1));
                }
            }
        }
        else if(played==false &&(ADR==1 && getxCount()==3 && getpCount()==1 && getoCount()!=3)){
            if(pPosX+1 < getRowSize()){
                if(gameBoard[pPosX+1][pPosY].getMoveChar() != '.'){
                    played = setMoveChecker(gameBoard, pPosX, pPosY);
                    move.setText(String.format("Computer: Column %d was moved. (Defense)",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. (Defense)\n",(pPosY+1));
                }
            }
            else{
                if(gameBoard[pPosX][pPosY].getMoveChar() == '.'){
                    played = setMoveChecker(gameBoard, pPosX, pPosY);
                    move.setText(String.format("Computer: Column %d was moved. (Defense)",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. (Defense)\n",(pPosY+1));
                }
            }
        }
        else if(played==false && (ADR==2 && getoCount()==2 && getpCount()==2 && getxCount()!=3)){
            if(pPosX+1 < getRowSize()){
                if(gameBoard[pPosX+1][pPosY].getMoveChar() != '.'){
                    played = setMoveChecker(gameBoard, pPosX, pPosY);
                    move.setText(String.format("Computer: Column %d was moved. (Triple Attack)",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. (Triple Attack)\n",(pPosY+1));
                }
            }
            else{
                if(gameBoard[pPosX][pPosY].getMoveChar() == '.'){
                    played = setMoveChecker(gameBoard, pPosX, pPosY);
                    move.setText(String.format("Computer: Column %d was moved. (Triple Attack)",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. (Triple Attack)\n",( pPosY+1));
                }
            }
        }
        setoCount(0);
        setpCount(0);
        setxCount(0);
        return played;
    }
    /**
     * Board un her tarafı taranıp, "checkerCount" ve "moveChecker" fonksiyonları ile hamleler yapılır.
     * @param gameBoard Oyunun oynandığı board
     * @return boolean(True - False)
     */
    public boolean ComputerAttackAndDefense(Cell[][] gameBoard, TextView move){

        boolean played=false;
        // Yatay
        for(int r=0; r < 3; r++){
            for(int i=0; i < getRowSize(); i++){
                for(int j=0; j < getColSize(); j++){
                    for(int k=j; k < j+4 && k < getColSize() && played==false; k++)
                        checkerCount(gameBoard,i,k);
                    played=moveChecker(gameBoard, played, r, move);
                }
            }
            // Dikey
            for(int i=0; i < getColSize(); i++){
                for(int j=0; j < getRowSize(); j++){
                    for(int k=j; k < j+4 && k < getRowSize() && played==false; k++)
                        checkerCount(gameBoard,k,i);
                    played=moveChecker(gameBoard, played, r, move);
                }
            }

            int smallSize=0;
            if(getRowSize() >= getColSize()){
                smallSize=getColSize();
            }
            else if(getRowSize() < getColSize()){
                smallSize=getRowSize();
            }

            //Soldan sağa çapraz
            for(int i=0; i < getRowSize()-3; i++){
                if((getRowSize()-i) >= smallSize){
                    for(int j=i,k=0; k < smallSize; j++,k++){
                        for(int l=j,m=k; m< k+4 && k < smallSize-3 && played==false;l++,m++)
                            checkerCount(gameBoard,l,m);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
                else if((getRowSize()-i) < smallSize){
                    for(int j=i,k=0; k < getRowSize()-i; j++,k++){
                        for(int l=j,m=k; m< k+4 && k < getRowSize()-i-3 && played==false;l++,m++)
                            checkerCount(gameBoard,l,m);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
            }
            for(int i=0; i < getColSize()-3; i++){
                if((getColSize()-i) >= smallSize){
                    for(int j=i,k=0; k < smallSize; j++,k++){
                        for(int l=k,m=j; l< k+4 && k < smallSize-3 && played==false;l++,m++)
                            checkerCount(gameBoard,l,m);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
                else if((getColSize()-i) < smallSize){
                    for(int j=i,k=0; k < getColSize()-i; j++,k++){
                        for(int l=k,m=j; l< k+4 && k < getColSize()-i-3 && played==false;l++,m++)
                            checkerCount(gameBoard,l,m);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
            }

            // Sağdan sola çapraz
            for(int i=0; i < getRowSize()-3; i++){
                if((getRowSize()-i) >= smallSize){
                    for(int j=i,k=getColSize()-1; j < smallSize+i; j++,k--){
                        for(int l=j,m=k; l< j+4 && j < smallSize+i-3 && played==false;l++,m--)
                            checkerCount(gameBoard,l,m);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
                else if((getRowSize()-i) < smallSize){
                    for(int j=i,k=getColSize()-1; j < getRowSize() && played==false ; j++,k--){
                        for(int l=j,m=k; l< j+4 && j < getRowSize()-3 && played==false;l++,m--)
                            checkerCount(gameBoard,l,m);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
            }
            for(int i=0; i < getColSize()-3; i++){
                if((getColSize()-i) >= smallSize){
                    for(int j=0,k=getColSize()-1-i; j < smallSize; j++,k--){
                        for(int l=k,m=j; m< j+4 && j < smallSize-3 && played==false;l--,m++)
                            checkerCount(gameBoard,m,l);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
                else if((getColSize()-i) < smallSize){
                    for(int j=0,k=getColSize()-1-i; j < getColSize()-i; j++,k--){
                        for(int l=k,m=j; m< j+4 && j < getColSize()-i-3  && played==false;l--,m++)
                            checkerCount(gameBoard,m,l);
                        played=moveChecker(gameBoard, played, r, move);
                    }
                }
            }

        }
        return played;
    }
    /**
     * Computer in her hangi bir atak ya da savunma hamlesi olmadığı taktirde board size çerçevesinde rasgele sayı üretilip hamle yapar.
     * @param gameBoard Oyunun oynandığı board
     */
    public void ComputerRandom(Cell[][] gameBoard, TextView move){
        Random rand = new Random();
        boolean flag=true;
        while(flag){
            pPosY = rand.nextInt(getRowSize()) + 0;
            for(int i=getRowSize()-1; i>=0; i--){
                if(gameBoard[i][pPosY].getMoveChar() == '.'){
                    gameBoard[i][pPosY].setMoveChar('O');
                    gameBoard[i][pPosY].changeButtonColor(R.drawable.yellow_button);

                    flag=false;
                    pPosX=i;
                    move.setText(String.format("Computer: Column %d was moved.",pPosY+1));
                    System.out.printf("Computer: Column %d was moved. (Random)\n",( pPosY+1));
                    i=0;
                }
            }
        }
    }
}
