package Effect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import tetris.Block;

public class ClearLinesEffect {
    private int blockCellsSize;
    private Color[] alphaColor;
    private volatile int currentAlphaIndex;
    private boolean is4Line;
    
    public ClearLinesEffect(int blockCellsSize) {
        this.blockCellsSize = blockCellsSize;
        alphaColor = new Color[50];
        currentAlphaIndex=0;
//        for (int i=0;i<10;i+=2) {
//            alphaColor[i] = Color.WHITE;
//            alphaColor[i+1] = Color.black;
//        }
        for (int i=0;i<20;i++) {
            alphaColor[i] = new Color(255,255,255,i*5);
        }
        for (int i=20;i<40;i++) {
            alphaColor[i] = alphaColor[39-i];
        }
        for (int i=40;i<50;i++) {
            alphaColor[i] = Color.BLACK;
        }
        is4Line=false;
    }
    
    public void setBlockCellsSize(int blockCellsSize) {
        this.blockCellsSize = blockCellsSize;
    }
    
    public void drawFrame(Graphics g, int linePos) {
        
        Graphics2D g2d = (Graphics2D)(g);
        
        for (int i=0;i<15;i++) {
//            if (is4Line) {
//                g.setColor(alphaColor[currentAlphaIndex].darker());
//                g.fillRect(0, 0, 10*blockCellsSize, 20*blockCellsSize);
//            }

            Block.drawBlockCells(g, i*blockCellsSize, linePos*blockCellsSize, blockCellsSize, alphaColor[currentAlphaIndex]);
        }
    } 
    public void nextFrame() {
        currentAlphaIndex++;
        if (currentAlphaIndex>=50) {
            currentAlphaIndex=0;
        }
    }
    public void setIs4Line(boolean set) {
        this.is4Line = set;
    }
}
