package tetris.TetrisBlock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import tetris.Block;
import tetris.BLockType;

public class IBlock extends Block {
    
    public static final Color blockColor=new Color(49, 178, 130);
    
    public IBlock() {
        int[][] blockShape = {{0,0,0,0},
                            {1,1,1,1},
                            {0,0,0,0},
                            {0,0,0,0}};
        super(blockShape,blockColor,3,-2,BLockType.I_BLOCK);
    }
    
}
