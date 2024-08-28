package tetris.TetrisBlock;

import java.awt.Color;
import tetris.Block;
import tetris.LoaiGach;

public class LBlock extends Block {
    
    public static final Color blockColor=new Color(180, 100, 50);

    public LBlock() {
        int[][] blockShape = {{1,0,0},
                            {1,1,1},
                            {0,0,0}};
        super(blockShape,blockColor,4,-2,LoaiGach.GACH_THUONG);
    }
    
}