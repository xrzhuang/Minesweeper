package hu.ait.android.minesweeper;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * Created by MaxZhuang on 2/26/18.
 */

public class MinesweeperView extends View{
    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintFont;
    private Paint paintBomb;
    private Bitmap flag;

    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);
        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
        paintBomb = new Paint();
        paintBomb.setColor(Color.BLACK);
        paintBomb.setStyle(Paint.Style.FILL);
        paintFont = new Paint();
        paintFont.setColor(Color.BLACK);
        paintFont.setTextSize(130);
        flag = BitmapFactory.decodeResource(getResources(),R.drawable.smolflag);

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawRect(0,0, getWidth(), getHeight(), paintBackground);
        drawGameArea(canvas);
        drawContent(canvas);
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0,0,getWidth(),getHeight(),paintLine);
        canvas.drawLine(getWidth()/5,0, getWidth()/5, getHeight(),paintLine);
        canvas.drawLine(2*(getWidth()/5), 0, 2*(getWidth()/5), getHeight(), paintLine);
        canvas.drawLine(3*(getWidth()/5), 0, 3*(getWidth()/5), getHeight(), paintLine);
        canvas.drawLine(4*(getWidth()/5), 0, 4*(getWidth()/5), getHeight(), paintLine);
        canvas.drawLine(0, getHeight()/5, getWidth(), getHeight()/5, paintLine);
        canvas.drawLine(0, 2*(getHeight()/5), getWidth(), 2*(getHeight()/5), paintLine);
        canvas.drawLine(0, 3*(getHeight()/5), getWidth(), 3*(getHeight()/5), paintLine);
        canvas.drawLine(0, 4*(getHeight()/5), getWidth(), 4*(getHeight()/5), paintLine);
    }

    private void drawContent(Canvas canvas){
        for (short i = 0; i < 5; i++){
            for (short j = 0; j < 5; j++){
                if ((MinesweeperModel.getInstance().getType(i,j)==MinesweeperModel.MINE) && (MinesweeperModel.getInstance().isWasClicked(i,j) == true) && (MinesweeperModel.getInstance().IsFlagged(i,j)==false)) {
                    float centerX = i* getWidth()/5 + getWidth()/10;
                    float centerY = j* getHeight()/5 + getHeight()/10;
                    int radius = getHeight()/10 - 2;
                    canvas.drawCircle(centerX, centerY, radius, paintBomb);
                }
                else if (MinesweeperModel.getInstance().IsFlagged(i,j)==true){
                    float centerX = i* getWidth()/5;
                    float centerY = j* getHeight()/5;
                    canvas.drawBitmap(flag, centerX, centerY, paintBomb);
                }

                else if ((MinesweeperModel.getInstance().getType(i,j)==MinesweeperModel.EMPTY) && (MinesweeperModel.getInstance().isWasClicked(i,j)==true)){
                    int adj = MinesweeperModel.getInstance().getMinesAround(i,j);
                    canvas.drawText(Integer.toString(adj),i*getWidth()/5 + getWidth()/15,j*getWidth()/5 + getHeight()/6,paintFont);
                }

            }
        }
    }

    private void drawGameState(){
        if (MinesweeperModel.GAMESTATE == 1){
            Toast.makeText(this.getContext(), "YOU LOSE SUCKA", Toast.LENGTH_SHORT).show();
        }
        if (MinesweeperModel.GAMESTATE == 2){
            Toast.makeText(this.getContext(), "YOU WIN HOMES", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if ((event.getAction() == MotionEvent.ACTION_DOWN) && (MinesweeperModel.GAMESTATE==0)){
            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);
            MinesweeperModel.getInstance().trySquare((short) tX,(short) tY);
        }
        drawGameState();
        invalidate();
        return true;
    }

    public void start(){
        MinesweeperModel.getInstance().startGame();
        invalidate();
    }

    public void restart(){
        MinesweeperModel.getInstance().resetGame();
        invalidate();
    }



}
