package com.myapplication.mazejava;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Stack;

public class GameView extends View {
    private Cell[][] cells;
    private static final int COLS = 14, ROWS = 5;
    private static final float WALL_THICKNESS = 4;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint;
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        createMaze();
    }

    private void createMaze(){
        Stack<Cell> stack = new Stack<>();
        Cell current, next;

        cells = new Cell[COLS][ROWS];

        for(int x=0; x<COLS; x++){
            for(int y=0; y<ROWS; y++){
                cells[x][y] = new Cell(x,y);
            }
        }

        current = cells[0][0];
        current.visited = true;
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else current = stack.pop();
        }while(!stack.empty());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);

        int width = getWidth();
        int height = getHeight();

        if(width/height < COLS/ROWS){
            cellSize = width/(COLS+1);
        }else cellSize = height/(ROWS+1);

        hMargin = (width - COLS*cellSize)/2;
        vMargin = (height -ROWS*cellSize) /2;

        canvas.translate(hMargin, vMargin);

        for(int x=0; x<COLS; x++){
            for(int y=0; y<ROWS; y++){
                if(cells[x][y].topWall)
                    canvas.drawLine(
                            x*cellSize,
                            y*cellSize,
                            (x+1)*cellSize,
                            y*cellSize,
                            wallPaint);

                if(cells[x][y].leftWall)
                    canvas.drawLine(
                            x*cellSize,
                            y*cellSize,
                            x*cellSize,
                            (y+1)*cellSize,
                            wallPaint);

                if(cells[x][y].bottomWall)
                    canvas.drawLine(
                            x*cellSize,
                            (y+1)*cellSize,
                            (x+1)*cellSize,
                            (y+1)*cellSize,
                            wallPaint);

                if(cells[x][y].rightWall)
                    canvas.drawLine(
                            (x+1)*cellSize,
                            y*cellSize,
                            (x+1)*cellSize,
                            (y+1)*cellSize,
                            wallPaint);
            }
        }
    }

    private class Cell{
        boolean
                topWall = true,
                leftWall = true,
                bottomWall = true,
                rightWall = true;

        int col, row;
        public Cell(int col, int row){
            this.col = col;
            this.row = row;
        }
    }
}










