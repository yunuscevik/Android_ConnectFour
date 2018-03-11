package com.example.asus.connectfour;

/**
 * Created by Asus on 13.02.2018.
 */


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Oyunun oynandığı ve gerekli methodların kullanılğı ana class
 * @author Yunus Çevik
 */


public class ConnectFour {
    private String move = "Player1";
    private int rowSize;
    private int colSize;
    private Boolean undoMode;
    private String chooseGame;
    private List<undoInformation> undoInf;
    private ConnectFour_Game conneFG;

    /**
     * Undo işleminde bazı bilgilerin tutulduğu class
     */
    public class undoInformation{ // Undo yapmadan önce row, col, hangi oyuncu ve oyuncunun taşının tutulduğu struct.
        public int row;
        public int col;
        public char moveChar;
        public String playerType;
    };

    // ---- Getters - Setters ---- //
    public ConnectFour_Game getConnetFG() {
        return conneFG;
    }
    public void setConnetFG(ConnectFour_Game conneFG) {
        this.conneFG = conneFG;
    }
    public Boolean getUndoMode() {
        return undoMode;
    }
    public void setUndoMode(Boolean undoMode) {
        this.undoMode = undoMode;
    }
    public String getChooseGame() {
        return chooseGame;
    }
    public void setChooseGame(String chooseGame) {
        this.chooseGame = chooseGame;
    }

    /**
     *
     * @param size Board Size
     * @param gameType Oyun tipi
     */
    public ConnectFour(int size,String gameType){
        setUndoMode(false);
        setRowSize(size);
        setColSize(size);
        setChooseGame(gameType);
        undoInf = new ArrayList<>();

    }
    /**
     * Oyun bittikten sonra tekrardan oyunu oluşturur
     * @param gameBoard Oyunun oynandığı board
     */
    private void resetBoard(Cell[][] gameBoard){
        for(int i = 0; i < getRowSize(); i++){
            for(int j = 0; j < getColSize(); j++){
                gameBoard[i][j].setMoveChar('.');
                gameBoard[i][j].changeButtonColor(R.drawable.table_hole);
            }
        }
        undoInf.clear();
    }
    /**
     * Oyunun kimin kazandığıyla ilgili messageDialog şeklinde bilgi verir.
     * @param wins
     */
    public void winsPrint(String wins, final Cell[][] gameBoard){
        getConnetFG().getCountDownTimer().cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(getConnetFG());
        builder.setTitle("Wins").setMessage(wins);
        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                getConnetFG().getMove().setText("");
                resetBoard(gameBoard);
                getConnetFG().repeatCountDownTimer(((getConnetFG().getMoveTime()+1) * 1000), 1000);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getConnetFG(), "This game has ended. We will wait for you again for the game. :)", Toast.LENGTH_LONG).show();
                getConnetFG().unregisterReceiver(getConnetFG().getBrReceiver());
                getConnetFG().finish();

            }
        });
        builder.create().show();
    }
    /**
     * "Player - Computer" seçildiği taktirde bu fonksiyon çağrılır ve sırayla hamle şansını oyunculara verir.
     * @param gameBoard Oyunun oynandığı board
     * @param pPosY Board üzerinde oynanacak sutun değeri
     * @return String
     */
    public String playerAndComputer(Cell[][] gameBoard, int pPosY,  Boolean undoM, ConnectFour_Game cfg){
        boolean win=false;
        boolean draws = true;
        setUndoMode(undoM);
        setConnetFG(cfg);
        if(getUndoMode() == true){
            if (undoInf.size() != 0) {
                for(int i = 0; i < 2; i++)
                    undoFunction(gameBoard);
            } else
                Toast.makeText(getConnetFG(), "Can Not UNDO Because of The Board is Empty !!!", Toast.LENGTH_LONG).show();
            return getChooseGame();
        }
        if("Player1".equals(getMove()) || "Player".equals(getMove())){
            win = play("Player",'X',gameBoard,pPosY);
            printCells(gameBoard);
            if(win == true){
                draws=false;
                winsPrint("         *****"+getConnetFG().getP1().getText()+" Wins*****",gameBoard);
                int score = (Integer.parseInt(new StringTokenizer((String) getConnetFG().getPlayer1Score().getText()).nextToken()));
                getConnetFG().getPlayer1Score().setText(""+(++score));
                return "stop";
            }
        }
        win=gameDraws(gameBoard);
        if(win==true && draws==true){
            winsPrint("         *****Game Draws*****",gameBoard);
            return "stop";
        }
        if("Computer".equals(getMove())){
            win=play(gameBoard);
            printCells(gameBoard);
            if(win==true){
                draws=false;
                winsPrint("         *****"+getConnetFG().getP2().getText()+" Wins*****",gameBoard);
                int score = (Integer.parseInt(new StringTokenizer((String) getConnetFG().getPlayer2Score().getText()).nextToken()));
                getConnetFG().getPlayer2Score().setText(""+(++score));
                return "stop";
            }
        }
        win=gameDraws(gameBoard);
        if(win==true && draws==true){
            winsPrint("         *****Game Draws*****",gameBoard);
            return "stop";
        }
        return "continue";
    }
    /**
     * "Player1 - Player2" seçildiği taktirde bu fonksiyon çağrılır ve sırayla hamle şansını oyunculara verir.
     * @param gameBoard Oyunun oynandığı board
     * @param pPosY Board üzerinde oynanacak sutun değeri
     * @return String
     */
    public String player1AndPlayer2(Cell[][] gameBoard,int pPosY, Boolean undoM, ConnectFour_Game cfg){
        boolean win=false;
        boolean draws = true;
        setUndoMode(undoM);
        setConnetFG(cfg);
        if(getUndoMode() == true){
            if (undoInf.size() != 0)
                undoFunction(gameBoard);
            else
                Toast.makeText(getConnetFG(), "Can Not UNDO Because of The Board is Empty !!!", Toast.LENGTH_SHORT).show();
            return getChooseGame();
        }
        if("Player1".equals(getMove())){
            win = play("Player1",'X',gameBoard,pPosY);
            printCells(gameBoard);
            if(win == true){
                draws=false;
                winsPrint("         *****"+getConnetFG().getP1().getText()+" Wins*****",gameBoard);
                int score = (Integer.parseInt(new StringTokenizer((String) getConnetFG().getPlayer1Score().getText()).nextToken()));
                getConnetFG().getPlayer1Score().setText(""+(++score));
                return "stop";
            }
            win=gameDraws(gameBoard);
            if(win == true && draws == true){
                winsPrint("         *****Game Draws*****",gameBoard);
                return "stop";
            }
        }
        else if("Player2".equals(getMove())){
            win = play("Player2",'O',gameBoard,pPosY);
            printCells(gameBoard);
            if(win == true){
                draws=false;
                winsPrint("         *****"+getConnetFG().getP2().getText()+" Wins*****",gameBoard);
                int score = (Integer.parseInt(new StringTokenizer((String) getConnetFG().getPlayer2Score().getText()).nextToken()));
                getConnetFG().getPlayer2Score().setText(""+(++score));
                return "stop";
            }
            win=gameDraws(gameBoard);
            if(win == true && draws == true){
                winsPrint("         *****Game Draws*****",gameBoard);
                return "stop";
            }
        }
        return "continue";
    }
    private void undoFunction(Cell[][] gameBoard){
        try{
            gameBoard[undoInf.get(undoInf.size() - 1).row][undoInf.get(undoInf.size() - 1).col].setMoveChar('.');
            gameBoard[undoInf.get(undoInf.size() - 1).row][undoInf.get(undoInf.size() - 1).col].changeButtonColor(R.drawable.table_hole);
            setMove(undoInf.get(undoInf.size() - 1).playerType);
            getConnetFG().getMove().setText(String.format("The move was undone in the column %d.",(undoInf.get(undoInf.size() - 1).col) + 1));
            if(getMove() == "Player" || getMove() == "Player1")
                getConnetFG().getMoveOrder().setText(getConnetFG().getP1().getText());
            else
                getConnetFG().getMoveOrder().setText(getConnetFG().getP2().getText());
            undoInf.remove(undoInf.size() - 1);
            printCells(gameBoard);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Hamle sırası Player'a, Player1'e veya Player2'e geldiği taktirde kullanıcıdan kordinat harfleri çerçevesinde harf alır ve board üzerine
     * kendi karakterini ekler. Eklenen karakter "control" fonksiyonu ile kontrol edilir ve kazanıp kazanmadığı belirlenir.
     * @param player Sıranın Player - Player1 - Player2 olduğunu belirler.
     * @param characterOfPlayer Oyuncunun oynadığı karakter
     * @param gameBoard Oyunun oynandığı board
     * @param pPosY Board üzerinde oynanacak sutun değeri
     * @return (True - False)
     */
    public boolean play(String player,char characterOfPlayer,Cell[][] gameBoard,int pPosY){
        Control cont = new Control(getRowSize());
        int pPosX = 0;
        boolean played = false;
        undoInformation undo = new undoInformation(); // undoInformation struct ının değişkeni

        undo.col = pPosY; // class içine column değeri kaydedilir.
        for(int j=getRowSize()-1; j>=0; j--){
            if(gameBoard[j][pPosY].getMoveChar() == '.'){
                gameBoard[j][pPosY].setMoveChar(characterOfPlayer);
                if (characterOfPlayer == 'X')
                    gameBoard[j][pPosY].changeButtonColor(R.drawable.blue_button);
                else
                    gameBoard[j][pPosY].changeButtonColor(R.drawable.yellow_button);
                getConnetFG().getMove().setText(String.format("Column %d was moved.",pPosY+1));
                pPosX=j;
                undo.row = pPosX; // struct içine row değeri kaydedilir.
                undo.moveChar = characterOfPlayer; // struct içine hamle yapanın taşı kaydedilir.
                played = true;
                j=0;
            }
        }
        undoInf.add(undo); // class içindekileri bir paket gibi undoInf Arraylist add() yaparak atar ve class ArrayListinde bilgiler tutulmuş olur.
        boolean controlCheck = cont.control(gameBoard, pPosX, pPosY, characterOfPlayer);
        if(played == true){
            if("Player1".equals(player) && controlCheck==false) {
                undo.playerType = "Player1";
                setMove("Player2");
                getConnetFG().getMoveOrder().setText(getConnetFG().getP2().getText());
            }
            else if("Player2".equals(player) && controlCheck==false) {
                undo.playerType = "Player2";
                setMove("Player1");
                getConnetFG().getMoveOrder().setText(getConnetFG().getP1().getText());
            }
            else if("Player".equals(player) && controlCheck==false) {
                undo.playerType = "Player";
                setMove("Computer");
                getConnetFG().getMoveOrder().setText(getConnetFG().getP2().getText());
            }
        }
        return controlCheck;
    }
    /**
     * Computer: Bu fonksiyon bilgisayarın atak, savunma ya da random bir hamle oynamasını belirler. Bu hamleler kordinat harfleri çerçevesinde
     * belirlenir ve board üzerine kendi karakterini ekler. Eklenen karakter "control" fonksiyonu ile kontrol edilir ve kazanıp kazanmadığı belirlenir.
     * @param gameBoard Oyunun oynandığı board
     * @return boolean (True - False)
     */
    public boolean play(Cell[][] gameBoard){
        undoInformation undo = new undoInformation(); // undoInformation class ının değişkeni
        Control cont = new Control(getRowSize());
        Computer com = new Computer(getRowSize());
        undo.playerType= "Computer"; // class içindeki playerType verisine o anda hangi player tipinin olduğu kaydedilir.
        boolean played = true;
        played = com.ComputerAttackAndDefense(gameBoard, getConnetFG().getMove());
        if(played==false)
            com.ComputerRandom(gameBoard, getConnetFG().getMove());
        undo.row = com.pPosX; // class içine row değeri kaydedilir.
        undo.col = com.pPosY; // class içine column değeri kaydedilir.
        undo.moveChar = 'O'; // class içine hamle yapanın taşı kaydedilir.
        undoInf.add(undo); // class içindekileri bir paket gibi undoInf vectorüne push_back yaparak atar ve class ArrayListinde bilgiler tutulmuş olur.
        setMove("Player");
        getConnetFG().getMoveOrder().setText(getConnetFG().getP1().getText());
        return cont.control(gameBoard,com.pPosX,com.pPosY,'O');
    }
    /**
     * Board un her yeri taranır ve boş bir yer (".") yok ise oyunun berabere olduğu vurgulanır.
     * @param gameBoard Oyunun oynandığı board
     * @return boolean (True - False)
     */
    public boolean gameDraws(Cell[][] gameBoard){
        boolean ret = false;
        int count=0;
        for(int i=0; i < getRowSize(); i++){
            for(int j=0; j < getColSize(); j++){
                if(gameBoard[i][j].getMoveChar() == '.')
                    count++;
            }
        }
        if(count==0)
            ret = true;
        return ret;
    }

    /**
     *
     * @return String(move)
     */
    public String getMove() {
        return move;
    }
    /**
     *
     * @param move Oynanan hamlenin karakteri
     */
    public void setMove(String move) {
        this.move = move;
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
     * gameBoard içinde yer ayrılmış tüm Cell tipindeki hücreler ekrana bastırılır. (Boardun çıktısı)
     * @param gameBoard Oyunun oynandığı board
     */
    public void printCells(Cell[][] gameBoard){
        for(int i=0; i < getColSize(); i++)
            System.out.printf("%d ",(i+1));
        System.out.println();
        for(int i=0; i < getRowSize(); i++){
            for(int j=0; j < getColSize(); j++){
                System.out.printf("%c ",gameBoard[i][j].getMoveChar());
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------");
    }
}
