package tetris.TetrisBlock;

import java.awt.Color;
import tetris.Block;
import tetris.LoaiGach;

public class JBlock extends Block {
    
    public static final Color blockColor=new Color(78, 61, 164);

    public JBlock() {
        int[][] blockShape = {{0,0,1},
                            {1,1,1},
                            {0,0,0}};
        super(blockShape,blockColor,4,-2,LoaiGach.GACH_THUONG);
    }
    
}
