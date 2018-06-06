package hu.ait.android.minesweeper;
import java.util.Random;

/**
 * Created by MaxZhuang on 2/26/18.
 */

public class MinesweeperModel {
    private static MinesweeperModel instance = null;
    public static MinesweeperModel getInstance(){
        if (instance == null){
            instance = new MinesweeperModel();
        }
        return instance;
    }

    private MinesweeperModel(){

    }

    public static int EMPTY = 0;
    public static int MINE = 1;
    public static boolean FLAGMODE = false;
    public static int GAMESTATE = 0;

    Field[][] fields = new Field[][] {
            {new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false)},
            {new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false)},
            {new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false)},
            {new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false)},
            {new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false), new Field(EMPTY,0, false, false)}
    };

    public int getType(short x, short y){
        return fields[x][y].getType();
    }

    public void setType(short x, short y, int newType){
        fields[x][y].setType(newType);
    }

    public int getMinesAround(short x, short y){
        return fields[x][y].getMinesAround();
    }

    public void setMinesAround(short x, short y, int newMines){
        fields[x][y].setMinesAround(newMines);
    }

    public boolean IsFlagged(short x, short y){
        return fields[x][y].IsFlagged();
    }

    public void setFlagged(short x, short y, boolean newFlag){
        fields[x][y].setFlagged(newFlag);
    }

    public boolean isWasClicked(short x, short y) {
        return fields[x][y].isWasClicked();
    }

    public void setWasClicked(short x, short y, boolean clicked){
        fields[x][y].setWasClicked(clicked);
    }



    public void startGame(){
        Random rand = new Random();
        for (short i=0; i < 5; i++) {
            for (short j=0; j < 5; j++){
                double probMine = rand.nextDouble();
                if (probMine > 0.75) {
                    setType( i, j, MINE);
                }
            }
        }
        markAdj();
    }

    public void resetGame(){
        Random rand = new Random();
        for (short i=0; i < 5; i++) {
            for (short j=0; j < 5; j++){
                double probMine = rand.nextDouble();
                setType(i,j,EMPTY);
                setMinesAround(i,j,0);
                setWasClicked(i,j,false);
                setFlagged(i,j,false);
                if (probMine > 0.75) {
                    setType( i, j, MINE);
                }
            }
        }
        markAdj();

    }


    public void trySquare(short x, short y){
        if (FLAGMODE == false) {
            if (fields[x][y].getType() == MINE) {
                fields[x][y].setWasClicked(true);
                GAMESTATE = 1;
            } else {
                recurseO(x, y);
            }
        }
        else if (FLAGMODE == true){
            if ((fields[x][y].getType() == MINE) && (IsFlagged(x, y)==false)){
                setFlagged(x,y,true);
                setWasClicked(x,y,true);
            }
            else {
                GAMESTATE = 1;
            }
        }

        checkGameWin();

    }

    private void checkGameWin(){

        for (short i=0; i < 5; i++) {
            for (short j = 0; j < 5; j++) {
                if (isWasClicked(i,j) == false){
                    return;
                }
            }
        }
        GAMESTATE = 2;
    }

    private void recurseO(short x, short y){
        if ((fields[x][y].getType() == EMPTY) && (fields[x][y].getMinesAround() != 0)){
            fields[x][y].setWasClicked(true);
            return;
        }
        if ((fields[x][y].getType() == EMPTY) && (fields[x][y].getMinesAround() == 0) && (fields[x][y].isWasClicked() == false)){
            fields[x][y].setWasClicked(true);
            if (x>0){
                recurseO((short) (x-1), y);
            }
            if ((x>0) && (y>0)){
                recurseO((short) (x-1), (short) (y-1));
            }
            if ((x>0) && (y<4)){
                recurseO((short) (x-1), (short) (y+1));
            }
            if (y<4){
                recurseO( (x), (short) (y+1));
            }
            if (y>0){
                recurseO( (x), (short) (y-1));
            }
            if (x<4) {
                recurseO((short) (x+1), y);
            }
            if ((x<4) && (y>0)){
                recurseO((short) (x+1), (short) (y-1));
            }
            if ((x<4) && (y<4)) {
                recurseO((short) (x+1), (short) (y+1));
            }

        }


    }

    private void markAdj() {
        for (short i=0; i < 5;i++){
            for (short j=0; j< 5; j++){
                if (getType(i,j) == EMPTY){
                    short minecounter = 0;
                    if ((i>0) && (getType((short) (i-1),j) == MINE)){
                        minecounter++;
                    }
                    if ((i>0) && (j>0) && (getType((short) (i-1),(short) (j-1)) == MINE)){
                        minecounter++;
                    }
                    if ((i>0) && (j<4) && (getType((short) (i-1),(short) (j+1)) == MINE)){
                        minecounter++;
                    }
                    if ((j<4) && (getType((i),(short) (j+1)) == MINE)){
                        minecounter++;
                    }
                    if ((j>0) && (getType((i),(short) (j-1)) == MINE)){
                        minecounter++;
                    }
                    if ((i<4) && (getType((short) (i+1),j) == MINE)){
                        minecounter++;
                    }
                    if ((i<4) && (j>0) && (getType((short) (i+1),(short) (j-1)) == MINE)){
                        minecounter++;
                    }
                    if ((i<4) && (j<4) && (getType((short) (i+1),(short) (j+1)) == MINE)){
                        minecounter++;
                    }

                    if (minecounter>0){
                        setMinesAround(i, j, minecounter);
                    }
                }
            }
        }
    }

}

