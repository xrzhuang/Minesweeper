package hu.ait.android.minesweeper;

/**
 * Created by MaxZhuang on 3/5/18.
 */

public class Field {
    private int type;
    private int minesAround;
    private boolean isFlagged;
    private boolean wasClicked;

    public Field(int sType, int sMinesAround, boolean sIsFlagged, boolean sWasClicked){
        type = sType;
        minesAround = sMinesAround;
        isFlagged = sIsFlagged;
        wasClicked = sWasClicked;
    }

    public int getType(){
        return type;
    }

    public void setType(int newType){
        type = newType;
    }

    public int getMinesAround(){
        return minesAround;
    }

    public void setMinesAround(int newMinesAround){
        minesAround = newMinesAround;
    }

    public boolean IsFlagged(){
        return isFlagged;
    }

    public void setFlagged(boolean flag){
        isFlagged = flag;
    }

    public boolean isWasClicked() {
        return wasClicked;
    }

    public void setWasClicked(boolean clicked){
        wasClicked = clicked;
    }
}


