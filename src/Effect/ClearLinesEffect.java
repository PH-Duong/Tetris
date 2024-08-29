package Effect;

import java.awt.Color;
import java.awt.Graphics;
import tetris.Block;

public class ClearLinesEffect {
    private int blockCellsSize;
    private Color[] alphaColor;
    private volatile int currentAlphaIndex;
    private boolean is4Line;
    
    public ClearLinesEffect(int blockCellsSize) {
        this.blockCellsSize = blockCellsSize;
        alphaColor = new Color[10];
        currentAlphaIndex=0;
        for (int i=0;i<10;i+=2) {
            alphaColor[i] = Color.WHITE;
            alphaColor[i+1] = Color.black;
        }
        is4Line=false;
    }
    
    public void setBlockCellsSize(int blockCellsSize) {
        this.blockCellsSize = blockCellsSize;
    }
    
    public void drawFrame(Graphics g, int linePos) {
        for (int i=0;i<15;i++) {
            if (is4Line) {
                g.setColor(alphaColor[currentAlphaIndex].darker());
                g.fillRect(0, 0, 10*blockCellsSize, 20*blockCellsSize);
            }
            
            Block.drawBlockCells(g, i*blockCellsSize, linePos*blockCellsSize, blockCellsSize, alphaColor[currentAlphaIndex]);
        }
    } 
    public void nextFrame() {
        currentAlphaIndex++;
        if (currentAlphaIndex>=10) {
            currentAlphaIndex=0;
        }
    }
    public void setIs4Line(boolean set) {
        this.is4Line = set;
    }
}
